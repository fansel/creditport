package de.swtp13.creditportbackend.v1.pdf;

import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
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

    public PdfService() throws IOException {
    }

    public Optional<byte[]> createOverview(int procedureId) {
        Optional<Procedure> procedureOptional = procedureRepository.findById(procedureId);
        if (!procedureOptional.isPresent()) {
            return Optional.empty();
        }

        Procedure procedure = procedureOptional.get();
        //List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(byteArrayOutputStream);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            applyTemplateToDocument(document);
            setDocumentMargins(document);
            addHeadingToDocument(document, procedure);
            addTableToDocument(document, procedure.getRequests());
            addQRCodeWithBox(document,"http://localhost:5173/status/"+procedureId);
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
        document.setMargins(150, 20, 20, 20);
    }

    private void addHeadingToDocument(Document document, Procedure procedure) {
        String firstHaldOfProcedureId = (String.valueOf(procedure.getProcedureId())).substring(0, 3);
        String secondHaldOfProcedureId = (String.valueOf(procedure.getProcedureId())).substring(3);
        String procedureIdShow = firstHaldOfProcedureId + "-" + secondHaldOfProcedureId;
        Paragraph heading = new Paragraph("Dein Antrag: " + procedureIdShow )
                .setTextAlignment(TextAlignment.CENTER)
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
                .addStyle(normalStyle)
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

        String[] headers = {"Modul Name (fremd)", "LP", "Modul Name", "LP"};
        for (String header : headers) {
            table.addCell(createHeaderCell(header));
        }
    }

    private void addDataRowsToTable(Table table, List<Request> requests) {
        for (Request request : requests) {
            table.addCell(createCell(String.valueOf(request.getExternalModuleIds())));
            table.addCell(createCell(String.valueOf(request.getCreditPoints())));
            table.addCell(createCell(String.valueOf(request.getInternalModuleIds())));
            table.addCell(createCell(String.valueOf(request.getCreditPoints()))); // LP External
        }
    }

    private Cell createHeaderCell(String content) {
        Style headerStyle = new Style()
                .setBackgroundColor(new DeviceRgb(200, 200, 200)) // Grauer Hintergrund
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setFont(font)
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


    private void addQRCodeWithBox(Document document, String url) throws Exception {
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(url);
        Image qrCodeImage = new Image(barcodeQRCode.createFormXObject(document.getPdfDocument()))
                .setWidth(100)
                .setHeight(100)
                .setBorderRadius(new BorderRadius(10))
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        Paragraph qrText = new Paragraph("Deinen Status abfragen:")
                .setFontSize(14)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT);  // Text linksbündig

        Div qrCodeContainer = new Div()
                .add(qrCodeImage).setHorizontalAlignment(HorizontalAlignment.RIGHT)
                .add(qrText).setVerticalAlignment(VerticalAlignment.MIDDLE).setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setMarginTop(20)
                .setBorderRadius(new BorderRadius(10))
                .setBackgroundColor(new DeviceRgb(200, 200, 200))
                .setPadding(10)
                .setMarginLeft(10)
                .setHeight(100); // Abstand zwischen Text und QR-Code



        document.add(qrCodeContainer);
    }



    PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    Style normalStyle = new Style()
            .setFont(font)
            .setFontSize(12);

    Style headingStyle = new Style()
            .setFont(font)
            .setFontSize(16)
            .setBold();





}

