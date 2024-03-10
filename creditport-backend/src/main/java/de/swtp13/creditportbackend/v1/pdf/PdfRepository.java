package de.swtp13.creditportbackend.v1.pdf;

//create pdfRepository interface

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface PdfRepository extends JpaRepository<Pdf, Integer> {
}