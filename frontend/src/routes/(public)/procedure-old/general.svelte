<script>
  import { superForm } from 'sveltekit-superforms/client';
  import AddUniForm from '$root/routes/(intern)/settings/universities/forms/AddUniForm.svelte';

  export let generalData;
  export let goToNextTab;
  export let universities;
  export let uniId;
  export let uniName;
  export let courses;
  //export let addUniversityForm;
  // export let data;
  // const {addUniversityForm, messages, errors, enhance} = superForm(data.addUniversityForm);

  let showAddUniversity = false;
  let showUniversityName = false;
  let newUniversityName = '';
  let showAddModal = false;

  // console.log(testUni);

  //let newUniForm;

  if (uniId !== undefined) {
    generalData.universityId = uniId;
  }

  function dialog_open(id) {
    const uni = universities.find((u) => u.uniId == id);
    if (!uni) {
      console.error('Uni not found');
    }
    updateForm.dialog_open(uni);
  }
</script>

<AddUniForm bind:showModal={showAddModal} />

<!-- Tab General -->

{#if uniId !== undefined}
  <label for="uniName">Der Name der hinzugefügten Universität lautet: </label>
  <div id="uniName" class="border rounded mb-2">
    <div class="p-2">
      {uniName}
      <!-- <p>die id lautet: {uniId}</p> -->
    </div>
  </div>
{/if}

<!-- <form action="/?add" method="POST"> -->

<div class="row">
  <div class="col-md">
    {#if !showAddUniversity && uniId === undefined}
      <div class="mb-3">
        <label for="uni" class="mb-2">Universität der anzurechnenden Module </label>
        <select class="form-select" name="uniId" id="uniId" aria-label="Default select example" bind:value={generalData.universityId}>
          {#each universities as university, index}
            <option value={university.uniId}>{university.uniName} </option>
          {/each}
        </select>
        <div class="py-2">Universität nicht gefunden <i class="bi bi-question-circle"></i></div>
        <!-- <button type="button" class="btn btn-primary" on:click={() => (showAddUniversity = true)}>Neue Universität hinzufügen</button> -->
        <button class="btn btn-primary btn-sm text-nowrap" on:click={() => (showAddModal = true)}>
          <i class="bi bi-plus-circle" />
          Hinzufügen
          <!-- </button> <button type="button" class="btn btn-primary" on:click={() => (showAddUniversity = false)}>abbrechen</button> -->
        </button>

        <!-- <input type="text" id="uni" bind:value={generalData.university} class="form-control" placeholder="Universität Bremen" required /> -->
      </div>
    {/if}

    <div class="mb-3">
      <label for="" class="mb-2">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
      <select class="form-select" name="internalCourseId" id="internalCourseId" aria-label="Default select example" bind:value={generalData.internalCourseId}>
        {#each courses as course, index}
          <option value={course.courseId}>{course.courseName} </option>
        {/each}
      </select>
      <!-- <input type="text" id="futureStudies" bind:value={generalData.internalCourseName} class="form-control" placeholder="B.Sc. Wirtschaftsinformatik" /> -->
    </div>
    <div class="form-floating">
      <textarea class="form-control" placeholder="Leave a comment here" id="globalAnnotation" bind:value={generalData.annotation} style="height: 100px" />
      <label for="globalAnnotation">Kommentare</label>
    </div>
  </div>
</div>
<hr />
<button type="button" class="btn btn-primary" on:click={goToNextTab}>Weiter</button>

<!-- </form> -->
<style>
  .full-border {
    border: 5px solid #dee2e6; /* Ändere die Breite und den Stil nach Bedarf */
  }
</style>
