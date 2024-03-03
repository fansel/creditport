<script>
  // Jede StatusComponent hat Variablen zum:
  // 1. Beziehen von Daten - 2. Text für Header und Body - 3. Setzen der Farbe
  export let statusCount;
  export let header;
  export let body;
  export let color;
</script>

<div class="col-md">
  <div class="card">
    <div class="card-header" style="background-color: {color};">
      {header}
    </div>
    <div class="card-body">{statusCount}</div>
  </div>
</div>

<style>
</style>
