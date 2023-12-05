<script>
  import Modal from '$root/lib/components/FormModal.svelte';

  let dialog;

  export let showModal;
  export let id;
  export let name;
</script>

<Modal bind:showModal bind:this={dialog} action="?/changeUni">
  <h2 slot="headline" class="m-0">Universität bearbeiten</h2>
  <div slot="body" class="p-3">
    <div class="row">
      <div class="col-3">
        <label for="name" class="col-form-label"> Name </label>
      </div>
      <div class="col">
        <input type="hidden" value={id} name="id" />

        <input type="text" value={name} name="name" class="form-control" />
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
