
package de.swtp13.creditportbackend.v1.procedures.dto;
import lombok.Data;
@Data
public class RequestDTO {
    private String externalModuleId;
    private String internalModuleId;
    private String annotationStudent;
    private String annotationCommittee;
    private int creditPoints;
    private String moduleLink;


}

