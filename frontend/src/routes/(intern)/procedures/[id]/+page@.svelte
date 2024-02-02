<script>
  import Status from '$lib/components/Status.svelte';
  import RelatedRequestsExtern from './forms/RelatedRequestsExtern.svelte';
  import RelatedRequestsIntern from './forms/RelatedRequestsIntern.svelte';
  import Header from '$lib/components/InternHeader.svelte';
  import * as config from '$lib/config';
  import { format, parseISO } from 'date-fns';
  import { page } from '$app/stores';
  import Comment from './Comment.svelte';
  import PDF from './PDF.svelte';
  import Navbar from './Navbar.svelte';
  import CreditModule from './CreditModule.svelte';

  export let data;

  const modules = data.modules;
  const request = data.request;
  let selectedModul = 0;
  let annotation;

  let showModalExtern;
  let showModalIntern;

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
    <div class="col">
      <Navbar bind:showModalExtern bind:showModalIntern></Navbar>
    </div>
  </div>
  <div class="row px-3 w-100">
    <div class="col-8">
      <PDF />
    </div>

    <div class="col-4">
      <form method="PUT" action="?/putRequest">
        <!-- hidden input für Daten die sich nicht ändern sollten -->
        <input name="requestId" value={request.requestId} type="hidden" />
        <input name="pdfExists" value={request.pdfExists} type="hidden" />
        <input name="createdAt" value={request.createdAt} type="hidden" />
        <div class="row mb-3">
          <div class="col-6"><strong>Antrag erstellt am </strong><br />{format(new Date(request.createdAt), 'dd.MM.yyyy HH:mm')}</div>
        </div>

        <!-- input für ausgewähltes Modul handlen -->
        <CreditModule />

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

          <Comment bind:annotation={request.annotation} />

          <button type="submit" class="btn btn-primary">Speichern</button>
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
</style>
