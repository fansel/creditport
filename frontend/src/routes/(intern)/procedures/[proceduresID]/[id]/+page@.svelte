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
  import Comment from './Comment.svelte';

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

<RelatedRequestsExtern bind:showModal={showModalExtern} />
<RelatedRequestsIntern bind:showModal={showModalIntern} />

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

              <!-- studianzeige -->
              <div class="col">
                <p class="mb-1"><strong>Auf Statusseite</strong><Status status={request.status} extern={true} /></p>
              </div>
            </div>
          </div>

          <Comment bind:request={data.request} />

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
