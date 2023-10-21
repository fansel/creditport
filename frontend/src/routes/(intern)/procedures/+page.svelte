<script>
  import { format, parseISO } from 'date-fns';
  import { enhance } from '$app/forms';

  export let data;

  console.log(new Date());
</script>

<div class="row py-3 mb-3 pb-md-1 border-bottom align-items-center">
  <div class="col-md">
    <h1>Vorgänge</h1>
  </div>

  <div class="col-auto col">
    <div class="btn-group flex-fill dropwdown">
      <button class="dropdown-toggle btn btn-outline-primary btn-sm" type="button" data-bs-toggle="dropdown">Sortieren</button>
      <div class="shadow dropdown-menu-right dropdown-menu">
        <div class="w-100 d-flex pb-2 mb-1 border-bottom">
          <input type="radio" class="btn-check ng-untouched ng-pristine ng-valid" />
          <label for="listSortReverseFalse" class="btn btn-outline-primary btn-sm mx-2 flex-fill">
            <i class="bi bi-sort-alpha-down" />
          </label>

          <input type="radio" class="btn-check ng-untouched ng-pristine ng-valid" />
          <label for="listSortReverseTrue" class="btn btn-outline-primary btn-sm me-2 flex-fill">
            <i class="bi bi-sort-alpha-up" />
          </label>
        </div>
        <div>
          <button class="dropdown-item" tabindex="0">Eingereicht am</button>
          <button class="dropdown-item" tabindex="0">Zuletzt bearbeitet</button>
          <button class="dropdown-item" tabindex="0">Status</button>
          <button class="dropdown-item" tabindex="0">Universität</button>
          <button class="dropdown-item" tabindex="0">Studiengang</button>
        </div>
      </div>
    </div>
  </div>
  <div class="col-auto col">
    <div class="btn-group flex-fill dropwdown">
      <button class="dropdown-toggle btn btn-outline-primary btn-sm" type="button" data-bs-toggle="dropdown">Anzahl</button>
      <div class="shadow dropdown-menu-right dropdown-menu">
        <button class="dropdown-item" tabindex="0">25</button>
        <button class="dropdown-item" tabindex="0">50</button>
        <button class="dropdown-item" tabindex="0">100</button>
        <button class="dropdown-item" tabindex="0">Alle</button>
      </div>
    </div>
  </div>
</div>

<div class="row align-items-center pt-3 pt-sm-4 pb-3 pb-lg-4 bg-body gy-3">
  <div class="col-md">
    <div class="form-inline d-flex align-items-center">
      <input type="text" placeholder="Suche" class="form-control form-control-sm" />
    </div>
  </div>

  <div class="col-auto text-end">
    <div class="d-flex flex-wrap gap-3">
      <div class="btn-group flex-fill dropwdown">
        <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Bearbeitet am</button>
        <div class="shadow dropdown-menu-right dropdown-menu">
          <button class="dropdown-item" tabindex="0">offen</button>
          <button class="dropdown-item" tabindex="0">bearbeitet</button>
          <button class="dropdown-item" tabindex="0">archiviert</button>
        </div>
      </div>

      <div class="btn-group flex-fill dropwdown">
        <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Erstellt am</button>
        <div class="shadow dropdown-menu-right dropdown-menu">
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Vor 7 Tagen</button>
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Diesen Monat</button>
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Dieses Jahr</button>
        </div>
      </div>
      <div class="d-flex flex-wrap">
        <div class="btn-group flex-fill dropwdown">
          <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Status</button>
          <div class="shadow dropdown-menu-right dropdown-menu">
            <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">offen <span class="badge bg-secondary-subtle text-dark text-end">24</span></button>
            <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">bearbeitet <span class="badge bg-secondary-subtle text-dark text-end">24</span></button>
            <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">archiviert <span class="badge bg-secondary-subtle text-dark text-end">24</span></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="d-flex flex-wrap gap-3 mb-3 justify-content-between align-items-center">
  <div class="d-flex align-center">
    {data.procedures.count} Vorgänge (gefiltert)
    <button class="btn btn-link py-0"><i class="bi bi-x" />Filter zurücksetzen</button>
  </div>

  <div>
    <nav aria-label="..." class="">
      <ul class="pagination pagination-sm mb-0">
        <li class="page-item"><button class="page-link" aria-label="Previous"><span aria-hidden="true">&laquo;</span></button></li>

        <li class="page-item active">
          <button class="page-link">1</button>
        </li>

        <li class="page-item">
          <button class="page-link">2</button>
        </li>
        <li class="page-item">
          <button class="page-link">3</button>
        </li>
        <li class="page-item"><button class="page-link" aria-label="Next"><span aria-hidden="true">&raquo;</span></button></li>
      </ul>
    </nav>
  </div>
</div>

<div class="table-responsive">
  <table class="table table-sm table-hover table-responsive border align-middle shadow-sm">
    <thead>
      <tr>
        <th>Eingereicht am</th>
        <th>Universität</th>
        <th>Studiengang</th>
        <th>Anzahl der Anträge</th>
        <th>Status</th>
        <th />
      </tr>
    </thead>

    <tbody>
      {#each data.procedures.data as procedure}
        <tr>
          <td>{format(new Date(), 'dd.MM.yyyy HH:mm')} </td>
          <td>{procedure.university}</td>

          <td>{procedure.course}</td>
          <td>{procedure.requestCount}</td>

          <td><span class="badge bg-secondary-subtle border border-secondary-subtle text-secondary-emphasis rounded-pill">eingereicht</span></td>

          <td>
            <div class="btn-group text-nowrap float-end" role="group">
              <form method="POST" action="?/delete" use:enhance>
                <input type="hidden" name="id" value="1" />
                <a type="button" href="/jobs/2" class="btn btn-sm btn-primary btn-group-right"><i class="bi bi-pencil-square" /></a>
              </form>
            </div>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
</div>

<div class="d-flex flex-wrap gap-3 mb-3 justify-content-between align-items-center">
  <div class="d-flex align-center">
    {data.procedures.count} Vorgänge (gefiltert)
    <button class="btn btn-link py-0"><i class="bi bi-x" />Filter zurücksetzen</button>
  </div>

  <div>
    <nav aria-label="..." class="">
      <ul class="pagination pagination-sm mb-0">
        <li class="page-item"><button class="page-link" aria-label="Previous"><span aria-hidden="true">&laquo;</span></button></li>

        <li class="page-item active">
          <button class="page-link">1</button>
        </li>

        <li class="page-item">
          <button class="page-link">2</button>
        </li>
        <li class="page-item">
          <button class="page-link">3</button>
        </li>
        <li class="page-item"><button class="page-link" aria-label="Next"><span aria-hidden="true">&raquo;</span></button></li>
      </ul>
    </nav>
  </div>
</div>

<style>
</style>
