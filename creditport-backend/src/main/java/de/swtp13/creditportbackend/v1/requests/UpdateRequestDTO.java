package de.swtp13.creditportbackend.v1.requests;

import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDTO {
    private int procedureId;
    private int requestId;
    private List<ExternalModule> externalModules; // Änderung hier
    private List<InternalModule> internalModules; // Änderung hier
    private String annotationStudent;
    private String annotationCommittee;
    private StatusRequest statusRequest;
    private Instant createdAt;
    private boolean pdfExists;
    private String moduleLink;
}
