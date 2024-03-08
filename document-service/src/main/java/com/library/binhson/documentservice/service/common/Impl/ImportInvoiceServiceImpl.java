package com.library.binhson.documentservice.service.common.Impl;

import com.library.binhson.documentservice.dto.ImportInvoiceDto;
import com.library.binhson.documentservice.dto.request.RequestImportInvoiceDto;
import com.library.binhson.documentservice.entity.ImportInvoice;
import com.library.binhson.documentservice.repository.ImportInvoiceRepository;
import com.library.binhson.documentservice.repository.LibrarianRepository;
import com.library.binhson.documentservice.service.common.IImportInvoiceService;
import com.library.binhson.documentservice.ultil.PageUtilObject;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.library.binhson.documentservice.ultil.AuthMyInfoUtils.getUsername;

@Service
@RequiredArgsConstructor
public class ImportInvoiceServiceImpl implements IImportInvoiceService {
    private final ImportInvoiceRepository importInvoiceRepository;
    private final ModelMapper modelMapper;
    private final LibrarianRepository librarianRepository;

    @Override
    public List<ImportInvoiceDto> get(Integer offset, Integer limit) {
        var allInvoice = getAllDto();
        var page = new PageUtilObject(limit, offset, new ArrayList<>(allInvoice));
        return (List<ImportInvoiceDto>) page.getData();
    }

    @Override
    public ImportInvoiceDto get(String id) {
        return findDtoById(Long.parseLong(id));
    }

    @Override
    public void delete(String id) {
        var invoice = findById(Long.parseLong(id));
        importInvoiceRepository.delete(invoice);
    }

    @Override
    public ImportInvoiceDto add(RequestImportInvoiceDto invoiceDto) {
        if (!valid(invoiceDto))
            throw new BadRequestException("Fill fully for invoice.");
        var invoice = ImportInvoice.builder()
                .cost(invoiceDto.cost())
                .origin(invoiceDto.origin())
                .importWarehouseDate(invoiceDto.import_warehouse_date())
                .build();
        if(Objects.isNull(invoice.getImportWarehouseDate()))
            invoice.setImportWarehouseDate(new Date());
        String librarianUserName = getUsername();
        var librarian = librarianRepository.findByUsername(librarianUserName).orElseThrow(() -> new RuntimeException("Server Error"));
        invoice.setLibrarian(librarian);
        var saveInvoice = importInvoiceRepository.save(invoice);
        return modelMapper.map(saveInvoice, ImportInvoiceDto.class);
    }

    private boolean valid(RequestImportInvoiceDto invoiceDto) {
        return Objects.nonNull(invoiceDto.cost()) && Objects.nonNull(invoiceDto.origin())
                && invoiceDto.cost() >= 0;
    }

    @Override
    public ImportInvoiceDto update(RequestImportInvoiceDto invoiceDto, Long id) {
        var invoice=findById(id);
        if(Objects.nonNull(invoiceDto.cost()) && invoiceDto.cost()>0)
            invoice.setCost(invoiceDto.cost());
        if(Objects.nonNull(invoiceDto.import_warehouse_date()))
            invoice.setImportWarehouseDate(invoice.getImportWarehouseDate());
        if(Objects.nonNull(invoiceDto.origin()))
            invoice.setOrigin(invoiceDto.origin());
        var saveInvoice = importInvoiceRepository.save(invoice);
        return modelMapper.map(saveInvoice, ImportInvoiceDto.class);
    }

    @Cacheable(value = "import_invoice")
    private List<ImportInvoice> getAll() {
        return importInvoiceRepository.findAll();
    }

    private List<ImportInvoiceDto> getAllDto() {
        var invoices = getAll();
        return invoices.stream().map(importInvoice -> modelMapper.map(importInvoice, ImportInvoiceDto.class)).toList();
    }


    private ImportInvoice findById(Long id) {
        return getAll().stream().filter(importInvoice -> importInvoice.getId().equals(id)).findFirst().orElseThrow(() -> new BadRequestException("Import Invoice don't exist"));
    }

    private ImportInvoiceDto findDtoById(Long id) {
        return modelMapper.map(findById(id), ImportInvoiceDto.class);
    }
}
