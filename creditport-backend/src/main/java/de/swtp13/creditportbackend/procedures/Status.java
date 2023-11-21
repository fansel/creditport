package de.swtp13.creditportbackend.procedures;

/**
 * Die {@code Status} enum repräsentiert die unterschiedlichen Status der Vorgänge.
 * Sie wird verwendet, um den Vorgängen spezifische Status zuzuteilen für eine bessere Übersicht.
 * Die Status sind in der Reihenfolge, in welcher ein Vorgang diese haben wird; neu --> offen --> in Bearbeitung --> weitergeleitet --> vollständig
 */
public enum Status {
    /**
     * NEU (Studienbüro)
     * Der Status neu wird für Vorgänge verwendet, welche das Studienbüro noch nicht gesehen hat.
     */
    1,
    /**
     * OFFEN (Studienbüro)
     * Wird für Vorgänge verwendet, welche das Studienbüro schon mal gesehen hat, aber noch nicht angefangen hat zu bearbeiten.
     */
    2,
    /**
     * IN BEARBEITUNG (Studienbüro, Antragsteller_in)
     * Vorgänge, bei denen schon einzelne Anträge bearbeitet wurden
     */
    3,
    /**
     * WEITERGELEITET (Studienbüro), VORBEREITET (Prüfungsausschuss)
     * Alle Anträge in diesem Vorgang wurden vom Studienbüro bearbeitet und wurde an den Prüfungsausschuss weitergeleitet zur Bearbeitung.
     */
    4,
    /**
     * VOLLSTÄNDIG (Prüfungsausschuss)
     * Alle Anträge in dem Vorgang wurden vom Prüfungsausschuss bearbeitet und es wurde eine begründete Entscheidung getroffen.
     */
    5;
}
