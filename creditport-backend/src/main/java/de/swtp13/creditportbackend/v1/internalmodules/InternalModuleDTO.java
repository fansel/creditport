package de.swtp13.creditportbackend.v1.internalmodules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalModuleDTO {
    private UUID id;
private List<UUID> courseIds;
private String number;
private String moduleName;
private String moduleDescription;
private int creditPoints;
}
