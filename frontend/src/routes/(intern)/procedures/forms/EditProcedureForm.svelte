<script>
  import Modal from '$lib/components/FormModal.svelte';
  import { page } from '$app/stores';
  import { onMount } from 'svelte';

  export let showModal;
  export let procedure;

  let dialog;
  let input;

  $: console.log(procedure);

  //Wenn das Modal neu geöffnet wird werden alle relevanten Werte neue gesetzt
  $: if (showModal) {
    input.value = procedure?.procedureId;
  }
</script>

<Modal bind:showModal bind:this={dialog} action="?/changeUni" reset={false} min_width="75rem">
  <h2 slot="headline" class="m-0">Vorgang bearbeiten</h2>
  <div slot="body" class="p-3">
    <div class="row">
      <div class="col">
        <div class="mb-3">
          <label for="name" class="col-form-label"> Vorgangsnummer </label>

          <input type="text" value={$page.form?.data?.name ?? procedure?.procedureId} disabled name="name" class="form-control {$page.form?.errors?.name ? 'is-invalid' : ''}" bind:this={input} />
          {#if $page.form?.errors?.name}
            <div class="invalid-feedback">
              {$page.form?.errors?.name}
            </div>
          {/if}
        </div>
        <div class="mb-3">
          <label for="name" class="col-form-label"> Vorgangsnummer </label>

          <input type="text" value={$page.form?.data?.name ?? procedure?.procedureId} disabled name="name" class="form-control {$page.form?.errors?.name ? 'is-invalid' : ''}" bind:this={input} />
          {#if $page.form?.errors?.name}
            <div class="invalid-feedback">
              {$page.form?.errors?.name}
            </div>
          {/if}
        </div>
      </div>
      <div class="col">
        <div class="list-group">
          <div class="list-group-item d-inline-flex justify-content-between"><strong>Module</strong></div>

          {#if procedure}
            {#each procedure.requests as request}
              <div class="list-group-item d-inline-flex justify-content-between">
                {request.externalModuleId} - {request.internalModuleId} <a href="/procedures/{procedure.procedureId}/{request.requestId}"><i class="bi bi-pencil-square" /></a>
              </div>
            {/each}
          {/if}
        </div>
      </div>
    </div>
    <div class="row" />
  </div>
  <div slot="footer" class="p-3 d-flex justify-content-end align-items-center border-top">
    <button class="btn btn-outline-primary mx-2">Weiterleiten</button>
    <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
    <button class="btn btn-primary" type="submit">Speichern</button>
  </div>
</Modal>

<style>
</style>
