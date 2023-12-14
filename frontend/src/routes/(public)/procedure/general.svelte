<script>
  export let generalData;
  export let goToNextTab;
</script>

<!-- Tab General -->

  <!-- <form action="/?add" method="POST"> -->
  <div class="row">
    <div class="col-md">
      <div class="mb-3">
        <label for="uni" class="mb-2">Universität der anzurechnenden Module </label>
        <input type="text" id="uni" bind:value={generalData.university} class="form-control" placeholder="Universität Bremen" />
      </div>
      <div class="mb-3">
        <label for="" class="mb-2">Studiengang der anzurechnenden Module</label>
        <input type="text" id="formerStudies" bind:value={generalData.formerStudies} class="form-control" placeholder="B.Sc. Informatik" />
      </div>
      <div class="mb-3">
        <label for="" class="mb-2">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
        <input type="text" id="futureStudies" bind:value={generalData.futureStudies} class="form-control" placeholder="B.Sc. Wirtschaftsinformatik" />
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

