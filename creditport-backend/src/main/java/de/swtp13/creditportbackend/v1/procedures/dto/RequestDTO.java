
package de.swtp13.creditportbackend.v1.procedures.dto;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import lombok.Data;
@Data
public class RequestDTO {
    private ExternalModule externalModule;
    private InternalModule internalModule;
    private String annotationStudent;
    private String annotationCommittee;
    private int creditPoints;
    private String moduleLink;


}

