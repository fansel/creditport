
package de.swtp13.creditportbackend.v1.procedures.dto;
import lombok.Data;
@Data
public class RequestDTO {
    private String externalModule;
    private String internalModule;
    private String annotation;
    private int creditPoints;


}

