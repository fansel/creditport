export function formatProcdureID(id) {
  // Überprüfen, ob die Eingabe eine 6-stellige Zahl ist
  if (!/^\d{6}$/.test(id)) {
    return "ungültig";
  }

  // Aufteilen der Zahl in zwei Teile und Hinzufügen des Bindestrichs
  const formattedID = `${id.slice(0, 3)}-${id.slice(3, 6)}`;
  
  return formattedID;
}
