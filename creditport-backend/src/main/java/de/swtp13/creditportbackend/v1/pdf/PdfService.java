package de.swtp13.creditportbackend.v1.pdf;

import com.google.zxing.WriterException;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.*;
import com.itextpdf.io.source.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PdfService {

    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private RequestRepository requestRepository;

    public Optional<byte[]> createOverview(int procedureId) {
        Optional<Procedure> procedureOptional = procedureRepository.findById(procedureId);
        if (!procedureOptional.isPresent()) {
            return Optional.empty();
        }

        Procedure procedure = procedureOptional.get();
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(byteArrayOutputStream);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            applyTemplateToDocument(document);
            setDocumentMargins(document);
            addHeadingToDocument(document, procedure);
            addTableToDocument(document, requests);
            generateQRCodeImage(document,"http://localhost:5173/status/"+procedureId);
            addFooterToDocument(document, procedure);

            document.close();
            return Optional.of(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            System.err.println("Error while creating PDF: " + e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void applyTemplateToDocument(Document document) throws IOException {
        ClassPathResource resource = new ClassPathResource("template.pdf");
        PdfDocument templateDoc = new PdfDocument(new PdfReader(resource.getInputStream()));
        templateDoc.copyPagesTo(1, templateDoc.getNumberOfPages(), document.getPdfDocument());
        templateDoc.close();
    }

    private void setDocumentMargins(Document document) {
        document.setMargins(200, 20, 20, 20);
    }

    private void addHeadingToDocument(Document document, Procedure procedure) {
        Style headingStyle = new Style()
                .setFontSize(16)
                .setBold();
        Paragraph heading = new Paragraph("Dein Antrag: " + procedure.getProcedureId())
                .setTextAlignment(TextAlignment.LEFT)
                .addStyle(headingStyle);
        document.add(heading);
    }

    private void addTableToDocument(Document document, List<Request> requests) {
        Table table = createTable(requests);
        document.add(table);
    }

    private void addFooterToDocument(Document document, Procedure procedure) {
        Paragraph footer = new Paragraph("Diese Antrag wurde elektronisch gestellt am " +
                procedure.getCreatedAt() +
                " und zuletzt geändert am " +
                procedure.getLastUpdated())
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(footer);
    }

    private Table createTable(List<Request> requests) {
        Table table = new Table(UnitValue.createPercentArray(new float[]{25, 25, 25, 25}))
                .setWidth(UnitValue.createPercentValue(100))
                .setHorizontalAlignment(HorizontalAlignment.CENTER);

        addHeadersToTable(table);
        addDataRowsToTable(table, requests);
        return table;
    }

    private void addHeadersToTable(Table table) {
        String[] headers = {"Module External", "LP Internal", "Module Internal", "LP External"};
        for (String header : headers) {
            table.addCell(createHeaderCell(header));
        }
    }

    private void addDataRowsToTable(Table table, List<Request> requests) {
        for (Request request : requests) {
            table.addCell(createCell(request.getExternalModuleId()));
            table.addCell(createCell(String.valueOf(request.getCreditPoints())));
            table.addCell(createCell(request.getInternalModuleId()));
            table.addCell(createCell(String.valueOf(request.getCreditPoints()))); // LP External
        }
    }

    private Cell createHeaderCell(String content) {
        Style headerStyle = new Style()
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFontSize(12);

        return new Cell()
                .add(new Paragraph(content))
                .addStyle(headerStyle);
    }

    private Cell createCell(String content) {
        Style cellStyle = new Style()
                .setFontSize(12)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setTextAlignment(TextAlignment.CENTER);

        return new Cell()
                .add(new Paragraph(content))
                .addStyle(cellStyle);
    }

    //addQRCodeToDocument(document, procedure);


    private void generateQRCodeImage(Document document, String url) throws Exception {
        int qrCodeSize = 100; // Higher resolution for initial QR code
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(url);
        Image qrCodeImage = new Image(barcodeQRCode.createFormXObject(document.getPdfDocument()))
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setWidth(qrCodeSize)
                .setHeight(qrCodeSize);

        document.add(qrCodeImage);
    }


}

