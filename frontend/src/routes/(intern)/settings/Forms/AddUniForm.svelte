<script>
  import Modal from '$root/lib/components/FormModal.svelte';

  let dialog;

  export let showModal;
</script>

<Modal bind:showModal bind:this={dialog} action="?/addUni">
  <h2 slot="headline" class="m-0">Universität hinzufügen</h2>
  <div slot="body" class="p-3">
    <div class="row">
      <div class="col-3">
        <label for="name" class="col-form-label">Name</label>
      </div>
      <div class="col">
        <input type="text" name="name" placeholder="Neue Universität" class="form-control" />
      </div>
    </div>
  </div>
  <div slot="footer" class="p-3 d-flex justify-content-end align-items-center border-top">
    <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
    <button class="btn btn-primary" type="submit">Hinzufügen</button>
  </div>
</Modal>

<style>
</style>
