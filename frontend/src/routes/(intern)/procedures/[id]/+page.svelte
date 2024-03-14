<script>
  import * as config from '$lib/config';
  import { format, parseISO } from 'date-fns';
  import { page } from '$app/stores';
  import { superForm } from 'sveltekit-superforms';

  import { truncateText } from '$root/lib/util.js';
  import { status_requests, user_roles } from '$root/lib/config';

  import Header from '$lib/components/InternHeader.svelte';
  import Comment from './Comment.svelte';
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';
  import Accordion from '$root/lib/components/Accordion.svelte';
  import Studi from '$root/lib/components/InfoModal.svelte';
  import RelatedRequestsExtern from './forms/RelatedRequestsExtern.svelte';
  import { full_request_schema } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';
  import UpdateExternalModule from './forms/UpdateExternalModule.svelte';

  import VirtualList from '@sveltejs/svelte-virtual-list';

  export let data;

  const { form, messages, errors, enhance } = superForm(data.updateRequestForm, {
    dataType: 'json',
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    clearOnSubmit: 'none',
    resetForm: false,
    validators: zod(full_request_schema),
    validationMethod: 'auto',
    customValidity: false
  });

  let procedure = data.procedure;
  let internalModules = data.modules;
  let showComment = false;
  let showModalExtern = false;
  let updateModuleForm;

  console.log(data.user.role);

  function updateStatus(status) {
    $form.statusRequest = status_requests[status].match;
  }
  function formalAblehnen() {
    $form.statusRequest = status_requests[4].match;
    $form.annotationCommittee = 'Formal abgelehnt (siehe Anmerkung für Studenten)';
    $form.annotationStudent = 'Formal abgelehnt ' + ($form.annotationStudent ? ':\n' : '') + $form.annotationStudent;
  }

  // let requestBackup = JSON.parse(JSON.stringify(data.request));

  function sumLP(list) {
    let sum = 0;
    for (let i = 0; i < list.length; i++) {
      sum += list[i].creditPoints;
    }
    return sum;
  }

  function sumLPInternalModules(list) {
    let sum = 0;
    for (let i = 0; i < list.length; i++) {
      sum += findCurrentModule(list[i].moduleId).creditPoints;
    }
    return sum;
  }

  function findCurrentModule(moduleId) {
    return internalModules.find((m) => m.moduleId == moduleId);
  }

  function module_dialog_open(id) {
    const module = $form.externalModules.find((u) => u.moduleId == id);
    if (!module) {
      console.error('Module not found');
    }
    updateModuleForm.dialog_open(module);
  }
  console.log('similar: ', data.similarRequests);
</script>

<RelatedRequestsExtern bind:showModal={showModalExtern} bind:similarRequests={data.similarRequests} />

<UpdateExternalModule bind:this={updateModuleForm} data={data.updateModuleForm} />

<Studi bind:showModal={showComment}>
  <div slot="headline"><strong>Hinweis für Vorgang der Student*in</strong></div>
  <div slot="body">
    <p>{procedure.annotation}</p>
  </div>
</Studi>

<form action="?/updateRequest" method="POST" use:enhance>
  <!-- <Header wide={true} /> -->
  <div class="border-bottom py-3 d-flex my-3 justify-content-between">
    <div class="d-flex">
      <div class="vorgangsnummer">
        <strong>Vorgangsnummer: </strong>
        {$form.procedureId}
      </div>
      <div class="dates d-none d-lg-block">
        <i class="ms-3 bi bi-calendar" />
        {format(new Date($form.createdAt), 'dd.MM.yyyy')}

        <i class="ms-3 bi bi-calendar-range" />
        {format(new Date(procedure.lastUpdated), 'dd.MM.yyyy')}
      </div>

      <div class="ms-3 studium">
        <strong>{procedure.course.courseName} </strong>
      </div>
    </div>

    <div class="status">
      <RequestStatus status={$form.statusRequest} />
    </div>
  </div>

  <div class="row">
    <div class="together hstack gap-2">
      {#if $form.pdfExists}
        <a class="btn btn-sm btn-danger fw-bold" href="{config.pdf_endpoint}{$form.requestId}" target="_blank">PDF</a>
      {:else}
        <div class="disabled d-flex flex-center btn btn-outline-danger"><i class="bi bi-ban" /></div>
      {/if}

      <input type="text" class="form-control" placeholder="Website zum Modul" bind:value={$form.moduleLink} />
      <button type="button" class=" btn btn-outline-primary" on:click={() => (showComment = true)}>Kommentar</button>
      <button type="button" class="btn-outline-primary btn {data.similarRequests.length == 0 ? 'disabled' : ''} text-nowrap" on:click={() => (showModalExtern = true)}>ähnliche Anträge</button>
    </div>
  </div>

  <!-- OVERVIEW -->
  <div class="row scrollable">
    <div class="col-md-6">
      {#each $form.externalModules as _, i}
        <Accordion>
          <div slot="head">
            <span class="fs-6 fw-bold">{truncateText($form.externalModules[i].moduleName, 55)}</span>
            {#if $form.externalModules[i].verified}
              <i class="bi ms-2 text-primary bi-check-circle" />
            {/if}
          </div>

          <div slot="details">
            <div class="row mb-3">
              <div class="col-md-10 mb-2">
                <label for="" class="mb-2">Name des Moduls </label>
                <input disabled type="text" class="form-control" placeholder="Modellierung und Programmierung" bind:value={$form.externalModules[i].moduleName} />
              </div>

              <div class="col-md-2 mb-2">
                <label for="" class="mb-2">LP</label>
                <input disabled type="number" class="form-control" placeholder="" bind:value={$form.externalModules[i].creditPoints} />
              </div>
            </div>
            <div class="row">
              <div class="col">
                <button type="button" class="btn btn-outline-secondary btn-sm" on:click={module_dialog_open($form.externalModules[i].moduleId)}>Bearbeiten<i class="bi bi-pencil-square ms-2" /></button
                >
              </div>
            </div>
          </div>
        </Accordion>
      {/each}
    </div>
    <div class="col-md-6">
      {#each $form.internalModules as _, i}
        <Accordion>
          <div slot="head" class="d-flex justify-content-">
            <span class="fs-6 fw-bold">{truncateText(findCurrentModule($form.internalModules[i].moduleId).moduleName, 55)}</span>
          </div>
          <div slot="details">
            <div class="row mb-3">
              <div class="col-md-10 mb-2">
                <label class="mb-2" for="">Name des Moduls</label>

                <select class="form-select" bind:value={$form.internalModules[i].moduleId}>
                  {#each internalModules as module, index}
                    <option value={module.moduleId}>{module.moduleName}</option>
                  {/each}
                </select>
              </div>
              <div class="col-md-2 mb-2">
                <label class="mb-2" for="">LP</label>
                <input type="text" class="form-control" disabled placeholder="" value={findCurrentModule($form.internalModules[i].moduleId).creditPoints} />
                <!-- <p class="border p-2 rounded">{module.creditPoints}</p> -->
              </div>
            </div>

            {#if findCurrentModule($form.internalModules[i].moduleId).moduleDescription}
              <div class="mb-3">
                <label class="mb-2" for="">Modulbeschreibung</label>
                <p class="moduleDescription border p-2 rounded">
                  {findCurrentModule($form.internalModules[i].moduleId).moduleDescription}
                </p>
              </div>
            {/if}
          </div>
        </Accordion>
      {/each}
    </div>
  </div>

  <div class="fest">
    <div class="row">
      <div class="col-md-6 fw-bold">
        <div class="m-2">
          Summe LP:
          {sumLP($form.externalModules)}
        </div>
      </div>
      <div class="col-md-6 fw-bold">
        <div class="m-2">
          Summe LP:
          {sumLPInternalModules($form.internalModules)}
        </div>
      </div>
    </div>
    <div class="comment my-3">
      <Comment bind:annotationCommittee={$form.annotationCommittee} bind:annotationStudent={$form.annotationStudent} />
    </div>
    <div class="buttons d-flex hstack gap-1">
      {#if data.request.statusRequest == status_requests[0].match}
        <button type="submit" class="btn btn-sm btn-primary d-flex" on:click={() => updateStatus(1)}>Speichern</button>
      {:else}
        <button type="submit" class="btn btn-sm btn-primary d-flex">Speichern</button>
      {/if}

      {#if data.user.role == user_roles.STUDY_OFFICE}
        <button type="submit" class="btn btn-sm btn-outline-danger d-flex justfiy-content-end" on:click={formalAblehnen}>Formal Ablehnen</button>
        <button type="submit" class="btn btn-sm btn-outline-warning d-flex justfiy-content-end" on:click={() => updateStatus(2)}>Rückfrage nötig</button>
      {/if}
      {#if data.user.role == user_roles.EXAM_COMITEE || data.user.role == user_roles.ADMIN}
        <button type="submit" class="btn btn-sm btn-success d-flex justfiy-content-end" on:click={() => updateStatus(3)}>Annehmen</button>
        <button type="submit" class="btn btn-sm btn-danger d-flex justfiy-content-end" on:click={() => updateStatus(4)}>Ablehnen</button>
        <button type="submit" class="btn btn-sm btn-outline-warning d-flex justfiy-content-end" on:click={() => updateStatus(2)}>Rückfrage nötig</button>
      {/if}
      <button type="submit" class="btn btn-sm btn-outline-secondary d-flex justfiy-content-end" on:click={() => updateStatus(0)}>Zurücksetzen</button>

      <!-- <button type="button" class="btn btn-sm btn-outline-primary d-flex justfiy-content-end">Status ändern</button> -->
    </div>
  </div>
</form>

<!-- <SuperDebug data={$form} /> -->

<style>
  .accordion-button:focus {
    box-shadow: none;
  }

  .accordion-button:not(.collapsed) {
    background-color: var(--bs-accordion-bg);
    /* border-bottom: var(--bs-border-width) var(--bs-border-style) var(--bs-border-color) !important; */
    box-shadow: none;
  }
  .moduleDescription {
    min-height: 38px;
    max-height: 120px;
    overflow-y: auto;
  }
  .scrollable {
    max-height: 500px;
    overflow-y: auto;
  }
</style>
