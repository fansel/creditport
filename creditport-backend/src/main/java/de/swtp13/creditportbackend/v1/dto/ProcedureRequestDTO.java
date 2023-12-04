package de.swtp13.creditportbackend.v1.dto;

import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
public class ProcedureRequestDTO {

    private String id;
    private String procStatus;
    private String procAnnotation;
    private LocalDateTime procCreatedOn;
    private Integer requestId;
    private String externalModuleId;
    private String internalModuleId;
    private String reqAnnotation;
    private Integer creditPoints;
    private LocalDateTime reqCreatedOn;

    // Constructors, Getters, and Setters

    public ProcedureRequestDTO(String id, String procStatus, String procAnnotation, LocalDateTime procCreatedOn,
                               Integer requestId, String externalModuleId, String internalModuleId, String reqAnnotation,
                               Integer creditPoints, LocalDateTime reqCreatedOn) {
        this.id = id;
        this.procStatus = procStatus;
        this.procAnnotation = procAnnotation;
        this.procCreatedOn = procCreatedOn;
        this.requestId = requestId;
        this.externalModuleId = externalModuleId;
        this.internalModuleId = internalModuleId;
        this.reqAnnotation = reqAnnotation;
        this.creditPoints = creditPoints;
        this.reqCreatedOn = reqCreatedOn;
    }

    public String getId(){
        return id;
    }
    public String getProcStatus(){
        return procStatus;
    }
    public String getProcAnnotation(){
        return procAnnotation;
    }
    public LocalDateTime getProcCreatedOn(){
        return procCreatedOn;
    }
    public Integer getRequestId(){
        return requestId;
    }
    public String getExternalModuleId(){
        return externalModuleId;
    }
    public String getInternalModuleId(){
        return internalModuleId;
    }
    public String getReqAnnotation(){
        return reqAnnotation;
    }
    public Integer getCreditPoints() {
        return creditPoints;
    }
    public LocalDateTime getReqCreatedOn() {
        return reqCreatedOn;
    }
}