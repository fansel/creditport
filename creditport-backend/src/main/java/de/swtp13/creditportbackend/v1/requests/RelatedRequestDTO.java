package de.swtp13.creditportbackend.v1.requests;

import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedRequestDTO {
    private int procedureId;
    private int requestId;
    private ExternalModule externalModule;
    private InternalModule internalModule;
    private String annotationStudent;
    private String annotationCommittee;
    private int creditPoints;
    private StatusRequest statusRequest;
    private Instant createdAt;
    private boolean pdfExists;


    private List<Integer> relatedRequests;
}