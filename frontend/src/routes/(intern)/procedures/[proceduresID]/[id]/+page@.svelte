<!-- Hauptbearbeitungsseite mit PDF Vorschau -->
<script>
  import Status from '$lib/components/Status.svelte';
  import RelatedRequestsExtern from './forms/RelatedRequestsExtern.svelte';
  import RelatedRequestsIntern from './forms/RelatedRequestsIntern.svelte';
  import Header from '$lib/components/InternHeader.svelte';
  import * as config from '$lib/config';
  import { format, parseISO } from 'date-fns';
  import { getNachfolger, getVorgänger } from '$lib/util';
  import { page } from '$app/stores';

  export let data;

  const modules = data.modules;
  const request = data.request;
  let selectedModul = 0;

  let showModalExtern = false;
  let showModalIntern = false;

  // APIs (Jetzt noch mit Testsatz, da API nicht steht)

  // let request = {
  //   procedureID: 1234,
  //   requestID: 1,
  //   externalModule: 'Mathe I',
  //   link: 'https://www.orimi.com/pdf-test.pdf',
  //   status: 9,
  //   comment_studies_office: 'Für mich passt das alles soweit, einmal bestätigen',
  //   comment_student: 'Der Bescheid wurde erfolgreich angenommen.',
  //   createdAt: new Date(),
  //   lastEditedAt: new Date()
  // };

  // $: statusColor = getStatus(request.status).statusColor;
  // $: statusText = getStatus(request.status).statusText;
  function updateStatus(status) {
    request.status = status;
    closeDropdown();
  }

  function closeDropdown() {
    var dropdown = document.getElementById('myDropdown');
    var bootstrapDropdown = new bootstrap.Dropdown(dropdown);
    bootstrapDropdown.hide();
  }

  // Bemerkungsfeld
</script>

<svelte:head>
  <title>{config.title} - {$page.data.title}</title>
</svelte:head>

<Header wide={true} />

<RelatedRequestsExtern bind:showmodal={showModalExtern} />
<RelatedRequestsIntern bind:showmodal={showModalIntern} />

<div class="border-bottom">
  <div class="col-md m-3">
    <h1>Vorgangsnummer: {request.procedureId}</h1>
  </div>
</div>

<div class="site position-fixed m-2 w-100">
  <div class="row px-3">
    <div class="col" />

    <!-- NAVBAR -->
    <div class="nav-bar my-3" width="100%">
      {#if request.relatedRequests.length > 0}
        {#if getVorgänger(request.requestId, request.relatedRequests) != null}
          <a type="button" class="btn btn-sm btn-outline-primary" rel="external" href="/procedures/{request.procedureId}/{getVorgänger(request.requestId, request.relatedRequests)}">
            <i class="bi bi-arrow-left" />
          </a>
        {/if}
        {#if getNachfolger(request.requestId, request.relatedRequests) != null}
          <a type="button" class="btn btn-sm btn-outline-primary" rel="external" href="/procedures/{request.procedureId}/{getNachfolger(request.requestId, request.relatedRequests)}">
            <i class="bi bi-arrow-right" />
          </a>
        {/if}
      {/if}

      <!-- {#if relatedRequests(request) != 1}
        <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href=/procedures/1/{requestId - 1}"><i class="bi bi-arrow-left" /> </button>
      {/if}
      {#if relatedRequests(request) != -1}
        <button type="button" class="btn btn-sm btn-outline-primary" onclick="window.location.href='/procedures/1/{requestId + 1}'">
          <i class="bi bi-arrow-right" />
        </button>
      {/if} -->

      <div class="btn-group dropend">
        <button type="button" class="btn btn-sm btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">ähnliche Anträge</button>
        <div class="dropdown-menu">
          <button class="dropdown-item" on:click={() => (showModalExtern = true)}>Module für aktuelles Fremdmodul</button>
          <button class="dropdown-item" on:click={() => (showModalIntern = true)}>Akzeptierte Fremdmodule für Modulvorschlag</button>
        </div>
      </div>
    </div>
  </div>
  <div class="row px-3 w-100">
    <div class="col-8">
      <div class="pdf-container">
        <div class="pdf-content">
          {#if request.pdfExists}
            <iframe src="{config.pdf_endpoint + request.requestId}#zoom=FitH" class="pdf-iframe" title="pdf" />
          {:else}
            <iframe src="https://www.orimi.com/pdf-test.pdf#zoom=FitH" class="pdf-iframe" title="pdf" />
          {/if}
        </div>
      </div>
    </div>

    <div class="col-4">
      <form action="">
        <div class="row mb-3">
          <div class="col-6"><strong>Antrag erstellt am </strong><br />{format(new Date(request.createdAt), 'dd.MM.yyyy HH:mm')}</div>
          <!-- <div class="col-6"><strong>zuletzt bearbeitet am</strong><br />{format(request.lastEditedAt, 'dd.MM.yyyy HH:mm')}</div> -->
        </div>

        <div class="mb-3">
          <label for="" class="mb-2"><strong>Anrechnungsmodul</strong></label>
          <select class="form-select" aria-label="Default select example" bind:value={selectedModul}>
            {#each modules as modul, index}
              <option value={index}>{modul.moduleName}</option>
            {/each}
          </select>
        </div>

        <div class="col mb-3">
          <div class="row">
            <!-- studibüro -->
            <div class="col">
              <p class="mb-1"><strong>Status</strong> <Status status={request.status} /></p>

              <!-- 
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
                  <button class="dropdown-item" on:click={() => updateStatus(6)}>{getStatus(6).statusText}</button>
                  <button class="dropdown-item" on:click={() => updateStatus(3)}>{getStatus(3).statusText}</button>
                  <button class="dropdown-item" on:click={() => updateStatus(7)}>{getStatus(7).statusText}</button>
                  <button class="dropdown-item" on:click={() => updateStatus(8)}>{getStatus(8).statusText}</button>
                  <button class="dropdown-item" on:click={() => updateStatus(9)}>{getStatus(9).statusText}</button>
                  <button class="dropdown-item" on:click={() => updateStatus(10)}>{getStatus(10).statusText}</button>
                </div>
              </div>
            </div> -->

              <!-- studianzeige -->
              <div class="col">
                <p class="mb-1"><strong>Auf Statusseite</strong><Status status={request.status} extern={true} /></p>

                <!--               

              <div class="btn bg-{statusColor}-subtle border border-{statusColor}-subtle text-{statusColor}-emphasis rounded-pill">
                {#if request.status === 7 || request.status === 8}
                  {getStatus(3).statusText}
                {:else}{statusText}
                {/if}
              </div> -->
              </div>
            </div>
          </div>

          <div class="form-row mb-2">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
              <li class="nav-item" role="presentation">
                <button class="nav-link active" id="studi-tab" data-bs-toggle="tab" data-bs-target="#studi-tab-pane" type="button" role="tab" aria-controls="studi-tab-pane" aria-selected="true"
                  >Studierende</button
                >
              </li>
              <li class="nav-item" role="presentation">
                <button class="nav-link" id="office-tab" data-bs-toggle="tab" data-bs-target="#office-tab-pane" type="button" role="tab" aria-controls="office-tab-pane" aria-selected="false"
                  >Prüfungsauschuss</button
                >
              </li>
            </ul>

            <div class="tab-content" id="myTabContent">
              <div class="tab-pane fade show active" id="studi-tab-pane" role="tabpanel" aria-labelledby="studi-tab" tabindex="0">
                <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4" name="comment">{request.annotation ?? ''}</textarea>
              </div>
              <div class="tab-pane fade" id="office-tab-pane" role="tabpanel" aria-labelledby="office-tab" tabindex="0">
                <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4" name="comment">{request.comment_student ?? ''}</textarea>
              </div>
            </div>
          </div>

          <div class="btn btn-primary">Speichern</div>
          <div class="btn btn-outline-secondary">Abbrechen</div>
        </div>
      </form>
    </div>
  </div>
</div>

<style>
  textarea#input {
    border-top: none;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
  /* TODO : buttons in nav-bar müssen ein wenig mehr Abstand zueinander haben */
  .nav-bar {
    padding-right: 1rem;
  }

  /* pdf */
  .pdf-iframe {
    width: 100%;
    height: calc(100vh - 250px);
  }
</style>
