package de.swtp13.creditportbackend.v1.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ProcedureRequestDTO {

    private String id;
    private String procStatus;
    private String procAnnotation;
    private LocalDateTime procCreatedAt;
    private Integer requestId;
    private String externalModuleId;
    private String internalModuleId;
    private String reqAnnotation;
    private Integer creditPoints;
    private LocalDateTime reqCreatedAt;

}