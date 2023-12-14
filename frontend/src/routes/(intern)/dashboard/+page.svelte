<script>
  import { format } from 'date-fns';
  import Status from '$lib/components/Status.svelte';

  export let data;
</script>

<h1 class="my-3">Dashboard</h1>

<div class="row">
  <div class="col-md">
    <div
      class="card
    mb-3"
    >
      <div class="card-header">
        <h2 class="fs-1">{data.open_procedures}</h2>
        <p>Offen</p>
      </div>
      <div class="card-body">
        <a href="/procedures" class="btn btn-sm btn-outline-secondary">Zu den Vorgängen</a>
        <a href="/procedures" class="btn btn-sm btn-outline-secondary float-end">Bearbeiten</a>
      </div>
    </div>
  </div>
  <div class="col-md">
    <div class="card mb-3">
      <div class="card-header">
        <h2 class="fs-1">{data.processing_procdures}</h2>
        <p>In Bearbeitung</p>
      </div>
      <div class="card-body">
        <a href="/procedures" class="btn btn-sm btn-outline-secondary">Zu den Vorgängen</a>
        <a href="/procedures" class="btn btn-sm btn-outline-secondary float-end">Neu</a>
      </div>
    </div>
  </div>
  <div class="col-md">
    <div class="card mb-3">
      <div class="card-header">
        <h2 class="fs-1">{data.archived_procedures}</h2>
        <p>Archiviert</p>
      </div>
      <div class="card-body">
        <a href="/procedures" class="btn btn-sm btn-outline-secondary">Zu den Jobs</a>
        <a href="/procedures" class="btn btn-sm btn-outline-secondary float-end">Neu</a>
      </div>
    </div>
  </div>
</div>

<div class="row">
  <div class="col-md">
    <div class="card mb-3" style="max-height: 243px;">
      <div class="card-header d-flex justify-content-between align-items-center" style="display: inherit;">
        <p class="fs-3 mb-0">Vorgänge</p>
      </div>
      <div class="card-body overflow-y-auto p-0">
        <table class="log-table text-left table-responsive">
          <tbody>
            {#each data.procedures as procedure}
            <tr>
              <td>{format(new Date(procedure.createdAt), 'dd-MM-yyyy')}</td>
              <td>{format(new Date(procedure.createdAt), 'HH:mm')}</td>

              <td>{procedure.university ?? '-'}</td>

              <td>{procedure.courseName ?? '-'}</td>
              <td><span class="badge bg-primary rounded-pill">{procedure.requests.length} Anträge</span></td>
              <td><Status status={procedure.status} /></td>

              <td class="float-end">
                <a type="button" href="/procedures" class="btn btn-sm btn-outline-primary ms-2 me-2">
                  <i class="bi bi-chevron-right" />
                </a>
              </td>
            </tr>
            {/each}
          </tbody>
        </table>

        <!-- <a href="/logs" class="btn btn-sm btn-outline-danger text-danger">Zu den Logfiles</a> -->
      </div>

      <div class="card-footer">
        <!-- <small class="text-muted">Last updated {formatDistance(last_log_time, new Date(), { addSuffix: true })}</small> -->
        <small class="text-muted">Last updated vor 2 Stunden</small>
      </div>
    </div>
  </div>
</div>

<style>
  .log-table {
    width: 100%;
  }

  .log-table tr {
    border-width: 0 0 1px;
    border-color: #dee2e6;
  }

  .log-table tr:hover {
    background-color: rgba(0, 0, 0, 0.075);
  }

  .log-table tr td {
    padding: 0.5rem 1rem;
  }

  .log-table tr td:nth-child(-n + 3) {
    width: 1%;
    white-space: nowrap;
  }

  .log-table tr:last-child {
    border-bottom-width: 0;
  }
</style>
