package de.swtp13.creditportbackend.v1.procedures.util;

import de.swtp13.creditportbackend.v1.procedures.Procedure;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import de.swtp13.creditportbackend.v1.procedures.ProcedureService;
import de.swtp13.creditportbackend.v1.requests.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Optional;

public class PdfGenerator {
    private ProcedureRepository  procedureRepository;
    private RequestRepository requestRepository;

    private ProcedureService procedureService;



    public File generatePdf(int procedureId) {
        Optional<Procedure> procedure = procedureRepository.findByProcedureId(procedureId);
        return null;
    }




}
