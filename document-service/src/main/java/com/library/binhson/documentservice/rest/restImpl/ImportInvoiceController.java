package com.library.binhson.documentservice.rest.restImpl;

import com.library.binhson.documentservice.dto.ImportInvoiceDto;
import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import com.library.binhson.documentservice.rest.IImportInvoiceController;
import com.library.binhson.documentservice.service.common.IImportInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.library.binhson.documentservice.ultil.ResponseUtil.response;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class ImportInvoiceController implements IImportInvoiceController {
    private final IImportInvoiceService importInvoiceService;
    @Override
    public ResponseEntity<?> get(Integer offset, Integer limit) {
        List<ImportInvoiceDto> invoicePage=importInvoiceService.get(offset,limit);
        return generateResponse(invoicePage);
    }

    @Override
    public ResponseEntity<?> get(String id) {
        ImportInvoiceDto invoice=importInvoiceService.get(id);
        return generateResponse(invoice);
    }

    @Override
    public ResponseEntity<?> search(Map<String, String> map) {
        //todo
        return null;
    }


    @Override
    public ResponseEntity<?> delete(String id) {
        importInvoiceService.delete(id);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> addImportImportInvoice(RequestImportInvoiceDto invoiceDto) {
        ImportInvoiceDto invoice=importInvoiceService.add(invoiceDto);
        return generateResponse(invoice);
    }

    @Override
    public ResponseEntity<?> updateImportInvoice(RequestImportInvoiceDto invoiceDto, Long id) {
        ImportInvoiceDto invoice=importInvoiceService.update(invoiceDto,id);
        return generateResponse(invoice);
    }


    private ResponseEntity<?> generateResponse(List<ImportInvoiceDto> dtos){
        dtos=dtos.stream().peek(importInvoiceDto -> importInvoiceDto.add(setHateoas(importInvoiceDto))).collect(Collectors.toList());
        return response(dtos,dtos.size());
    }
    private ResponseEntity<?> generateResponse(ImportInvoiceDto dto){
        dto=dto.add(setHateoas(dto));
        return response(dto,1);

    }
    private List<Link> setHateoas(ImportInvoiceDto dto){
        Link selfLink=linkTo(methodOn(ImportInvoiceController.class).get(dto.getId()+"")).withSelfRel();
        Link librarianLink=linkTo(ImportInvoiceDto.class).slash("api").slash("v1").slash("user-service")
                .slash("users")
                .withRel("librarian");
        return Arrays.asList(selfLink,librarianLink);
    }
}
