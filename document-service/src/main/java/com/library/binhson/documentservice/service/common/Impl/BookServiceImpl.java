package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.BookDto;
import com.library.binhson.documentservice.dto.request.RequestBookDto;
import com.library.binhson.documentservice.dto.request.RequestUpdateBookDto;
import com.library.binhson.documentservice.entity.Author;
import com.library.binhson.documentservice.entity.Book;
import com.library.binhson.documentservice.entity.EBook;
import com.library.binhson.documentservice.entity.PhysicalBook;
import com.library.binhson.documentservice.repository.*;
import com.library.binhson.documentservice.service.common.IBookService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import com.library.binhson.documentservice.ultil.mapper.BookMapper;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
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
        return null;
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
    public BookDto add(RequestBookDto bookDto) {
        Book addedBook;
        var book = modelMapper.map(bookDto, Book.class);
        book = setAuthors(book, bookDto);
        book = setCategories(book, bookDto.category_ids());
        if (bookDto.book_style().equalsIgnoreCase("ebook")) {
            book = setEbook(bookDto, ((EBook) book));
            addedBook = ebookRepository.save((EBook) book);
        } else if (bookDto.book_style().equalsIgnoreCase("physical")) {
            book = setPhysicalBook(bookDto, ((PhysicalBook) book));
            addedBook = physicalBookRepository.save(((PhysicalBook) book));
        } else {
            addedBook = bookRepository.save(book);
        }
        return map(addedBook);
    }

    private Book setPhysicalBook(RequestBookDto bookDto, PhysicalBook book) {
        book.setDepreciation(bookDto.depreciation());
        book.setWeight(bookDto.weight());
        book.setHeight(bookDto.height());
        book.setThickness(bookDto.thickness());
        return book;
    }

    private EBook setEbook(RequestBookDto bookDto, EBook book) {
        book.setFileName(book.getFileName());
        book.setGeneratedDate(book.getGeneratedDate());
        return book;
    }

    private Book setCategories(Book book, Set<Long> categoryIds) {
        if (Objects.nonNull(categoryIds)) {
            categoryIds.forEach(categoryId -> {
                try {
                    var category = categoryRepository.findById(categoryId).orElseThrow(RuntimeException::new);
                    var categories = book.getCategories();
                    if (Objects.isNull(categories))
                        categories = new HashSet<>();
                    categories.add(category);
                    book.setCategories(categories);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }
            });
        }
        return book;
    }

    private Book setAuthors(Book book, RequestBookDto bookDto) {
        if (Objects.nonNull(bookDto.author_ids()) && !bookDto.author_ids().isEmpty()) {
            bookDto.author_ids().forEach(authorId -> {
                try {
                    var author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException(""));
                    var authors = book.getAuthors();
                    if (Objects.isNull(authors))
                        authors = new HashSet<>();
                    authors.add(author);
                    book.setAuthors(authors);
                } catch (Exception ex) {
                    log.error(ex.getMessage());
                }

            });
        }
        return book;
    }

    @Override
    public BookDto update(RequestUpdateBookDto bookDto, String id) {
        var book=findById(id);
        BookMapper.INSTANCE.updateEntityFromDTO(bookDto, book, authorRepository,categoryRepository, importInvoiceRepository);
        bookRepository.save(book);
        if(physicalBookRepository.existsById(id)){
            var physicalBook=physicalBookRepository.findById(id).get();
            BookMapper.INSTANCE.updatePhysicalBookFromDTO(bookDto, physicalBook, addressRepository);
            physicalBookRepository.save(physicalBook);
        } else if (ebookRepository.existsById(id)) {
            var ebook=ebookRepository.findById(id).get();
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
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public byte[] getEbook(String id) {
        var ebook=findEBookById(id);
        return ebook.getEbook();
    }


    @Cacheable(value = "books")
    private List<Book> getAll() {
        return bookRepository.findAll();
    }

    private Book findById(String id) {
        return getAll().stream().filter(book -> book.getId().equals(id)).findFirst().orElseThrow(() -> new BadRequestException("Book don't exist."));
    }
    private EBook findEBookById(String id){
        return ebookRepository.findById(id).orElseThrow(()-> new BadRequestException("Book don't exist"));
    }

    private BookDto map(Book book) {
        var dto = modelMapper.map(book, BookDto.class);
        if (physicalBookRepository.existsById(book.getId())) {
            PhysicalBook physicalBook = physicalBookRepository.findById(book.getId()).orElseThrow(() -> new RuntimeException("SERVER ERROR."));
            dto.setLocalAddressId(physicalBook.getLocalAddress().getId());
            dto.setHeight(physicalBook.getHeight());
            dto.setWeight(physicalBook.getWeight());
            dto.setThickness(physicalBook.getThickness());
            dto.setDepreciation(physicalBook.getDepreciation());
            dto.setType("physical");
            log.info("Physical book: " + ((PhysicalBook) book).getWeight());
        } else if (ebookRepository.existsById(book.getId())) {
            EBook eBook = ebookRepository.findById(book.getId()).orElseThrow(() -> new RuntimeException("SERVER ERROR."));
            dto.setFileName(eBook.getFileName());
            dto.setGeneratedDate(eBook.getGeneratedDate());
            log.info("Ebook: " + ((EBook) book).getFileName());
            dto.setType("ebook");
        } else log.error("Book have yet data.");
        return dto;
    }
}
