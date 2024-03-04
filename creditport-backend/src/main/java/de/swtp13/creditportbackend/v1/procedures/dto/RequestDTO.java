
package de.swtp13.creditportbackend.v1.procedures.dto;
import de.swtp13.creditportbackend.v1.internalmodules.InternalModule;
import de.swtp13.creditportbackend.v1.externalmodules.ExternalModule;
import lombok.Data;

import java.util.List;

@Data
public class RequestDTO {
    private List<ExternalModule> externalModules;
    private List<InternalModule> internalModules;
    private String annotationStudent;
    private String annotationCommittee;
    private int creditPoints;
    private String moduleLink;


}

