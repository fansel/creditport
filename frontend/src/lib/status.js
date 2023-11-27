export function getStatus(status) {
  let statusText;
  let statusColor;

  if (status === 1) {
    statusText = 'Neu';
    statusColor = 'secondary';
  } else if (status === 2) {
    statusText = 'Offen';
    statusColor = 'secondary';
  } else if (status === 3) {
    statusText = 'in Bearbeitung';
    statusColor = 'warning';
  } else if (status === 4) {
    statusText = 'Weitergeleitet';
    statusColor = 'warning';
  } else if (status === 5) {
    statusText = 'Vollständig';
    statusColor = 'success';
  } else if (status === 6) {
    statusText = 'Offen';
    statusColor = 'warning';
  } else if (status === 7) {
    statusText = 'Weitergeleitet';
    statusColor = 'warning';
  } else if (status === 8) {
    statusText = 'Rückfrage nötig';
    statusColor = 'warning';
  } else if (status === 9) {
    statusText = 'Angenommen';
    statusColor = 'success';
  } else if (status === 10) {
    statusText = 'Abgelehnt';
    statusColor = 'danger';
  }

  return {
    statusText: statusText,
    statusColor: statusColor
  };
}
