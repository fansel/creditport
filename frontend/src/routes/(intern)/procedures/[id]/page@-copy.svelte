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
  import CommentModal from './forms/CommentModal.svelte';

  export let data;
  console.log(data.modules);

  let request = data.request;
  let requestBackup = JSON.parse(JSON.stringify(data.request));

  let procedure = data.procedure;

  // consloge.log('procedureId: ', request.procedureId);

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

  let showComment = false;
</script>

<svelte:head>
  <!-- <title>{config.title} - {$page.data.title}</title> -->
  <title>{$page.data.title}</title>
</svelte:head>

<Header wide={true} />

<RelatedRequestsExtern bind:showModal={showModalExtern} />
<RelatedRequestsIntern bind:showModal={showModalIntern} />
<CommentModal bind:showModal={showComment} bind:annotationCommittee={request.annotationCommittee} bind:annotationStudent={request.annotationStudent}></CommentModal>

<div class="border-bottom d-flex justify-content-between">
  <div class="m-3 d-flex flex-column justify-content-center">
    <h2>Vorgangsnummer: {request.procedureId}</h2>
  </div>
  <div class="m-3 d-flex flex-column justify-content-center">
    <h2>{procedure.course.courseName}</h2>
  </div>
  <div class="m-3 d-flex flex-column justify-content-center">
    <div class="center">
      <RequestStatus status={request.statusRequest} />
    </div>
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
        {#each request.internalModules as modul, i}
          <CreditModule bind:selectedOption={request.internalModules} />
        {/each}
        <!-- <CreditModule bind:selectedOption={request.internalModules} /> -->

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

          <!-- <Comment bind:annotationCommittee={request.annotationCommittee} bind:annotationStudent={request.annotationStudent} /> -->
          <button type="submit" class="btn btn-primary" on:click={submitForm}>Speichern</button>
          <div class="btn btn-outline-secondary" on:click={cancelChanges}>Abbrechen</div>
        </div>

        <div class="d-flex justify-content-end align-items-end">
          <button class="btn btn-lg rounded-circle btn-primary" on:click={() => (showComment = true)}><i class="bi bi-chat-fill"></i></button>
        </div>
      </div>
    </div>
  </div>
</div>

<style>
</style>
