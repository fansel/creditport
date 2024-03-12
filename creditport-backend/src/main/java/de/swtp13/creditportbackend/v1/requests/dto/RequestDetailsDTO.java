package de.swtp13.creditportbackend.v1.requests.dto;

import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.requests.Request;
import de.swtp13.creditportbackend.v1.requests.StatusRequest;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@Data
public class RequestDetailsDTO {
    private int requestId;
    private List<ExternalModule> externalModules = new ArrayList<>();
    private List<InternalModule> internalModules= new ArrayList<>();
    private String annotationStudent;
    private String annotationCommittee;
    private StatusRequest statusRequest;
    private Instant createdAt;
    private boolean pdfExists;
    private String moduleLink;

    // Konstruktoren, Getter und Setter
}
