<script>
  import VirtualList from '@sveltejs/svelte-virtual-list';
  import AddCourseForm from './form/AddCourseForm.svelte';
  import UpdateCourseForm from './form/UpdateCourseForm.svelte';
  import DeleteCourseForm from './form/DeleteCourseForm.svelte';
  import UpdateModuleForm from './form/UpdateModuleForm.svelte';
  import DeleteModuleForm from './form/DeleteModuleForm.svelte';

  export let data;

  let start;
  let end;

  let updateCourseForm;
  let addCourseForm;
  let updateModuleForm;

  let showDeleteModuleModal = false;
  let showDeleteCourseModal = false;

  let searchTerm = '';
  let searchResults;
  let searchResultsCount = 0;

  let selectedCourseId;
  let selectedModule;

  // $: console.log(selectedModule)

  $: searchResultsCount = searchResults.length;

  $: selectedCourse = data.courses.find((c) => c.courseId == selectedCourseId);
  $: selectedModules = selectedCourse?.internalModules || [];

  function compareModuleNames(a, b) {
    return a.moduleName.localeCompare(b.moduleName);
  }

  $: searchResults = selectedModules
    .filter((module) => {
      return module.moduleName.toLowerCase().includes(searchTerm);
    })
    .sort(compareModuleNames);

  function test() {}

  function course_dialog_open(id) {
    const course = data.courses.find((u) => u.courseId == id);
    if (!course) {
      console.error('Course not found');
    }
    updateCourseForm.dialog_open(course);
  }

  function module_dialog_open(id) {
    const module = data.modules.find((u) => u.moduleId == id);
    if (!module) {
      console.error('Module not found');
    }
    updateModuleForm.dialog_open(module);
  }
</script>

<UpdateCourseForm bind:this={updateCourseForm} data={data.updateCourseForm} />
<UpdateModuleForm bind:this={updateModuleForm} data={data.updateModuleForm} />
<AddCourseForm bind:this={addCourseForm} data={data.addCourseForm} />
<DeleteCourseForm course={selectedCourse} bind:showModal={showDeleteCourseModal} />
<DeleteModuleForm module={selectedModule} bind:showModal={showDeleteModuleModal} />

<div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
  <h4 class="m-0">Interne Studiengänge</h4>

  <div class="d-flex flex-wrap gap-2">
    <button class="btn btn-primary btn-sm text-nowrap" on:click={addCourseForm.dialog_open()}>
      <i class="bi bi-plus-circle" />
      Neu
    </button>
    <button class="btn btn-primary btn-sm" on:click={test}>
      <i class="bi bi-cloud-arrow-up" />
      Importieren
    </button>
  </div>
</div>

<div class="mb-3">
  <select name="" id="" class="form-select" bind:value={selectedCourseId}>
    {#each data.courses as course}
      <option value={course.courseId}>{course.courseName} ({course.internalModules?.length} Module)</option>
    {/each}
  </select>
  <div class="d-flex justify-content-between mt-3">
    <p class="m-0">
      <i class="bi bi-question-circle me-2" />
      Verwalte die internen Studiengänge der Universtität Leipzig. Um Module zu bearbeiten wähle den Studiengang aus der Liste.
    </p>
    <div class="ms-3 hstack flex-column flex-md-row gap-2">
      <button class="btn btn-outline-primary btn-sm text-nowrap" on:click={course_dialog_open(selectedCourseId)}>Name bearbeiten</button>
      <button class="btn btn-outline-danger btn-sm text-nowrap" on:click={() => (showDeleteCourseModal = true)}>Löschen</button>
    </div>
  </div>
</div>

<hr />

<div class="d-flex justify-content-between align-items-center mb-2 flex-wrap gap-2">
  <h4 class="m-0">Dazugehörige Module</h4>

  <div class="d-flex flex-wrap gap-2">
    <button class="btn btn-primary btn-sm text-nowrap" on:click={test}>
      <i class="bi bi-plus-circle" />
      Modul hinzufügen
    </button>
    <button class="btn btn-primary btn-sm" on:click={test}>
      <i class="bi bi-cloud-arrow-up" />
      Module importieren
    </button>
  </div>
</div>

<div class="row mb-3">
  <div class="col">
    <div class="form-inline d-flex align-items-center no-wrap">
      <input type="text" placeholder="Suche" class="form-control form-control-sm" bind:value={searchTerm} />
    </div>
  </div>
</div>

<!-- <p>Wähle einen Studiengang aus um die dazugehörigen Module zu bearbeiten.</p> -->
<ul class="list-group mb-3 cp-table border">
  <li class="cp-table-header border-bottom font-sm">
    <div class="row">
      <div class="col-8">Name</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  <div class="cp-table-body">
    <VirtualList items={searchResults} height="332px" let:item>
      <li class="cp-table-item border-bottom">
        <div class="row gy-2">
          <div class="col col-md-8 d-flex align-items-center">
            {item.moduleName}
          </div>
          <div class="col-auto col-md d-flex align-items-center">
            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary" on:click={module_dialog_open(item.moduleId)}>Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger" on:click={() => ((selectedModule = item), (showDeleteModuleModal = true))}>Löschen</button>
            </div>
          </div>
        </div>
      </li>
    </VirtualList>
  </div>
</ul>

{#if searchResultsCount < selectedModules.length}
  <p>{searchResultsCount} Treffer</p>
{/if}

<style>
</style>
