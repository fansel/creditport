package de.swtp13.creditportbackend.requests;

/**
 * Die {@code Status} enum repräsentiert die unterschiedlichen Status der Anträge in einem Vorgang.
 * Sie wird verwendet, um den Anträgen spezifische Status zuzuteilen für eine bessere Übersicht.
 */
public enum Status {
    /**
     * Der Antrag wurde vom Studienbüro noch nicht bearbeitet.
     */
    NICHT_BEARBEITET,
    /**
     * Der Antrag wurde bearbeitet, kann an den Prüfungsausschuss weitergeleitet werden
     */
    BEARBEITET,
    /**
     * Das ist ein Sonderstatus, wenn zu den Anträgen noch Informationen fehlen, kann das Studienbüro den Antrag manuell auf
     * "Rückfrage nötig" stellen, was dann dem Antragsteller angezeigt wird.
     */
    RÜCKFRAGE_NÖTIG,
    /**
     * Der Prüfungsausschuss hat den Antrag als korrekt bestätigt.
     *
     * Dieser Status ist vom Antragsteller sichtbar.
     */
    ANGENOMMEN,
    /**
     * Der Prüfungsausschuss lehnt den Antrag ab.
     *
     * Dieser Status ist vom Antragsteller sichtbar.
     */
    ABGELEHNT;
}
