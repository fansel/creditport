<script>
  import Modal from '$lib/components/InfoModal.svelte';

  export let showModal;
</script>

<Modal bind:showModal>
  <h2 slot="headline" class="m-0">ähnliche Anträge</h2>

  <!-- body -->
  <!-- alle Fremdmodule für dieses Modul -->

  <form action="" slot="body" class="p-3">
    <div class="row form-inline d-flex align-items-center no-wrap mb-3">
      <div class="col-4">
        <strong>für Modulvorschlag </strong>
      </div>
      <div class="col-8">
        <input type="text" placeholder="Suche" class="form-control form-control-sm" />
      </div>
    </div>

    <div class="table-responsive">
      <table class="table table-sm table-hover table-responsive border align-middle shadow-sm">
        <thead>
          <tr>
            <th>Datum</th>
            <th>Universität</th>
            <th>Modulname</th>
            <th>Status</th>
            <th />
          </tr>
        </thead>

        <tbody>
          <tr>
            <td>22.11.23 </td>
            <td>Universität Halle</td>

            <td>Mathe I</td>
            <td><span class="badge bg-success-subtle border border-success-subtle text-secondary-emphasis rounded-pill">angenommen</span></td>

            <td>
              <div class="btn-group text-nowrap float-end" role="group" />
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </form>
</Modal>
