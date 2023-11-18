<script>
  export let universities;
</script>

<div class="row mb-3">
  <div class="col"><h4>Vorschlagliste</h4></div>
  <div class="col-8">
    <div class="form-inline d-flex align-items-center">
      <input type="text" placeholder="Suche" class="form-control form-control-sm" />
    </div>
  </div>
</div>

<ul class="list-group mb-4 uni-table border">
  <li class="uni-table-header border-bottom font-sm">
    <div class="row">
      <div class="col-9">Name</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  <div class="uni-table-body">
    {#each universities as uni}
      <li class="uni-table-item">
        <div class="row">
          <div class="col-9 d-flex align-items-center">
            {uni.uniName}
          </div>
          <div class="col d-flex align-items-center">
            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary">Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger">Löschen</button>
            </div>
          </div>
        </div>
      </li>
    {/each}
  </div>
</ul>

<button class="btn btn-primary mt-2 mb-2">Speichern</button>

<style>
  .uni-table {
    max-height: 330px;
    overflow: hidden;
  }

  .uni-table-header {
    width: 100%;
    padding: 0.5rem 1rem;
  }

  .uni-table-body {
    overflow-y: auto;
  }

  .uni-table-item {
    border-width: 0 0 1px;
    border-color: #dee2e6;
    border-bottom: 1px solid #dee2e6;
    width: 100%;
    padding: 0.5rem 1rem;
  }

  .uni-table-item:hover {
    background-color: rgba(0, 0, 0, 0.075);
  }

  .uni-table-item:last-child {
    border-bottom-width: 0;
  }
</style>
