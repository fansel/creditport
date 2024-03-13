
package de.swtp13.creditportbackend.v1.procedures.dto;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RequestDTO {
    private List<UUID> externalModuleId;
    private List<UUID> internalModuleId;
    private String annotationStudent;
    private String annotationCommittee;
    private String moduleLink;


}

