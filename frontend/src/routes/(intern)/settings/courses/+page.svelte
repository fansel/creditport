<script>
  import VirtualList from '@sveltejs/svelte-virtual-list';

  export let data;

  let start;
  let end;

  function test() {}
</script>

<div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
  <h4 class="m-0">Interne Studiengänge</h4>

  <div class="d-flex flex-wrap gap-2">
    <button class="btn btn-primary btn-sm text-nowrap" on:click={test}>
      <i class="bi bi-plus-circle" />
      Hinzufügen
    </button>
    <button class="btn btn-primary btn-sm" on:click={test}>
      <i class="bi bi-cloud-arrow-up" />
      Importieren
    </button>
  </div>
</div>

<ul class="list-group mb-3 uni-table border">
  <li class="uni-table-header border-bottom font-sm">
    <div class="row">
      <div class="col-8">Name</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  <div class="uni-table-body">
    <VirtualList items={data.courses} height="332px" bind:start bind:end let:item>
      <li class="uni-table-item border-bottom">
        <div class="row gy-2">
          <div class="col col-md-8 d-flex align-items-center">
            <button class="btn btn-link text-decoration-none">
              {item.courseName}
              ({item.internalModules.length} Module)
            </button>

            <!-- {#if item.verified}
              <i class="bi bi-check-circle ms-2 text-primary" />
            {/if} -->
          </div>
          <div class="col-auto col-md d-flex align-items-center">
            <!-- <form method="POST" use:enhance>
              <input type="hidden" name="id" value={item.uniId} /> -->

            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary" on:click={test}>Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger" on:click={test}>Löschen</button>
            </div>
            <!-- </form> -->
          </div>
        </div>
      </li>
    </VirtualList>
  </div>
</ul>

<div class="d-flex justify-content-between align-items-center mb-2 flex-wrap gap-2">
  <h4 class="m-0">Interne Module</h4>

  <div class="d-flex flex-wrap gap-2">
    <button class="btn btn-primary btn-sm text-nowrap" on:click={test}>
      <i class="bi bi-plus-circle" />
      Hinzufügen
    </button>
    <button class="btn btn-primary btn-sm" on:click={test}>
      <i class="bi bi-cloud-arrow-up" />
      Importieren
    </button>
  </div>
</div>
<p>Wähle einen Studiengang aus um die dazugehörigen Module zu bearbeiten.</p>
<ul class="list-group mb-3 uni-table border">
  <li class="uni-table-header border-bottom font-sm">
    <div class="row">
      <div class="col-8">Name</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  <div class="uni-table-body">
    <VirtualList items={data.modules} height="332px" let:item>
      <li class="uni-table-item border-bottom">
        <div class="row gy-2">
          <div class="col col-md-8 d-flex align-items-center">
            {item.moduleName}
            <!-- ({item.internalModules.length} Module) -->
            <!-- {#if item.verified}
              <i class="bi bi-check-circle ms-2 text-primary" />
            {/if} -->
          </div>
          <div class="col-auto col-md d-flex align-items-center">
            <!-- <form method="POST" use:enhance>
              <input type="hidden" name="id" value={item.uniId} /> -->

            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary" on:click={test}>Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger" on:click={test}>Löschen</button>
            </div>
            <!-- </form> -->
          </div>
        </div>
      </li>
    </VirtualList>
  </div>
</ul>

<style>
  .no-wrap {
    white-space: nowrap;
  }

  .font-sm {
    font-size: 0.875rem;
  }

  .uni-table {
    list-style-type: none;
  }

  .uni-table-header {
    width: 100%;
    padding: 0.5rem 1rem;
  }

  .uni-table-body {
    overflow-y: auto;
  }

  .uni-table-item {
    /* border-bottom: 1px solid rgb(222, 226, 230); */
    width: 100%;
    padding: 0.5rem 1rem;
  }

  .uni-table-item:hover {
    background-color: rgba(0, 0, 0, 0.075);
  }
</style>
