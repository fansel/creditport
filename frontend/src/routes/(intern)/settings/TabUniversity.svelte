<script>
  import { enhance } from '$app/forms';
  import VirtualList from '@sveltejs/svelte-virtual-list';
  import DeleteUniForm from './Forms/DeleteUniForm.svelte';
  import UpdateUniForm from './Forms/UpdateUniForm.svelte';
  import AddUniForm from './Forms/AddUniForm.svelte';

  export let universities;

  let start;
  let end;

  let showDeleteModal = false;
  let showUpdateModal = false;
  let showAddModal = false;

  let selectedID;
  let selectedName;

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
</script>

<DeleteUniForm id={selectedID} bind:showModal={showDeleteModal} />
<UpdateUniForm id={selectedID} name={selectedName} bind:showModal={showUpdateModal} />
<AddUniForm bind:showModal={showAddModal} />

<div class="row mb-3">
  <div class="col">
    <h4 class=" d-flex">
      Vorschlagliste
      <button class="btn btn-primary btn-sm ms-4" on:click={() => (showAddModal = true)}>
        <i class="bi bi-plus-circle" />
        Universität hinzufügen
      </button>
    </h4>
  </div>

  <div class="col-8">
    <div class="form-inline d-flex align-items-center no-wrap">
      <input type="text" placeholder="Suche" class="form-control form-control-sm" bind:value={searchTerm} />
    </div>
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
          </div>
          <div class="col d-flex align-items-center">
            <!-- <form method="POST" use:enhance>
              <input type="hidden" name="id" value={item.uniId} /> -->

            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary" on:click={() => ((showUpdateModal = true), (selectedID = item.uniId), (selectedName = item.uniName))}>Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger" on:click={() => ((showDeleteModal = true), (selectedID = item.uniId))}>Löschen</button>
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
