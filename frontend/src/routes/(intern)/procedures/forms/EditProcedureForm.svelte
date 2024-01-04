<script>
  import Modal from '$lib/components/FormModal.svelte';
  import { page } from '$app/stores';
  import { onMount } from 'svelte';
  import * as config from '$lib/config';
  import { format, parseISO } from 'date-fns';
  import Status from '$lib/components/Status.svelte'

  export let showModal;
  export let procedure;

  let dialog;
  let input;

  // $: console.log(procedure);

  //Wenn das Modal neu geöffnet wird werden alle relevanten Werte neue gesetzt
  // $: if (showModal) {
  //   input.value = procedure?.procedureId;
  // }
</script>

<Modal bind:showModal bind:this={dialog} action="?/editProcedure" reset={false} min_width="75rem">
  <h2 slot="headline" class="m-0">Vorgang bearbeiten</h2>
  <div slot="body" class="p-3">
    {#if showModal}
      <div class="row">
        <div class="col">
          <div class="row mb-3">
            <div class="col-3">
              <label for="id" class="col-form-label"> Vorgangsnummer </label>
            </div>
            <div class="col">
              <input type="text" value={$page.form?.data?.procedureId ?? procedure?.procedureId} disabled name="id" class="form-control {$page.form?.errors?.procedureId ? 'is-invalid' : ''}" />
              {#if $page.form?.errors?.procedureId}
                <div class="invalid-feedback">
                  {$page.form?.errors?.procedureId}
                </div>
              {/if}
            </div>
          </div>
          <div class=" row mb-3">
            <div class="col-3">
              <label for="university" class="col-form-label"> Universität </label>
            </div>
            <div class="col">
              <input type="text" value={$page.form?.data?.university ?? procedure?.university} name="university" class="form-control {$page.form?.errors?.university ? 'is-invalid' : ''}" />
              {#if $page.form?.errors?.university}
                <div class="invalid-feedback">
                  {$page.form?.errors?.university}
                </div>
              {/if}
            </div>
          </div>
          <div class="row mb-3">
            <div class="col-3">
              <label for="courseName" class="col-form-label"> Studiengang </label>
            </div>
            <div class="col">
              <input type="text" value={$page.form?.data?.courseName ?? procedure?.courseName} name="courseName" class="form-control {$page.form?.errors?.courseName ? 'is-invalid' : ''}" />
              {#if $page.form?.errors?.courseName}
                <div class="invalid-feedback">
                  {$page.form?.errors?.courseName}
                </div>
              {/if}
            </div>
          </div>
          <div class="mb-3">
            <label for="annotation" class="col-form-label"> Kommentare </label>

            <textarea
              type="text"
              value={$page.form?.data?.annotation ?? procedure?.annotation}
              name="name"
              class="form-control {$page.form?.errors?.annotation ? 'is-invalid' : ''}"
              placeholder="Lassen sie einen Kommentar da"
              style="min-height: 120px;"
            />
            {#if $page.form?.errors?.annotation}
              <div class="invalid-feedback">
                {$page.form?.errors?.annotation}
              </div>
            {/if}
          </div>
        </div>
        <div class="col">
          <div class="list-group">
            <div class="list-group-item d-inline-flex justify-content-between"><strong>Moduleanträge ({procedure.requests.length})</strong></div>

            {#if procedure}
              {#each procedure.requests as request}
                <div class="list-group-item d-inline-flex justify-content-between">
                  <div class="d-inline-flex align-item-center">
                    {request.externalModuleId}
                    <Status status={request.status} />
                  </div>
                  <a href="/procedures/{procedure.procedureId}/{request.requestId}"><i class="bi bi-pencil-square" /></a>
                </div>
              {/each}
            {/if}
          </div>
        </div>
      </div>
      <div class="row" />
    {/if}
  </div>
  <div slot="footer" class="p-3 d-flex justify-content-between align-items-center border-top">
    {#if showModal}
      <div class="">
        <div class="form-text" id="basic-addon4">Erstellt am {format(parseISO(procedure?.createdAt), 'dd.MM.yyyy HH:mm')}</div>
      </div>
      <div>
        <button class="btn btn-outline-primary mx-2">Weiterleiten <i class="bi bi-arrow-right" /> </button>
        <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
        <button class="btn btn-primary" type="submit">Speichern</button>
      </div>
    {/if}
  </div>
</Modal>

<style>
  label {
    font-size: 0.9rem;
  }
</style>
