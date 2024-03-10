package de.swtp13.creditportbackend.v1.procedures.util;

import de.swtp13.creditportbackend.v1.procedures.ApplicationContextProvider;
import de.swtp13.creditportbackend.v1.procedures.ProcedureRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.security.SecureRandom;

public class IDGenerator implements IdentifierGenerator {

    private static final String CHARACTERS = "0123456789";
    private static final int ID_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return generateUniqueId();
    }

    private int generateId() {
        StringBuilder id = new StringBuilder(ID_LENGTH);

        id.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length() - 1) + 1));

        for (int i = 1; i < ID_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(randomIndex));
        }

        return Integer.parseInt(id.toString());
    }


    private int generateUniqueId() {
        int id = generateId();
        while (idExists(id)) {
            id = generateId();
        }
        return id;
    }

    private boolean idExists(int id) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        ProcedureRepository repository = applicationContext.getBean(ProcedureRepository.class);
        return repository.findByProcedureId(id).isPresent();
    }
}
