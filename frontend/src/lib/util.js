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
  const jsonPayload = decodeURIComponent(
    atob(base64)
      .split('')
      .map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      })
      .join('')
  );

  return JSON.parse(jsonPayload);
}

/**
 * Gibt den kleinsten Nachfolger von number aus dem array zurück
 * @param {number} number - Eine Natürliche Zahl
 * @param {number[]} array - Ein Array mit Zahlen, die potentielle Nachfolger von number sind
 * @example getNachfolger(12, [9, 11, 17]) // gibt 17 zurück, da 17 der kleinste Nachfolger von 12 ist
 * @returns {number|null} - Gibt einen Int aus dem array zurück oder null, wenn kein Nachfolger gefunden wurde
 */
export function getNachfolger(number, array) {
  let minNachfolger = null;

  for (let i = 0; i < array.length; i++) {
    const aktuellerNachfolger = array[i];

    if (aktuellerNachfolger > number && (minNachfolger === null || aktuellerNachfolger < minNachfolger)) {
      minNachfolger = aktuellerNachfolger;
    }
  }

  return minNachfolger;
}

/**
 * Gibt den größten Vorgänger von number aus dem array zurück
 * @param {number} number - Eine Natürliche Zahl
 * @param {number[]} array - Ein Array mit Zahlen, die potentielle Vorgänger von number sind
 * @example getVorgänger(12, [9,11,17]) // gibt 11 zurück, da 11 der größte Vorgänger von 12 ist
 * @returns {number|null} - Gibt einen Int aus dem array zurück oder null, wenn kein Vorgänger gefunden wurde
 */
export function getVorgänger(number, array) {
  let maxVorgänger = null;

  for (let i = 0; i < array.length; i++) {
    const aktuellerVorgänger = array[i];

    if (aktuellerVorgänger < number && (maxVorgänger === null || aktuellerVorgänger > maxVorgänger)) {
      maxVorgänger = aktuellerVorgänger;
    }
  }

  return maxVorgänger;
}

/**
 * Schneidet einen Text ab, wenn er länger als die angegebene maximale Länge ist.
 * @param {string} text - Der Text, der abgeschnitten werden soll.
 * @param {number} maxLength - Die maximale Länge des abgeschnittenen Texts.
 * @returns {string} - Der abgeschnittene Text, gegebenenfalls mit "..." am Ende.
 */
export function truncateText(text, maxLength) {
  // Überprüfen, ob die Länge des Texts größer als die maximale Länge ist
  if (text.length > maxLength) {
      // Wenn ja, schneide den Text ab und füge "..." hinzu
      return text.substring(0, maxLength) + "...";
  } else {
      // Andernfalls gebe den unveränderten Text zurück
      return text;
  }
}