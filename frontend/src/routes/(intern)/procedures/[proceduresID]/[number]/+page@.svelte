<!-- Hauptbearbeitungsseite mit PDF Vorschau -->
<script>
  import { getStatus } from '$lib/status.js';
  import Modal from '$root/lib/components/FormModal.svelte';
  import Header from '$lib/components/InternHeader.svelte';
  import { format } from 'date-fns';

  export let data;

  const modules = data.modules;
  let selectedModul = 0;

  let showModal1 = false;
  let showModal2 = false;

  // APIs (Jetzt noch mit Testsatz, da API nicht steht)

  let request = {
    procedureID: 1234,
    requestID: 1,
    externalModule: 'Mathe I',
    link: 'https://www.orimi.com/pdf-test.pdf',
    status: 9,
    comment: 'Der Bescheid wurde erfolgreich angenommen.',
    createdAt: new Date(),
    lastEditedAt: new Date()
  };

  $: statusColor = getStatus(request.status).statusColor;
  $: statusText = getStatus(request.status).statusText;
  function updateStatus(status) {
    request.status = status;
    closeDropdown();
  }

  function closeDropdown() {
    var dropdown = document.getElementById('myDropdown');
    var bootstrapDropdown = new bootstrap.Dropdown(dropdown);
    bootstrapDropdown.hide();
  }
</script>

<Header />

<div class="border-bottom">
  <div class="col-md m-3">
    <h1>Vorgangsnummer: {request.procedureID}</h1>
  </div>
</div>

<div class="site position-fixed m-2 w-100">
  <div class="row px-3">
    <div class="nav-bar my-3" width="100%">
      <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='https://google.com'">
        <i class="bi bi-arrow-left" />
      </button>
      <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='https://youtube.com'">
        <i class="bi bi-arrow-right" />
      </button>
      <div class="btn-group dropend">
        <button type="button" class="btn btn-sm btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">ähnliche Anträge</button>
        <div class="dropdown-menu">
          <a class="dropdown-item" on:click={() => (showModal1 = true)}>Module für aktuelles Fremdmodul</a>
          <a class="dropdown-item" on:click={() => (showModal2 = true)}>Akzeptierte Fremdmodule für Modulvorschlag</a>
        </div>
      </div>
      <!-- <button type="button" class="btn btn-sm btn-outline-primary" on:click={() => (showModal = true)}> ähnliche Anträge </button> -->
    </div>
  </div>
  <div class="row px-3 w-100">
    <div class="col-8">
      <div class="pdf-container">
        <div class="pdf-content"><iframe src={request.link} width="100%" height="800" /></div>
      </div>
    </div>

    <div class="col-4">
      <form action="">
        <div class="row mb-3">
          <div class="col-6"><strong>Antrag erstellt am </strong><br />{format(request.createdAt, 'dd.MM.yyyy HH:mm')}</div>
          <div class="col-6"><strong>zuletzt bearbeitet am</strong><br />{format(request.lastEditedAt, 'dd.MM.yyyy HH:mm')}</div>
        </div>

        <div class="mb-3">
          <label for="" class="mb-2"><strong>Anrechnungsmodul</strong></label>
          <select class="form-select" aria-label="Default select example" bind:value={selectedModul}>
            {#each modules as modul, index}
              <option value={index}>{modul.moduleName}</option>
            {/each}
          </select>
        </div>

        <div class="col-auto mb-3">
          <div class="row">
            <!-- studibüro -->
            <div class="col-6">
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
                  <a class="dropdown-item" on:click={() => updateStatus(6)}>{getStatus(6).statusText}</a>
                  <a class="dropdown-item" on:click={() => updateStatus(3)}>{getStatus(3).statusText}</a>
                  <a class="dropdown-item" on:click={() => updateStatus(7)}>{getStatus(7).statusText}</a>
                  <a class="dropdown-item" on:click={() => updateStatus(8)}>{getStatus(8).statusText}</a>
                  <a class="dropdown-item" on:click={() => updateStatus(9)}>{getStatus(9).statusText}</a>
                  <a class="dropdown-item" on:click={() => updateStatus(10)}>{getStatus(10).statusText}</a>
                </div>
              </div>
            </div>

            <!-- studianzeige -->
            <div class="col-6">
              <label class="mb-1"><strong>Auf Statusseite</strong></label><br />

              <div class="btn bg-{statusColor}-subtle border border-{statusColor}-subtle text-{statusColor}-emphasis rounded-pill">
                {#if request.status === 7 || request.status === 8}
                  {getStatus(3).statusText}
                {:else}{statusText}
                {/if}
              </div>
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

<Modal bind:showModal={showModal1}>
  <h2 slot="headline" class="m-0">ähnliche Anträge</h2>

  <!-- body -->
  <!-- alle Fremdmodule für dieses Modul -->

  <form action="" slot="body" class="p-3">
    <div class="row form-inline d-flex align-items-center no-wrap mb-3">
      <div class="col-4">
        <strong>für Fremdmodule </strong>
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
            <td><span class="badge bg-danger-subtle border border-danger-subtle text-secondary-emphasis rounded-pill">abgelehnt</span></td>

            <td>
              <div class="btn-group text-nowrap float-end" role="group" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>
</Modal>

<Modal bind:showModal={showModal2}>
  <h2 slot="headline" class="m-0">ähnliche Anträge</h2>

  <!-- body -->
  <!-- alle Fremdmodule für dieses Modul -->

  <form action="" slot="body" class="p-3">
    <div class="row form-inline d-flex align-items-center no-wrap mb-3">
      <div class="col-4">
        <strong>für Modulvorschlag </strong>
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
