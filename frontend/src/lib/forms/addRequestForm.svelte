<script>
  import Modal from '$lib/components/Modal.svelte';

  export let showModal = false;
  export let modules;

  let dialog; // HTMLDialogElement
  $: if (dialog && showModal) dialog.showModal();
</script>

<dialog bind:this={dialog} on:close={() => (showModal = false)} class="card p-0 border">
  <!-- <div on:click|stopPropagation> -->

  <form action="POST">
    <div class="card-header fw-bold">Modulantrag für Anrechnung der Universität Halle</div>
    <div class="card-body">
      <div class="row">
        <div class="col-md">
          <h2 class="h4">Fremdmodul</h2>

          <div class="mb-3">
            <label for="" class="mb-2">Name</label>
            <input type="text" class="form-control" placeholder="Modellierung und Programmierung" />
          </div>

          <div class="row">
            <div class="col-md-8">
              <div class="mb-3">
                <label for="" class="mb-2">Website zum Modul</label>
                <input type="text" class="form-control" placeholder="http://uni-leipzig.de/module_xyz" />
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="" class="mb-2">LP</label>
                <input type="text" class="form-control" placeholder="5" />
              </div>
            </div>
          </div>
          <div class="mb-3">
            <label for="formFile" class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
            <input class="form-control" type="file" id="formFile" />
          </div>
        </div>
        <div class="col-md">
          <h2 class="h4">Modul Vorschlag</h2>

          <div class="mb-3">
            <label for="" class="mb-2">Name</label>
            <select class="form-select" aria-label="Default select example">
              {#each modules as modul}
                <option value={modul.name}>{modul.name}</option>
              {/each}
            </select>
          </div>
        </div>
      </div>
    </div>

    <button class="btn btn-primary">Hinzufügen</button>
  </form>

  <button class="btn-close" autofocus on:click={() => dialog.close()} />

  <!-- </div> -->
</dialog>

<style>
</style>
