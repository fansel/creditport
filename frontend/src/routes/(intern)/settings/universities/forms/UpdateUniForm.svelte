<script>
  import Modal from '$lib/components/FormModal.svelte';
  import { page } from '$app/stores';
  import { onMount } from 'svelte';

  let dialog;
  let input;

  export let showModal;
  export let uni;

  //Wenn das Modal neu geöffnet wird werden alle relevanten Werte neue gesetzt
  $: if (showModal) {
    input.value = uni?.uniName;
  }
</script>

<Modal bind:showModal bind:this={dialog} action="?/changeUni" reset={false}>
  <h2 slot="headline" class="m-0">Universität bearbeiten</h2>
  <div slot="body" class="p-3">
    <div class="row">
      <div class="col-3">
        <label for="name" class="col-form-label"> Name </label>
      </div>
      <div class="col">
        <input type="hidden" value={uni?.uniId} name="id" />

        <input type="text" value={$page.form?.data?.name ?? uni?.uniName} name="name" class="form-control {$page.form?.errors?.name ? 'is-invalid' : ''}" bind:this={input} />
        {#if $page.form?.errors?.name}
          <div class="invalid-feedback">
            {$page.form?.errors?.name}
          </div>
        {/if}
      </div>
    </div>
  </div>
  <div slot="footer" class="p-3 d-flex justify-content-end align-items-center border-top">
    <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
    <button class="btn btn-primary" type="submit">Speichern</button>
  </div>
</Modal>

<style>
</style>
