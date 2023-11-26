<!-- Hauptbearbeitungsseite mit PDF Vorschau -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/Oo+O5" crossorigin="anonymous">
  export let data;

  const modules = data.modules;
  let selectedModul = 0;

  // importing Requests modal (pop up)
  import Modal from '$lib/components/Modal.svelte';

  let showModal = false;

  // APIs (Jetzt noch mit Testsatz, da API nicht steht)

  $: request = {
    procedureID: 1234,
    requestID: 1,
    externalModule: 'Mathe I',
    link: 'https://www.orimi.com/pdf-test.pdf',
    status: 1,
    comment: 'Der Bescheid wurde erfolgreich angenommen.',
    createdAt: new Date(),
    lastEditedAt: new Date()
  };

  $: statusColor = 'success';
  $: statusText = 'default';
  function updateStatus(status) {
    console.log('updateStatus called');

    request.status = status;
    if (request.status === 1) {
      console.log('Status one called');
      statusText = 'Neu';
      statusColor = 'secondary';
    } else if (request.status === 2) {
      console.log('Status two called');
      statusText = 'Offen';
      statusColor = 'secondary';
    } else if (request.status === 3) {
      console.log('Status three called');
      statusText = 'In Bearbeitung';
      statusColor = 'warning';
    } else if (request.status === 4) {
      console.log('Status four called');
      statusText = 'Weitergeleitet';
      statusColor = 'warning';
    } else if (request.status === 5) {
      console.log('Status five called');
      statusText = 'Abgeschlossen';
      statusColor = 'success';
    }
    closeDropdown();
  }

  function closeDropdown() {
    var dropdown = document.getElementById('myDropdown');
    var bootstrapDropdown = new bootstrap.Dropdown(dropdown);
    bootstrapDropdown.hide();
  }
</script>

<div class="border-bottom">
  <div class="col-md m-3">
    <h1>Vorgangsnummer: {request.procedureID}</h1>
  </div>
</div>

<div class="site position-fixed m-2">
  <div class="row px-3">
    <div class="nav-bar my-3" width="100%">
      <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='https://google.com'">
        <i class="bi bi-arrow-left" />
      </button>
      <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='https://youtube.com'">
        <i class="bi bi-arrow-right" />
      </button>
      <button type="button" class="btn btn-sm btn-outline-primary" on:click={() => (showModal = true)}> ähnliche Anträge </button>
    </div>
  </div>
  <div class="row px-3 w-100">
    <div class="col-8">
      <div class="pdf-container">
        <div class="pdf-content"><iframe src={request.link} width="1150" height="800" /></div>
      </div>
    </div>

    <div class="col-4">
      <form action="">
        <div class="mb-3">
          <label for="" class="mb-2"><strong>Modulvorschlag</strong></label>
          <select class="form-select" aria-label="Default select example" bind:value={selectedModul}>
            {#each modules as modul, index}
              <option value={index}>{modul.moduleName}</option>
            {/each}
          </select>
        </div>

        <div class="col-auto mb-3">
          <label class="mb-1"><strong>Status</strong></label><br />

          <div class="btn-group dropend">
            <button
              type="button"
              class="btn bg-{statusColor}-subtle border border-{statusColor}-subtle text-{statusColor}-emphasis rounded-pill dropdown-toggle"
              data-bs-toggle="dropdown"
              aria-haspopup="true"
              aria-expanded="false"
            >
              {statusText}
            </button>
            <div class="dropdown-menu" id="myDropdown">
              <a class="dropdown-item" on:click={() => updateStatus(1)}>Neu</a>
              <a class="dropdown-item" on:click={() => updateStatus(2)}>Offen</a>
              <a class="dropdown-item" on:click={() => updateStatus(3)}>in Bearbeitung</a>
              <a class="dropdown-item" on:click={() => updateStatus(4)}>Weitergeleitet</a>
              <a class="dropdown-item" on:click={() => updateStatus(5)}>Abgeschlossen</a>
            </div>
          </div>
        </div>

        <div class="form-row mb-2">
          <div class="form-group col">
            <label class="my-2"><strong>Bemerkungsfeld</strong></label>
            <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4">{request.comment}</textarea>
          </div>
        </div>

        <div class="btn btn-primary">Speichern</div>
        <div class="btn btn-outline-secondary">Abbrechen</div>
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
