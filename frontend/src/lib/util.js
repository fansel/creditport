export function formatProcdureID(id) {
  // Überprüfen, ob die Eingabe eine 6-stellige Zahl ist
  if (!/^\d{6}$/.test(id)) {
    return 'ungültig';
  }

  // Aufteilen der Zahl in zwei Teile und Hinzufügen des Bindestrichs
  const formattedID = `${id.slice(0, 3)}-${id.slice(3, 6)}`;

  return formattedID;
}

export function parseJwt(token) {
  const base64Url = token.split('.')[1];
  const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
    return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
  }).join(''));

  return JSON.parse(jsonPayload);
}
