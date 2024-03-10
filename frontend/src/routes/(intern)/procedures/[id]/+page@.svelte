<script>
  import RequestStatus from '$lib/components/RequestStatus.svelte';
  import RelatedRequestsExtern from './forms/RelatedRequestsExtern.svelte';
  import RelatedRequestsIntern from './forms/RelatedRequestsIntern.svelte';
  import Header from '$lib/components/InternHeader.svelte';
  import * as config from '$lib/config';
  import { format, parseISO } from 'date-fns';
  import { page } from '$app/stores';
  import { successToast } from '$root/lib/toast';
  import Comment from './Comment.svelte';
  import PDF from './PDF.svelte';
  import Navbar from './Navbar.svelte';
  import CreditModule from './CreditModule.svelte';

  export let data;
  console.log(data.modules);

  let request = data.request;
  let requestBackup = JSON.parse(JSON.stringify(data.request));

  let showModalExtern;
  let showModalIntern;

  function updateStatus(status) {
    request.status = status;
    closeDropdown();
  }

  async function submitForm(event) {
    const body = request;
    delete body.relatedRequests;
    const res = await fetch('/update-request', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(body)
    });
    // successToast('Erfolreich gespeichert');

    console.log(res);
    requestBackup = JSON.parse(JSON.stringify(request));
  }

  async function cancelChanges(event) {
    request = requestBackup;
    submitForm();
  }

  function closeDropdown() {
    var dropdown = document.getElementById('myDropdown');
    var bootstrapDropdown = new bootstrap.Dropdown(dropdown);
    bootstrapDropdown.hide();
  }
</script>

<svelte:head>
  <!-- <title>{config.title} - {$page.data.title}</title> -->
  <title>{$page.data.title}</title>
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
      <Navbar bind:showModalExtern bind:showModalIntern />
    </div>
  </div>
  <div class="row px-3 w-100">
    <div class="col-8">
      <PDF />
    </div>

    <div class="col-4">
      <div>
        <div class="row mb-3">
          <!-- <div class="col-6"><strong>Antrag erstellt am </strong><br />{format(new Date(request.createdAt), 'dd.MM.yyyy HH:mm')}</div> -->
          <div class="col-6"><strong>Antrag erstellt am </strong><br />{format(new Date(request.createdAt), 'dd.MM.yyyy')}</div>
        </div>

        <!-- input für ausgewähltes Modul handlen -->
        <CreditModule bind:selectedOption={request.internalModule} />

        <div class="col mb-3">
          <div class="row">
            <!-- studibüro -->
            <div class="col">
              <p class="mb-1"><strong>Status</strong> <RequestStatus status={request.statusRequest} /></p>

              <!-- studianzeige -->
              <div class="col">
                <p class="mb-1"><strong>Auf Statusseite</strong><RequestStatus status={request.statusRequest} extern={true} /></p>
              </div>
            </div>
          </div>

          <Comment bind:annotationCommittee={request.annotationCommittee} bind:annotationStudent={request.annotationStudent} />
          <button type="submit" class="btn btn-primary" on:click={submitForm}>Speichern</button>
          <div class="btn btn-outline-secondary" on:click={cancelChanges}>Abbrechen</div>
        </div>
      </div>
    </div>
  </div>
</div>

<style>
</style>
