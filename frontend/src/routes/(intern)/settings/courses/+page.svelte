<script>
  import VirtualList from '@sveltejs/svelte-virtual-list';
  import UpdateCourseForm from './form/UpdateCourseForm.svelte';

  export let data;

  let start;
  let end;

  let updateCourseForm;
  let addCourseForm;

  let selectedCourseId;
  $: selectedCourse = data.courses.find((c) => c.courseId == selectedCourseId);
  $: selectedModules = selectedCourse?.internalModules || [];

  function test() {}

  function course_dialog_open(id) {
    const course = data.courses.find((u) => u.courseId == id);
    if (!course) {
      console.error('Course not found');
    }
    updateCourseForm.dialog_open(course);
  }
</script>

<UpdateCourseForm bind:this={updateCourseForm} data={data.updateCourseForm} />

<div class="d-flex justify-content-between align-items-center mb-3 flex-wrap gap-2">
  <h4 class="m-0">Interne Studiengänge</h4>

  <div class="d-flex flex-wrap gap-2">
    <button class="btn btn-primary btn-sm text-nowrap" on:click={test}>
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
    <div>
      <button class="btn btn-outline-primary btn-sm text-nowrap" on:click={test}>Name bearbeiten</button>
      <button class="btn btn-outline-danger btn-sm text-nowrap" on:click={test}>Löschen</button>
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
<!-- <p>Wähle einen Studiengang aus um die dazugehörigen Module zu bearbeiten.</p> -->
<ul class="list-group mb-3 cp-table border">
  <li class="cp-table-header border-bottom font-sm">
    <div class="row">
      <div class="col-8">Name</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  <div class="cp-table-body">
    <VirtualList items={selectedModules} height="332px" let:item>
      <li class="cp-table-item border-bottom">
        <div class="row gy-2">
          <div class="col col-md-8 d-flex align-items-center">
            {item.moduleName}
          </div>
          <div class="col-auto col-md d-flex align-items-center">
            <div class="btn-group">
              <button class="btn btn-sm btn-outline-primary" on:click={test}>Bearbeiten</button>
              <button class="btn btn-sm btn-outline-danger" on:click={test}>Löschen</button>
            </div>
          </div>
        </div>
      </li>
    </VirtualList>
  </div>
</ul>

<style>
</style>
