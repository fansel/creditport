<script>
  import { enhance } from '$app/forms';
  import VirtualList from '@sveltejs/svelte-virtual-list';
  import DeleteUniForm from './forms/DeleteUniForm.svelte';
  import UpdateUniForm from './forms/UpdateUniForm.svelte';
  import ImportUniForm from './forms/ImportUniForm.svelte';
  import AddUniForm from './forms/AddUniForm.svelte';

  export let data;
  export let form;

  $: universities = data.universities;

  let start;
  let end;

  let showDeleteModal = false;
  // let showUpdateModal = false;
  let showAddModal = false;

  let updateForm;
  let importForm;

  let selectedUni;

  let searchTerm = '';
  let searchResults;
  let searchResultsCount = 0;
  // let lowerCaseUniversities = [];

  //TODO
  // - Groß und Kleinschreibung ignorieren beim suchen
  // - BUG wenn man ganz runtergescrollt hat und neu sucht wird Scrollbar nicht geupdatet
  // - eigene Implementierung von VirtualList
  // - letztes Item hat doppelten Border-Bottom

  function compareUniNames(a, b) {
    return a.uniName.localeCompare(b.uniName);
  }

  $: searchResultsCount = searchResults.length;

  $: searchResults = universities
    .filter((uni) => {
      return uni.uniName.includes(searchTerm);
    })
    .sort(compareUniNames);

  function dialog_open(id) {
    const uni = universities.find((u) => u.uniId == id);
    if (!uni) {
      console.error('Uni not found');
    }
    updateForm.dialog_open(uni);
  }
</script>

<DeleteUniForm uni={selectedUni} bind:showModal={showDeleteModal} />
<UpdateUniForm bind:this={updateForm} data={data.updateUniForm} />
<AddUniForm bind:showModal={showAddModal} />
<ImportUniForm bind:this={importForm} data={data.importUniForm} />

<div class="row">
  <div class="col">
    <h4 class=" d-flex flex-wrap gap-3">Vorschlagliste</h4>
  </div>

  <div class="col-8">
    <div class="form-inline d-flex align-items-center no-wrap">
      <input type="text" placeholder="Suche" class="form-control form-control-sm" bind:value={searchTerm} />
    </div>
  </div>
</div>

<div class="row mb-3">
  <div class="col">
    <button class="btn btn-primary btn-sm text-nowrap" on:click={() => (showAddModal = true)}>
      <i class="bi bi-plus-circle" />
      Universität hinzufügen
    </button>
    <button class="btn btn-primary btn-sm" on:click={() => importForm.dialog_open()}>
      <i class="bi bi-cloud-arrow-up" />
      Importieren
    </button>
  </div>
</div>

<ul class="list-group mb-3 uni-table border">
  <li class="uni-table-header border-bottom font-sm">
    <div class="row">
      <div class="col-8">Name</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  <div class="uni-table-body">
    <VirtualList items={searchResults} height="332px" bind:start bind:end let:item>
      <li class="uni-table-item border-bottom">
        <div class="row">
          <div class="col-8 d-flex align-items-center">
            {item.uniName}
            {#if item.verified}
              <i class="bi bi-check-circle ms-2 text-primary" />
            {/if}
          </div>
          <div class="col d-flex align-items-center">
            <!-- <form method="POST" use:enhance>
              <input type="hidden" name="id" value={item.uniId} /> -->

            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary" on:click={() => dialog_open(item.uniId)}>Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger" on:click={() => ((showDeleteModal = true), (selectedUni = item))}>Löschen</button>
            </div>
            <!-- </form> -->
          </div>
        </div>
      </li>
    </VirtualList>
  </div>
</ul>

{#if searchResultsCount < universities.length}
  <p>{searchResultsCount} Treffer</p>
{/if}

<style>
  .no-wrap {
    white-space: nowrap;
  }

  .font-sm {
    font-size: 0.875rem;
  }

  .uni-table {
    list-style-type: none;
  }

  .uni-table-header {
    width: 100%;
    padding: 0.5rem 1rem;
  }

  .uni-table-body {
    overflow-y: auto;
  }

  .uni-table-item {
    /* border-bottom: 1px solid rgb(222, 226, 230); */
    width: 100%;
    padding: 0.5rem 1rem;
  }

  .uni-table-item:hover {
    background-color: rgba(0, 0, 0, 0.075);
  }
</style>
