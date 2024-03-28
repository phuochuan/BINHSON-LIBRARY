package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.BookDto;
import com.library.binhson.documentservice.dto.request.RequestBookDto;
import com.library.binhson.documentservice.dto.request.RequestUpdateBookDto;
import com.library.binhson.documentservice.entity.*;
import com.library.binhson.documentservice.repository.*;
import com.library.binhson.documentservice.service.common.IBookService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import com.library.binhson.documentservice.ultil.mapper.BookMapper;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.apache.james.mime4j.dom.datetime.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;
    private final EBookRepository ebookRepository;
    private final PhysicalBookRepository physicalBookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final LocalAddressRepository addressRepository;
    private final ImportInvoiceRepository importInvoiceRepository;

    @Override
    public List<BookDto> get(Integer offset, Integer limit) {
        List<BookDto> books = getAll().stream().map(this::map).toList();
        PageUtilObject pageUtilObject = new PageUtilObject(limit, offset, Arrays.asList(books.toArray()));
        return (List<BookDto>) pageUtilObject.getData();
    }

    @Override
    public BookDto getById(String id) {
        var book = findById(id);
        return map(book);
    }

    @Override
    public List<BookDto> search(Map<String, String> map) {
        Integer offset=1;
        Integer limit=10;
        String key="";
        try{
            key=map.get("key");
            offset=Integer.parseInt(map.get("offset"));
            limit=Integer.parseInt(map.get("limit"));
        }catch (Exception ex){
        }
        String finalKey = key;
        List<BookDto> bookDtos=getAll().stream().filter(book -> book.getName().contains(finalKey)).map(book -> map(book)).toList();
        PageUtilObject pageUtilObject=new PageUtilObject(limit,offset, new ArrayList<>(bookDtos));
        return(List<BookDto>) pageUtilObject.getData();
    }

    @Override
    public void deleteById(String id) {
        if (!bookRepository.existsById(id))
            throw new BadRequestException("Book don't exist.");
        bookRepository.deleteById(id);
        if (ebookRepository.existsById(id))
            ebookRepository.deleteById(id);
        if (physicalBookRepository.existsById(id))
            physicalBookRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "books")
    public BookDto add(RequestBookDto bookDto) {
        log.info("Add new book");
        Book addedBook;
        var book = Book.builder()
                .id(getBookId())
                .authors(setAuthors(bookDto))
                .categories(setCategories(bookDto.category_ids()))
                .length(bookDto.length())
                .name(bookDto.name())
                .quality(bookDto.quality())
                .republishTime(bookDto.republish_time())
                .stogreInvoince(importInvoiceRepository.findById(bookDto.stogre_invoince_id()).orElseThrow())
                .build();

        if (bookDto.book_style().equalsIgnoreCase("ebook")) {
            var ebook = setEbook(bookDto, (book));
            addedBook = ebookRepository.save(ebook);
        } else if (bookDto.book_style().equalsIgnoreCase("physical")) {
            var physicalBook = setPhysicalBook(bookDto, book);
            addedBook = physicalBookRepository.save((physicalBook));
        } else addedBook = bookRepository.save(book);
        return map(addedBook);
    }

    private String getBookId() {
        return "DC_" + (new Date()).toString();
    }

    private PhysicalBook setPhysicalBook(RequestBookDto bookDto, Book oldBook) {
        var book = new PhysicalBook();
        book = modelMapper.map(oldBook, PhysicalBook.class);
        log.info("book author: ", Arrays.toString(book.getAuthors().toArray()));
        book.setId(oldBook.getId());
        book.setDepreciation(bookDto.depreciation());
        book.setWeight(bookDto.weight());
        book.setHeight(bookDto.height());
        book.setThickness(bookDto.thickness());
        return book;
    }

    private EBook setEbook(RequestBookDto bookDto, Book oldBook) {
        var book = new EBook();
        book.setId(oldBook.getId());
        book = modelMapper.map(oldBook, EBook.class);
        book.setFileName(book.getFileName());
        book.setGeneratedDate(book.getGeneratedDate());
        return book;
    }

    private Set<Category> setCategories(Set<Long> categoryIds) {
        if (Objects.nonNull(categoryIds)) {
            return categoryIds.stream().map(categoryId -> {
                return categoryRepository.findById(categoryId).orElseThrow(RuntimeException::new);
            }).collect(Collectors.toSet());
        }
        log.info("Category is null");

        return new HashSet<>();
    }

    private Set<Author> setAuthors(RequestBookDto bookDto) {
        if (Objects.nonNull(bookDto.author_ids())) {
            return bookDto.author_ids().stream().map(authorId -> {
                return authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException(""));
            }).collect(Collectors.toSet());
        }
        log.info("Author is null");
        return new HashSet<>();
    }

    @Override
    @CacheEvict(value = "books")
    public BookDto update(RequestUpdateBookDto bookDto, String id) {
        var book = findById(id);
        BookMapper.INSTANCE.updateEntityFromDTO(bookDto, book, authorRepository, categoryRepository, importInvoiceRepository);
        bookRepository.save(book);
        if (physicalBookRepository.existsById(id)) {
            var physicalBook = physicalBookRepository.findById(id).get();
            BookMapper.INSTANCE.updatePhysicalBookFromDTO(bookDto, physicalBook, addressRepository);
            physicalBookRepository.save(physicalBook);
        } else if (ebookRepository.existsById(id)) {
            var ebook = ebookRepository.findById(id).get();
            BookMapper.INSTANCE.updateEBookFromDTO(bookDto, ebook);
            ebookRepository.save(ebook);
        }
        return map(book);
    }

    @Override
    public void setFileForEBook(MultipartFile ebook, String id) {
        try {
            var book = findEBookById(id);
            book.setFileName(ebook.getOriginalFilename());
            book.setEbook(ebook.getBytes());
            ebookRepository.save(book);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public byte[] getEbook(String id) {
        var ebook = findEBookById(id);
        return ebook.getEbook();
    }


    @Cacheable(value = "books")
    private List<Book> getAll() {
        return bookRepository.findAll();
    }

    private Book findById(String id) {
        return getAll().stream().filter(book -> book.getId().equals(id)).findFirst().orElseThrow(() -> new BadRequestException("Book don't exist."));
    }

    private EBook findEBookById(String id) {
        return ebookRepository.findById(id).orElseThrow(() -> new BadRequestException("Book don't exist"));
    }

    private BookDto map(Book book) {
        BookDto dto;
        if (physicalBookRepository.existsById(book.getId())) {
            PhysicalBook physicalBook = physicalBookRepository.findById(book.getId()).orElseThrow(() -> new RuntimeException("SERVER ERROR."));
            dto = BookMapper.INSTANCE.entityToDTO(physicalBook);
            if (Objects.nonNull(physicalBook.getLocalAddress()))
                dto.setLocalAddressId(physicalBook.getLocalAddress().getId());
            dto.setHeight(physicalBook.getHeight());
            dto.setWeight(physicalBook.getWeight());
            dto.setThickness(physicalBook.getThickness());
            dto.setDepreciation(physicalBook.getDepreciation());
            dto.setType("physical");
            log.info("Physical book: " + ((PhysicalBook) book).getWeight());
        } else if (ebookRepository.existsById(book.getId())) {
            EBook eBook = ebookRepository.findById(book.getId()).orElseThrow(() -> new RuntimeException("SERVER ERROR."));
            dto = modelMapper.map(eBook, BookDto.class);
            dto.setFileName(eBook.getFileName());
            dto.setGeneratedDate(eBook.getGeneratedDate());
            log.info("Ebook: " + ((EBook) book).getFileName());
            dto.setType("ebook");
        } else dto = modelMapper.map(book, BookDto.class);
        dto.setCategoryIds(book.getCategories().stream().map(Category::getId).collect(Collectors.toSet()));
        dto.setAuthorIds(book.getAuthors().stream().map(Author::getId).collect(Collectors.toSet()));
        return dto;
    }
}
