package de.swtp13.creditportbackend.procedures;

public enum Status {
    NEU,
    OFFEN,
    IN_BEARBEITUNG,
    WEITERGELEITET,
    VOLLSTÄNDIG; //wenn alle Anträge angenommen/ abgelehnt wurden
}
