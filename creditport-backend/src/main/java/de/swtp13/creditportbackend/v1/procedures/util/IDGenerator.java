package de.swtp13.creditportbackend.v1.procedures.util;

import java.security.SecureRandom;

public class IDGenerator {
    private static final String CHARACTERS = "0123456789";
    private static final int ID_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static int generateID() {
        StringBuilder id = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(randomIndex));
        }
        return Integer.parseInt(id.toString());
    }
}
