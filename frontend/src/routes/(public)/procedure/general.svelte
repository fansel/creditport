<script>
  export let generalData;
  export let goToNextTab;
  export let universities;

  

  

</script>


<!-- Tab General -->

<!-- <form action="/?add" method="POST"> -->
<div class="row">
  <div class="col-md">
    <div class="mb-3">
      <label for="uni" class="mb-2">Universität der anzurechnenden Module </label>
      <select
      class="form-select" 
      name="uni"
      id="uni"
      aria-label="Default select example"
      bind:value={generalData.university}
      >
      {#each universities as university, index}
      <option value={index} >{university.uniName}</option>         
      {/each}
      </select>
     
      <!-- <input type="text" id="uni" bind:value={generalData.university} class="form-control" placeholder="Universität Bremen" required /> -->
    </div>
    <div class="mb-3">
      <label for="" class="mb-2">Studiengang der anzurechnenden Module</label>
      <input type="text" id="formerStudies" bind:value={generalData.externalCourseName} class="form-control" placeholder="B.Sc. Informatik"/>
    </div>
    <div class="mb-3">
      <label for="" class="mb-2">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
      <input type="text" id="futureStudies" bind:value={generalData.internalCourseName} class="form-control" placeholder="B.Sc. Wirtschaftsinformatik" />
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
