package de.swtp13.creditportbackend.requests;

/**
 * Die {@code Status} enum repräsentiert die unterschiedlichen Status der Anträge in einem Vorgang.
 * Sie wird verwendet, um den Anträgen spezifische Status zuzuteilen für eine bessere Übersicht.
 */
public enum Status {
    /**
     * NICHT_BEARBEITET (Studienbüro)
     * Der Antrag wurde vom Studienbüro noch nicht bearbeitet.
     */
    NICHT_BEARBEITET(6),
    /**
     * BEARBEITET (Studienbüro)
     * Der Antrag wurde bearbeitet, kann an den Prüfungsausschuss weitergeleitet werden
     */
    BEARBEITET(7),
    /**
     * RÜCKFRAGE_NÖTIG (Studienbüro, Antragsteller_in)
     * Das ist ein Sonderstatus, wenn zu den Anträgen noch Informationen fehlen, kann das Studienbüro den Antrag manuell auf
     * "Rückfrage nötig" stellen, was dann dem Antragsteller angezeigt wird.
     */
    RÜCKFRAGE_NÖTIG(8),
    /**
     * ANGENOMMEN (Prüfungsausschuss, Antragsteller_in)
     * Der Prüfungsausschuss hat den Antrag als korrekt bestätigt.
     */
    ANGENOMMEN(9),
    /**
     * ABGELEHNT (Prüfungsausschuss, Antragsteller_in)
     * Der Prüfungsausschuss lehnt den Antrag ab.
     */
    ABGELEHNT(10);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
