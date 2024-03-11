package com.library.binhson.documentservice.ultil.mapper;

import com.library.binhson.documentservice.dto.BookDto;
import com.library.binhson.documentservice.dto.request.RequestUpdateBookDto;
import com.library.binhson.documentservice.entity.Book;
import com.library.binhson.documentservice.entity.EBook;
import com.library.binhson.documentservice.entity.PhysicalBook;
import com.library.binhson.documentservice.repository.AuthorRepository;
import com.library.binhson.documentservice.repository.CategoryRepository;
import com.library.binhson.documentservice.repository.ImportInvoiceRepository;
import com.library.binhson.documentservice.repository.LocalAddressRepository;
import jakarta.ws.rs.NotFoundException;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
@Transactional(rollbackFor ={ Exception.class, NotFoundException.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);


    BookDto entityToDTO(Book entity);

    Book dtoToEntity(BookDto dto);




    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromDTO(RequestUpdateBookDto dto, @MappingTarget Book entity, @Context AuthorRepository authorRepository,
                             @Context CategoryRepository categoryRepository,@Context ImportInvoiceRepository importInvoiceRepository);

    @AfterMapping
    default void customizeMapping(BookDto dto, @MappingTarget Book entity, @Context AuthorRepository authorRepository,
                                  @Context CategoryRepository categoryRepository,@Context ImportInvoiceRepository importInvoiceRepository) {
        entity.setAuthors(dto.getAuthorIds().stream()
                .map(authorRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet()));

        entity.setCategories(dto.getCategoryIds().stream()
                .map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet()));
        entity.setStogreInvoince(importInvoiceRepository.findById(dto.getImportInvoiceId()).get());
    }


    void updatePhysicalBookFromDTO(RequestUpdateBookDto bookDto, @MappingTarget PhysicalBook physicalBook, @Context LocalAddressRepository addressRepository);
    @AfterMapping
    default void customizeMapping(RequestUpdateBookDto bookDto, @MappingTarget PhysicalBook physicalBook, @Context LocalAddressRepository addressRepository){
        physicalBook.setLocalAddress(addressRepository.findById(bookDto.getLocalAddressId()).get());
    }

    void updateEBookFromDTO(RequestUpdateBookDto bookDto,@MappingTarget EBook ebook);
}
