package de.swtp13.creditportbackend.v1.dto;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * Die Klasse ist ein DTO (Data Transfer Object) für alle relevanten Infos die Vorgänge und Anträge enthalten.
 */
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