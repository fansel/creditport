<script>
  
  import { superForm } from 'sveltekit-superforms/client';

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
  
  
  //let newUniForm;

  if (uniId !== undefined) {
    generalData.universityId = uniId;
}

</script>

<!-- Tab General -->
{#if showAddUniversity}
  <div class="full-border rounded mb-4 p-4">
    <div class="">
      <p class="text-warning">Sie sind dabei eine neue Universität hinzuzufügen. Bitte achten Sie auf die korrekte Schreibweise!</p>
      <form action="?/addUniversity" method="POST" id="requests">
        <div class="mb-3">
          <label for="newUniversityName" class="form-label">Name der Universität</label>
          <input type="text" name="universityName" class="form-control" id="newUniversityName" bind:value={newUniversityName} />
        </div>
        <button type="submit" class="btn btn-primary" on:click={() => (showUniversityName = true)}>Hinzufügen</button>
        <button type="button" class="btn btn-primary" on:click={() => (showAddUniversity = false)}>abbrechen</button>
      </form>
    </div>
  </div>
{/if}

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
        <p class="text-info">falls Ihre Universität nicht in der Liste auftaucht können Sie diese manuell hinzufügen</p>
        <button type="button" class="btn btn-primary" on:click={() => (showAddUniversity = true)}>Neue Universität hinzufügen</button>

        <!-- <input type="text" id="uni" bind:value={generalData.university} class="form-control" placeholder="Universität Bremen" required /> -->
      </div>
    {/if}
    
    <div class="mb-3">
      <label for="" class="mb-2">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
      <select class="form-select" name="internalCourseId" id="internalCourseId" aria-label="Default select example" bind:value={generalData.internalCourseId}>
        {#each courses as course , index}
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
