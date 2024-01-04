package de.swtp13.creditportbackend.v1.pdf;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PdfService {

    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private RequestRepository requestRepository;

    public Optional<byte[]> createOverview(int procedureId) throws IOException {
        // Load the procedure and requests
        Optional<Procedure> procedureOptional = procedureRepository.findById(procedureId);
        if (procedureOptional.isEmpty()) {
            return Optional.empty();
        }
        Procedure procedure = procedureOptional.get();
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedureId);

        // Load the PDF template and generate the PDF
        try (PDDocument document = Loader.loadPDF(new File("template.pdf"))) {
            PDPage page = document.getPage(0);


            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                    contentStream.newLineAtOffset(25, 750);
                    contentStream.showText("Dein Antrag: " + procedure.getProcedureId());
                    contentStream.endText();

                    // Table header
                    String[] headers = {"Module External", "LP Internal", "Module Internal", "LP External"};
                    float margin = 50;
                    float yStart = 700;
                    float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
                    float rowHeight = 20;
                    float tableTopY = yStart - (rowHeight / 2);

                    // Draw the rows
                    float nexty = tableTopY;
                    for (int i = 0; i <= requests.size(); i++) {
                        contentStream.moveTo(margin, nexty);
                        contentStream.lineTo(margin + tableWidth, nexty);
                        contentStream.stroke();
                        nexty -= rowHeight;
                    }

                    // Draw the columns
                    float nextx = margin;
                    for (int i = 0; i < headers.length; i++) {
                        contentStream.moveTo(nextx, tableTopY);
                        contentStream.lineTo(nextx, tableTopY - (requests.size() * rowHeight));
                        contentStream.stroke();
                        nextx += tableWidth / headers.length;
                    }

                    // Filling table content
                    float textx = margin + 5;
                    float texty = tableTopY - 15;
                    for (int i = 0; i < requests.size(); i++) {
                        Request request = requests.get(i);
                        String[] requestDetails = {
                                request.getExternalModuleId(),
                                String.valueOf(request.getCreditPoints()), // Assuming LP Internal is CreditPoints
                                request.getInternalModuleId(),
                                "LP External Value" // Placeholder for LP External
                        };

                        for (String text : requestDetails) {
                            contentStream.beginText();
                            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                            contentStream.newLineAtOffset(textx, texty);
                            contentStream.showText(text);
                            contentStream.endText();
                            textx += tableWidth / headers.length;
                        }
                        texty -= rowHeight;
                        textx = margin + 5;
                    }

                    // Footer
                    contentStream.beginText();
                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                    contentStream.newLineAtOffset(400, 50);
                    String footerText = "Diese Antrag wurde elektronisch gestellt am" +
                            procedure.getCreatedAt() +
                            " und zuletzt geändert am " +
                            procedure.getLastUpdated();
                    contentStream.showText(footerText);
                    contentStream.endText();
                }

                // Save and return the PDF as a byte array
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                document.save(byteArrayOutputStream);
                return Optional.of(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                System.out.println("Error while creating PDF: " + e.getMessage());
                return Optional.empty();
            }
    }

}
