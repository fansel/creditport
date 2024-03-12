<script>
  import Modal from '$lib/components/FormModal.svelte';

  let dialog;

  export let showModal;
  export let user;
</script>

<Modal bind:showModal bind:this={dialog} action="?/deleteUser">
  <h2 slot="headline" class="m-0">Löschen bestätigen</h2>
  <div slot="body" class="p-3">
    <p>Willst du wirklich <strong>{user?.username}</strong> unwiderruflich löschen?</p>
    <input type="hidden" value={user?.userId} name="userId" />
  </div>
  <div slot="footer" class="p-3 d-flex justify-content-end align-items-center border-top">
    <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
    <button class="btn btn-danger" type="submit">Bestätigen</button>
  </div>
</Modal>

<style>
</style>
