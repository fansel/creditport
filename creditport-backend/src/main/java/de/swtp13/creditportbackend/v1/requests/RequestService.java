package de.swtp13.creditportbackend.v1.requests;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.procedures.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private ProcedureRepository procedureRepository;
    @Autowired
    private RequestRepository requestRepository;

    public void setProcedureStatus(int requestId){
        Procedure procedure = requestRepository.findProcedureByRequestId(requestId);
        boolean bearbeitet = false;
        List<Request> requests = requestRepository.findRequestsByProcedureId(procedure.getProcedureId());
        for (Request request : requests){
            if (request.getStatusRequest().equals(StatusRequest.BEARBEITET)) {
                bearbeitet = true;
                break;
            }
        }
        if ((procedure.getStatus().equals(Status.OFFEN) || procedure.getStatus().equals(Status.NEU)) && bearbeitet){
            procedure.setStatus(Status.IN_BEARBEITUNG);
            procedureRepository.save(procedure);
            return;
        }

        else {
            boolean entschieden = true;
            for (Request request : requests) {
                if (request.getStatusRequest().equals(StatusRequest.ANGENOMMEN) || request.getStatusRequest().equals(StatusRequest.ABGELEHNT)) {
                    entschieden = false;
                    break;
                }
            }
            if (procedure.getStatus().equals(Status.WEITERGELEITET) && entschieden) {
                procedure.setStatus(Status.VOLLSTÄNDIG);
                procedureRepository.save(procedure);
            }
        }
    }

    public void setFavored(Request request){
        request.setFavored(!request.isFavored());
    }
}
