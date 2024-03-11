package de.swtp13.creditportbackend.v1.requests;

import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import lombok.Data;
import org.postgresql.jdbc.UUIDArrayAssistant;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class UpdateRequestDTO {
    private int procedureId;
    private int requestId;
    private List<UUID> externalModuleIds;
    private List<UUID> internalModuleIds;
    private String annotationStudent;
    private String annotationCommittee;
    private StatusRequest statusRequest;
    private Instant createdAt;
    private boolean pdfExists;
    private String moduleLink;
}
