<script>
  // Jede StatusComponent hat Variablen zum:
  // 1. Beziehen von Daten - 2. Text für Header und Body - 3. Setzen der Farbe
  export let statusCount;
  export let header;
  export let body;
  export let colorTag;
</script>

<div class="col">
  <div class="card">
    <div class="card-header bg-{colorTag}">
      {header}
    </div>
    <div class="card-body">{statusCount}</div>
  </div>
</div>

<style>
</style>
