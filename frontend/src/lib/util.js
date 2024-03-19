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

/**
 * Überprüft, ob ein Datum zwischen zwei anderen Daten liegt, wobei null als das größte oder kleinste Datum betrachtet wird.
 * @param {Date} date - Das zu überprüfende Datum.
 * @param {Date} date1 - Das erste Grenzdatum.
 * @param {Date} date2 - Das zweite Grenzdatum.
 * @returns {boolean} - Gibt true zurück, wenn das Datum zwischen date1 und date2 liegt, andernfalls false.
 */
export function isDateBetween(date, date1, date2) {
  // Behandlung von null als größtes bzw. kleinste Datum
  if (date1 === null) date1 = new Date(Number.MIN_VALUE);
  if (date2 === null) date2 = new Date(Number.MAX_VALUE);

  // Überprüfen, ob das Datum zwischen date1 und date2 liegt
  return date >= date1 && date <= date2;
}

export function hasDefinedAttributes(obj) {
  let count = 0;
  
  function countAttributes(obj) {
      for (let key in obj) {
          if (typeof obj[key] === 'object') {
              countAttributes(obj[key]); // Rekursiver Aufruf für verschachtelte Objekte
          } else if (obj[key] !== undefined) {
              count++; // Zähle das Attribut, wenn es nicht undefined ist und kein Objekt
          }
      }
  }
  
  countAttributes(obj);
  return count;
}


