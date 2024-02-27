package com.library.binhson.documentservice.repository;

import com.library.binhson.documentservice.entity.ImportInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportInvoiceRepository extends JpaRepository<ImportInvoice,Long >
{
}
