package de.swtp13.creditportbackend.v1.externalmodules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExternalModuleDTO {
    private UUID moduleId;
    private String moduleNumber;
    private String moduleName;
    private String moduleDescription;
    private UUID uniId;
    private double creditpoints;
}
