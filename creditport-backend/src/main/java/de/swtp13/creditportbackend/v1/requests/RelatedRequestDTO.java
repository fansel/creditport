package de.swtp13.creditportbackend.v1.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedRequestDTO {
    private int procedureId;
    private int requestId;
    private String externalModule;
    private String internalModule;
    private String annotation;
    private int creditPoints;
    private Status status;
    private Instant createdAt;
    private byte[] pdfContent;


    private List<Integer> relatedRequests;
}