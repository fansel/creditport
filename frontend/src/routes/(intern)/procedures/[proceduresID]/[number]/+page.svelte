<!-- Hauptbearbeitungsseite mit PDF Vorschau -->
<script>
  export let data;

  const modules = data.modules;
  let selectedModul = 0;

  // importing Requests modal (pop up)
  import Modal from '$lib/components/Modal.svelte';

  let showModal = false;
</script>

<div class="site position-fixed">
  <div class="nav-bar my-3" width="100%">
    <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='https://google.com'">
      <i class="bi bi-arrow-left" />
    </button>
    <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='https://youtube.com'">
      <i class="bi bi-arrow-right" />
    </button>
    <button type="button" class="btn btn-sm btn-outline-primary" on:click={() => (showModal = true)}> ähnliche Anträge </button>
  </div>

  <div class="row p-3 w-100">
    <div class="col">
      <div class="pdf-container">
        <div class="pdf-content"><iframe src="https://www.orimi.com/pdf-test.pdf" width="700" height="750" /></div>
      </div>
    </div>

    <div class="col">
      <form action="">
        <div class="mb-3">
          <label for="" class="mb-2"><strong>Modulvorschlag</strong></label>
          <select class="form-select" aria-label="Default select example" bind:value={selectedModul}>
            {#each modules as modul, index}
              <option value={index}>{modul.moduleName}</option>
            {/each}
          </select>
        </div>

        <div class="form-row">
          <div class="form-group col">
            <label><strong>Bemerkungsfeld</strong></label>
            <input type="email" class="form-control" id="inputEmail4" placeholder="Begründen Sie Ihren Entscheid" />
          </div>
        </div>
      </form>
    </div>
  </div>
</div>

<Modal bind:showModal>
  <h2 slot="headline" class="m-0">ähnliche Anträge</h2>

  <!-- body -->
  <!-- alle Fremdmodule für dieses Modul -->

  <form action="" slot="body" class="p-3">
    <div class="row form-inline d-flex align-items-center no-wrap mb-3">
      <div class="col-4">
        <strong>akzeptierte Fremdmodule </strong>
      </div>
      <div class="col-8">
        <input type="text" placeholder="Suche" class="form-control form-control-sm" />
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-sm table-hover table-responsive border align-middle shadow-sm">
        <thead>
          <tr>
            <th>Datum</th>
            <th>Universität</th>
            <th>Modulname</th>
            <th>Status</th>
            <th />
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>22.11.23 </td>
            <td>Universität Halle</td>

            <td>Mathe I</td>
            <td><span class="badge bg-success-subtle border border-success-subtle text-secondary-emphasis rounded-pill">angenommen</span></td>

            <td>
              <div class="btn-group text-nowrap float-end" role="group" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>

  <!-- footer -->

  <form action="" slot="footer" class="p-3">
    <div class="row form-inline d-flex align-items-center no-wrap mb-3">
      <div class="col-4">
        <strong>akzeptiert für Modul</strong>
      </div>
      <div class="col-8">
        <input type="text" placeholder="Suche" class="form-control form-control-sm" />
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-sm table-hover table-responsive border align-middle shadow-sm">
        <thead>
          <tr>
            <th>Datum</th>
            <th>Universität</th>
            <th>Modulname</th>
            <th>Status</th>
            <th />
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>22.11.23 </td>
            <td>Universität Halle</td>

            <td>Mathe I</td>
            <td><span class="badge bg-success-subtle border border-success-subtle text-secondary-emphasis rounded-pill">angenommen</span></td>

            <td>
              <div class="btn-group text-nowrap float-end" role="group" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>
</Modal>

<style>
  /* TODO : buttons in nav-bar müssen ein wenig mehr Abstand zueinander haben */
  .nav-bar {
    padding-right: 1rem;
  }

  /* pdf */
</style>
