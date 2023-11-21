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
    6,
    /**
     * BEARBEITET (Studienbüro)
     * Der Antrag wurde bearbeitet, kann an den Prüfungsausschuss weitergeleitet werden
     */
    7,
    /**
     * RÜCKFRAGE_NÖTIG (Studienbüro, Antragsteller_in)
     * Das ist ein Sonderstatus, wenn zu den Anträgen noch Informationen fehlen, kann das Studienbüro den Antrag manuell auf
     * "Rückfrage nötig" stellen, was dann dem Antragsteller angezeigt wird.
     */
    8,
    /**
     * ANGENOMMEN (Prüfungsausschuss, Antragsteller_in)
     * Der Prüfungsausschuss hat den Antrag als korrekt bestätigt.
     */
    9,
    /**
     * ABGELEHNT (Prüfungsausschuss, Antragsteller_in)
     * Der Prüfungsausschuss lehnt den Antrag ab.
     */
    10;
}
