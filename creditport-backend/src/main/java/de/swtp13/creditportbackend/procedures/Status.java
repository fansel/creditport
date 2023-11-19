package de.swtp13.creditportbackend.procedures;

/**
 * Die {@code Status} enum repräsentiert die unterschiedlichen Status der Vorgänge.
 * Sie wird verwendet, um den Vorgängen spezifische Status zuzuteilen für eine bessere Übersicht.
 * Die Status sind in der Reihenfolge, in welcher ein Vorgang diese haben wird; neu --> offen --> in Bearbeitung --> weitergeleitet --> vollständig
 */
public enum Status {
    /**
     * Der Status neu wird für Vorgänge verwendet, welche das Studienbüro noch nicht gesehen hat.
     */
    NEU,
    /**
     * Wird für Vorgänge verwendet, welche das Studienbüro schon mal gesehen hat, aber noch nicht angefangen hat zu bearbeiten.
     */
    OFFEN,
    /**
     * Vorgänge, bei denen schon einzelne Anträge bearbeitet wurden
     *
     * Dieser Status ist vom Antragsteller sichtbar.
     */
    IN_BEARBEITUNG,
    /**
     * Alle Anträge in diesem Vorgang wurden vom Studienbüro bearbeitet und wurde an den Prüfungsausschuss weitergeleitet zur Bearbeitung.
     */
    WEITERGELEITET,
    /**
     * alle Anträge in dem Vorgang wurden vom Prüfungsausschuss bearbeitet und es wurde eine begründete Entscheidung getroffen.
     */
    VOLLSTÄNDIG;
}
