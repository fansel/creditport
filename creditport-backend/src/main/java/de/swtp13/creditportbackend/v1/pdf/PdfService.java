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
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.*;
import com.itextpdf.io.source.ByteArrayOutputStream;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


@Service
public class PdfService {



    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private RequestRepository requestRepository;

    private Style normalStyle = null;
    private Style headingStyle = null;
    private PdfFont font = null;

    public PdfService() throws IOException {
    }

    public Optional<byte[]> createOverview(int procedureId) throws IOException {
        Optional<Procedure> procedureOptional = procedureRepository.findById(procedureId);
        if (!procedureOptional.isPresent()) {
            return Optional.empty();
        }
        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        normalStyle = new Style()
                .setFont(font)
                .setFontSize(12);
        headingStyle = new Style()
                .setFont(font)
                .setFontSize(16)
                .setBold();

        Procedure procedure = procedureOptional.get();
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(byteArrayOutputStream);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            applyTemplateToDocument(document);
            setDocumentMargins(document);
            //addHeadingToDocument(document, procedure);
            procedureDetails(document, procedure);
            addTableToDocument(document, requests);
            addQRCodeWithBox(document,"http://localhost:5173/status/"+procedureId);
            addFooterToDocument(document, procedure);
            document.close();
            pdfDoc.close();

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
        try {

            String firstHaldOfProcedureId = (String.valueOf(procedure.getProcedureId())).substring(0, 3);
            String secondHaldOfProcedureId = (String.valueOf(procedure.getProcedureId())).substring(3);
            String procedureIdShow = firstHaldOfProcedureId + "-" + secondHaldOfProcedureId;
            Paragraph heading = new Paragraph("Dein Antrag: " + procedureIdShow )
                    .setTextAlignment(TextAlignment.CENTER)
                    .addStyle(headingStyle);
            document.add(heading);
            System.out.println("Added heading to document wihout error");
        } catch (Exception e) {
            System.out.println("Error while adding heading to document: " + e.getMessage());
        }
    }


    private void procedureDetails(Document document, Procedure procedure) {
        // Überschrift für den Abschnitt
        Paragraph heading = new Paragraph("Verfahrensdetails")
                .setFontSize(14)
                .setBold()
                .setUnderline()
                .setMarginBottom(10);
        document.add(heading);

        // ID und Status des Verfahrens
        Paragraph idAndStatus = new Paragraph()
                .add(new Text("Verfahrens-ID: ").setBold())
                .add(String.valueOf(procedure.getProcedureId()) + "\n")
                .setMarginBottom(5);
        document.add(idAndStatus);

        // Anmerkungen zum Verfahren
        if (procedure.getAnnotation() != null && !procedure.getAnnotation().isEmpty()) {
            Paragraph annotations = new Paragraph()
                    .add(new Text("Anmerkungen: ").setBold())
                    .add(procedure.getAnnotation() + "\n")
                    .setMarginBottom(5);
            document.add(annotations);
        }

        // Universitätsinformationen, wenn vorhanden
        if (procedure.getUniversity() != null) {
            Paragraph universityInfo = new Paragraph()
                    .add(new Text("Universität: ").setBold())
                    .add(procedure.getUniversity().getUniName() + "\n")
                    .setMarginBottom(5);
            document.add(universityInfo);
        }

        // Kursinformationen, wenn vorhanden
        if (procedure.getCourse() != null) {
            Paragraph courseInfo = new Paragraph()
                    .add(new Text("Studiengang: ").setBold())
                    .add(procedure.getCourse().getCourseName() + "\n")
                    .setMarginBottom(5);
            document.add(courseInfo);
        }
    }

    private void addTableToDocument(Document document, List<Request> requests) {
        Table table = createTable(requests);
        document.add(table);
    }

    private void addFooterToDocument(Document document, Procedure procedure) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'um' HH:mm 'Uhr'");

        // Konvertieren und Formatieren von procedure.getCreatedAt()
        LocalDateTime createdDateTime = LocalDateTime.ofInstant(procedure.getCreatedAt(), ZoneId.systemDefault());
        String formattedCreatedDate = createdDateTime.format(formatter);

        // Konvertieren und Formatieren von procedure.getLastUpdated()
        LocalDateTime updatedDateTime = LocalDateTime.ofInstant(procedure.getLastUpdated(), ZoneId.systemDefault());
        String formattedUpdatedDate = updatedDateTime.format(formatter);

        // Erstellen des Paragraphs mit den formatierten Datumswerten
        Paragraph footer = new Paragraph("Dieser Antrag wurde elektronisch gestellt am " +
                formattedCreatedDate +
                " und zuletzt geändert am " +
                formattedUpdatedDate)
                .addStyle(normalStyle)
                .setTextAlignment(TextAlignment.LEFT);

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
            List<ExternalModule> externalModules = request.getExternalModules();
            List<InternalModule> internalModules = request.getInternalModules();
            int externalSize = externalModules.size();
            int internalSize = internalModules.size();
            int maxModules = Math.max(externalSize, internalSize);

            for (int i = 0; i < maxModules; i++) {
                ExternalModule externalModule = null;
                InternalModule internalModule = null;

                // Externe Moduldaten hinzufügen oder duplizieren
                if (externalSize > 0) {
                    externalModule = externalModules.get(i % externalSize);
                    table.addCell(createCell(externalModule.getModuleName()));
                    table.addCell(createCell(String.valueOf(externalModule.getCreditPoints())));
                } else {
                    table.addCell(createCell(""));
                    table.addCell(createCell(""));
                }

                // Interne Moduldaten hinzufügen oder duplizieren
                if (internalSize > 0) {
                    internalModule = internalModules.get(i % internalSize);
                    table.addCell(createCell(internalModule.getModuleName()));
                    table.addCell(createCell(String.valueOf(internalModule.getCreditPoints())));
                } else {
                    table.addCell(createCell(""));
                    table.addCell(createCell(""));
                }
            }
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
        // QR-Code erstellen
        BarcodeQRCode barcodeQRCode = new BarcodeQRCode(url);
        Image qrCodeImage = new Image(barcodeQRCode.createFormXObject(document.getPdfDocument()))
                .setWidth(100)
                .setHeight(100)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT); // QR-Code rechtsbündig

        // Text für QR-Code, Schriftgröße erhöht
        Paragraph qrText = new Paragraph("Deinen Status abfragen:")
                .setFontSize(18) // Schriftgröße erhöht
                .setBold();

        // Tabelle zur Anordnung von Text und QR-Code
        float[] columnWidths = {1, 2}; // Verhältnis der Spaltenbreite angepasst, um mehr Platz für den QR-Code zu schaffen
        Table table = new Table(UnitValue.createPercentArray(columnWidths))
                .useAllAvailableWidth(); // Tabelle verwendet die ganze verfügbare Breite

        // Text in die erste Zelle der Tabelle hinzufügen
        Cell textCell = new Cell().add(qrText)
                .setBorder(Border.NO_BORDER) // Keine Zellenumrandung
                .setVerticalAlignment(VerticalAlignment.MIDDLE) // Vertikale Ausrichtung in der Mitte
                .setTextAlignment(TextAlignment.LEFT) // Text linksbündig ausrichten
                .setPaddingRight(20); // Rechter Abstand, um den QR-Code näher an den Rand zu bringen
        table.addCell(textCell);

        // QR-Code in die zweite Zelle der Tabelle hinzufügen
        Cell qrCell = new Cell().add(new Paragraph()) // Füge einen leeren Absatz hinzu, um den QR-Code an den Rand zu drücken
                .add(qrCodeImage)
                .setBorder(Border.NO_BORDER) // Keine Zellenumrandung
                .setPaddingLeft(10) // Platz zwischen Text und QR-Code
                .setVerticalAlignment(VerticalAlignment.MIDDLE) // Vertikale Ausrichtung in der Mitte
                .setPaddingRight(10); // Geringer Abstand vom Rand
        table.addCell(qrCell);

        // Container für die Tabelle
        Div qrCodeContainer = new Div()
                .add(table)
                .setBackgroundColor(new DeviceRgb(200, 200, 200)) // Hintergrundfarbe
                .setPadding(10) // Innenabstand
                .setMarginTop(20) // Abstand nach oben
                .setBorderRadius(new BorderRadius(10)) // Ecken abrunden
                .setWidth(UnitValue.createPercentValue(100)); // Breite auf 100% setzen

        // Container zum Dokument hinzufügen
        document.add(qrCodeContainer);
    }






}







