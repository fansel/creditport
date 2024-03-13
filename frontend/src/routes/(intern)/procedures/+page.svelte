<script>
  import { format, parseISO } from 'date-fns';
  import { enhance } from '$app/forms';
  import { onMount } from 'svelte';
  import { formatProcdureID } from '$lib/util.js';
  import { status_procedures } from '$lib/config';

  import EditProcedureForm from './forms/EditProcedureForm.svelte';
  import ProcedureStatus from '$lib/components/ProcedureStatus.svelte';

  /* https://www.npmjs.com/package/date-picker-svelte */
  import { DateInput, localeFromDateFnsLocale } from 'date-picker-svelte';
  import { de } from 'date-fns/locale';

  export let data;

  $: procedures = data.procedures;
  $: proceduresSearchIndex = createSearchIndex(procedures);

  let showEditModal = false;
  let selectedProcedure;

  let locale = localeFromDateFnsLocale(de);

  let searchTerm = '';
  let searchResults;
  let searchResultsCount = 0;
  let sortingByField = 'createdAt';
  let sortingDirection = false; //true DESC - false ASC
  let filtered = false;

  let startDateCreatedAt = null;
  let endDateCreatedAt = null;
  let startDateLastUpdated = null;
  let endDateLastUpdated = null;

  let createdAtFilter = { start: null, end: null };
  let lastUpdatedFilter = { start: null, end: null };

  let statusFilter = Object.fromEntries(status_procedures.map(({ match }) => [match, false]));
  $: filteredByStatus = Object.values(statusFilter).some((value) => value === true);

  $: filtered = !(
    searchTerm == '' &&
    Object.values(statusFilter).every((value) => value === false) &&
    createdAtFilter.start == null &&
    createdAtFilter.end == null &&
    lastUpdatedFilter.start == null &&
    lastUpdatedFilter.end == null
  );

  /* TODO
  - Pagination
  - Anzahl der Treffer pro Seite
  - Filtern nach Erstellt am
  - Filtern nach Bearbeitet am
  - PUT Request für Procedure
  - Archivierte per default nicht anzeigen
  
  */

  /**
   * Erstellt eine Vergleichsfunktion für die Sortierung von Objekten nach einer bestimmten Eigenschaft.
   * @param {string} property - Die Eigenschaft, nach der sortiert werden soll.
   * @param {boolean} reversed - Optionaler Parameter zum Umkehren der Sortierreihenfolge. Standardmäßig false.
   * @returns {Function} - Die Vergleichsfunktion für die Sortierung.
   */
  function compareByProperty(property, sortingDirection = false) {
    if (property != '') {
      return function (a, b) {
        const comparison = a[property].localeCompare(b[property]);
        return sortingDirection ? -comparison : comparison;
      };
    }
  }

  // Filter zurücksetzen
  function resetFilters() {
    searchTerm = '';
    statusFilter = Object.fromEntries(status_procedures.map(({ match }) => [match, false])); // Alle Werte auf false setzten
    createdAtFilter = { start: null, end: null };
    lastUpdatedFilter = { start: null, end: null };
  }

  // Eine Funktion die zählt wie oft ein gewisser Status (Bsp: NEU) in procedures vorkommt
  function countStatus(arr, status) {
    // Zähler für Status 1 initialisieren
    let count = 0;

    // Durch das Array iterieren
    arr.forEach((obj) => {
      // Überprüfen, ob das Statusattribut gleich 1 ist
      if (obj.status === status) {
        // Wenn ja, erhöhe den Zähler um 1
        count++;
      }
    });

    // Die Anzahl der Vorkommen von Status 1 zurückgeben
    return count;
  }

  // Erweitert die Procedure Objekte um ein neues Attribut searchIndex in dem in Kleinbuchstaben alle wichtigen Suchfelder aneinandergehängt werden
  const createSearchIndex = (arr) => arr.map((obj) => ({ ...obj, searchIndex: `${obj.procedureId}${obj.status}${obj.university}${obj.createdAt}${obj.courseName}`.toLowerCase() }));

  $: searchResultsCount = searchResults.length;
  $: searchResults = proceduresSearchIndex
    .filter((proc) => {
      const filterBySearch = proc.searchIndex.includes(searchTerm);

      let filterByStatus = true;
      if (statusFilter && filteredByStatus) filterByStatus = statusFilter[proc.status];

      let filterByCreatedAt = true;
      let filterBylastUpdateAt = true;

      return filterBySearch && filterByStatus && filterByCreatedAt && filterBylastUpdateAt;
    })
    .sort(compareByProperty(sortingByField, sortingDirection));
</script>

<EditProcedureForm procedure={selectedProcedure} bind:showModal={showEditModal} />

<div class="row py-3 mb-3 pb-md-1 border-bottom align-items-center">
  <div class="col-md">
    <h1>Vorgänge</h1>
  </div>

  <div class="col-auto col">
    <div class="btn-group flex-fill dropwdown">
      <button class="dropdown-toggle btn btn-outline-primary btn-sm" type="button" data-bs-toggle="dropdown">Sortieren</button>

      <div class="shadow dropdown-menu-right dropdown-menu">
        <!-- svelte-ignore a11y-click-events-have-key-events -->
        <!-- svelte-ignore a11y-no-static-element-interactions -->
        <div class="w-100 d-flex pb-2 mb-1 border-bottom" on:click|stopPropagation>
          <button class="btn {!sortingDirection ? 'btn-primary' : 'btn-outline-primary'} btn-sm mx-2 flex-fill" on:click={() => (sortingDirection = false)}>
            <i class="bi bi-sort-alpha-down" />
          </button>
          <button class="btn {!sortingDirection ? 'btn-outline-primary' : 'btn-primary'} btn-sm me-2 flex-fill" on:click={() => (sortingDirection = true)}> <i class="bi bi-sort-alpha-up" /></button>
        </div>
        <div>
          <button class="dropdown-item" class:active={sortingByField == 'createdAt'} tabindex="0" on:click={() => (sortingByField = 'createdAt')}>Erstellt am</button>
          <button class="dropdown-item" class:active={sortingByField == 'lastUpdated'} tabindex="0" on:click={() => (sortingByField = 'lastUpdated')}>Bearbeitet am</button>
          <button class="dropdown-item" class:active={sortingByField == 'status'} tabindex="0" on:click={() => (sortingByField = 'status')}>Status</button>
          <button class="dropdown-item" class:active={sortingByField == 'university'} tabindex="0" on:click={() => (sortingByField = 'university')}>Universität</button>
          <button class="dropdown-item" class:active={sortingByField == 'courseName'} tabindex="0" on:click={() => (sortingByField = 'courseName')}>Studiengang</button>
        </div>
      </div>
    </div>
  </div>
  <div class="col-auto col">
    <div class="btn-group flex-fill dropwdown">
      <button class="dropdown-toggle btn btn-outline-primary btn-sm" type="button" data-bs-toggle="dropdown">Anzahl</button>
      <div class="shadow dropdown-menu-right dropdown-menu text-sm">
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
      <input type="text" placeholder="Suche" class="form-control form-control-sm" bind:value={searchTerm} />
    </div>
  </div>

  <div class="col-auto text-end">
    <div class="d-flex flex-wrap gap-3">
      <div class="btn-group flex-fill dropwdown">
        <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Erstellt am</button>
        <div class="shadow dropdown-menu-right dropdown-menu bordered-dropdown">
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Vor 7 Tagen</button>
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Diesen Monat</button>
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Dieses Jahr</button>

          <!-- svelte-ignore a11y-click-events-have-key-events -->
          <!-- svelte-ignore a11y-no-static-element-interactions -->
          <div class="dropdown-item date-picker-holder" on:click|stopPropagation>
            <label for="" class="mb-1">Nach</label>
            <DateInput bind:value={startDateCreatedAt} closeOnSelection={true} {locale} format="dd.MM.yyyy" placeholder="dd.mm.yyyy" />
          </div>
          <!-- svelte-ignore a11y-click-events-have-key-events -->
          <!-- svelte-ignore a11y-no-static-element-interactions -->
          <div class="dropdown-item date-picker-holder" on:click|stopPropagation>
            <label for="" class="mb-1">Vor</label>
            <DateInput bind:value={endDateCreatedAt} closeOnSelection={true} {locale} format="dd.MM.yyyy" placeholder="dd.mm.yyyy" />
          </div>
        </div>
      </div>

      <div class="btn-group flex-fill dropwdown">
        <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Bearbeitet am</button>
        <div class="shadow dropdown-menu-right dropdown-menu bordered-dropdown">
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Vor 7 Tagen</button>
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Diesen Monat</button>
          <button class="dropdown-item d-inline-flex justify-content-between" tabindex="0">Dieses Jahr</button>

          <!-- svelte-ignore a11y-click-events-have-key-events -->
          <!-- svelte-ignore a11y-no-static-element-interactions -->
          <div class="dropdown-item date-picker-holder" on:click|stopPropagation>
            <label for="" class="mb-1">Nach</label>
            <DateInput bind:value={startDateLastUpdated} closeOnSelection={true} {locale} format="dd.MM.yyyy" placeholder="dd.mm.yyyy" />
          </div>
          <!-- svelte-ignore a11y-click-events-have-key-events -->
          <!-- svelte-ignore a11y-no-static-element-interactions -->
          <div class="dropdown-item date-picker-holder" on:click|stopPropagation>
            <label for="" class="mb-1">Vor</label>
            <DateInput bind:value={endDateLastUpdated} closeOnSelection={true} {locale} format="dd.MM.yyyy" placeholder="dd.mm.yyyy" />
          </div>
        </div>
      </div>

      <div class="d-flex flex-wrap">
        <div class="btn-group flex-fill dropwdown">
          <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Status</button>

          <div class="shadow dropdown-menu-right dropdown-menu bordered-dropdown">
            {#each status_procedures as status, index}
              <input type="checkbox" class="btn-check" id="statusCheckbox{status.text_intern}" bind:checked={statusFilter[status.match]} disabled={countStatus(procedures, status.match) < 1} />
              <label
                for="statusCheckbox{status.text_intern}"
                class="dropdown-item d-inline-flex justify-content-between align-items-center {countStatus(procedures, status.match) < 1 ? 'disabled' : ''} {statusFilter[status.match]
                  ? 'active'
                  : 'false'}"
              >
                {status.text_intern}
                <span class="badge bg-secondary-subtle text-dark text-end ms-3">{countStatus(procedures, status.match)}</span>
              </label>
            {/each}
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="d-flex flex-wrap gap-3 mb-3 justify-content-between align-items-center">
  <div class="d-flex align-center">
    {searchResultsCount} Vorgänge {filtered ? '(gefiltert)' : ''}
    {#if filtered}
      <button class="btn btn-link py-0" on:click={resetFilters}><i class="bi bi-x" />Filter zurücksetzen</button>
    {/if}
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
        <th>Erstellt am</th>
        <th>Vorgangsnummer</th>
        <th>Universität</th>
        <th>Studiengang</th>
        <th>Anzahl der Anträge</th>
        <th>Status</th>
        <th />
      </tr>
    </thead>

    <tbody>
      {#each searchResults as procedure}
        <tr>
          <td>{format(new Date(procedure.createdAt), 'dd.MM.yyyy HH:mm')} </td>
          <td>{formatProcdureID(String(procedure.procedureId))} </td>
          <td>{procedure.university.uniName ?? '-'}</td>

          <td>{procedure.courseName ?? '-'}</td>
          <td>{procedure.requestDetails.length ?? '-'}</td>

          <td><ProcedureStatus status={procedure.status} /></td>

          <td>
            <div class="btn-group text-nowrap float-end" role="group">
              <form method="POST" use:enhance>
                <button class="text-danger btn p-1 me-2" formaction="?/archiv"> <i class="bi bi-archive " /> </button>

                <input type="hidden" name="id" value={procedure.procedureId} />
                <button type="button" on:click={() => ((showEditModal = true), (selectedProcedure = procedure))} class="btn btn-sm btn-primary btn-group-right"
                  ><i class="bi bi-pencil-square" /></button
                >
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
    {searchResultsCount} Vorgänge {filtered ? '(gefiltert)' : ''}
    {#if filtered}
      <button class="btn btn-link py-0" on:click={resetFilters}><i class="bi bi-x" />Filter zurücksetzen</button>
    {/if}
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
  .bordered-dropdown .dropdown-item:not(:last-child) {
    border-bottom: 1px solid var(--bs-border-color);
  }

  .dropdown-item.date-picker-holder:active {
    background-color: transparent;
    color: var(--bs-dropdown-link-color);
  }
</style>
