<script>
  import { enhance } from '$app/forms';
  export let modulesForm;
  export let requests;
  export let removeRequest;
  export let generalData;
  export let modules;
  export let activeTab;
  export let goToNextTab;
  export let addRequest;
</script>

<!-- Tab Module -->

<!-- <h1>Anträge zur Modulanrechnung von Modulen der {generalData.university}</h1> -->

<form action="?/requests" method="POST" enctype="multipart/form-data" use:enhance id="requests" bind:this={modulesForm}>
  <input type="hidden" name="globalAnnotation" value={generalData.annotation} />
  <input type="hidden" name="university" id="university" value={generalData.university} />
  <input type="hidden" name="externalCourseName" id="externalCourseName" value={generalData.externalCourseName} />
  <input type="hidden" name="modulesCount" bind:value={requests.length} />
  {#each requests as { moduleData }, index (index)}
    <div class="card w-100 mb-3" key={index}>
      <div class="card-body">
        <div class="position-relative">
          <button class="btn btn-primary position-absolute top-0 end-0" on:click={() => removeRequest(index)} type="button">Antrag löschen</button>
        </div>
        <div class="row">
          <div class="col-md">
            <h2 class="h4">Fremdmodul</h2>

            <!-- <div class="row">
              <div class="col-md-6 mb-3">
                <label for="" class="mb-2">Universität</label>
                <input type="text" class="form-control" name={`fremduni${index}`} value={generalData.uni} />
              </div>
              <div class="col-md-6">
                <label for="" class="mb-2">Studiengang</label>
                <input type="text" class="form-control" name={`fremdstudiengang${index}`} value={generalData.formerStudies} />
              </div>
            </div> -->

            <div class="row">
              <div class="col-md-10">
                <div class="mb-3">
                  <label for={`externalModule${index}`} class="mb-2">Name des Moduls</label>
                  <input type="text" class="form-control" name={`externalModule${index}`} id={`externalModule${index}`} placeholder="Modellierung und Programmierung" />
                </div>
              </div>
              <div class="col-md-2">
                <div class="mb-3">
                  <label for={`creditPoints${index}`} class="mb-2">LP</label>
                  <input type="text" class="form-control" name={`creditPoints${index}`} id={`creditPoints${index}`} placeholder="5" />
                </div>
              </div>
            </div>

              <div class="mb-3 col-md">
              <label for={`formFile${index}`} class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
              <input class="form-control mb-3" type="file" name={`formFile${index}`} id={`formFile${index}`} />
              <div />

              <div class="row md-3" >
              <div class="mb-3 col-md">
                <label for={`moduleLink${index}`} class="form-label">Website zum Modul</label>
                <input type="text" class="form-control" name={`moduleLink${index}`} id={`moduleLink${index}`} placeholder="http://uni-leipzig.de/module_xyz" />
              </div>
            </div>

            </div>
          </div>
          <div class="col-md">
            <h2 class="h4">Modulvorschlag</h2>

            <div class="mb-2" />
            <div class="mb-3">
              <label for={`internalModule${index}`} class="mb-2">Name des Moduls</label>
              <select class="form-select" name={`internalModule${index}`} id={`internalModule${index}`} aria-label="Default select example" bind:value={moduleData.selectedModul}>
                {#each modules as modul, index}
                  <option value={index}>{modul.moduleName}</option>
                {/each}
              </select>
            </div>

            <p>{modules[moduleData.selectedModul].moduleDescription ?? ''}</p>
          </div>
        </div>
        <div class="form-floating">
          <textarea class="form-control" name={`annotation${index}`} placeholder="Leave a comment here" id={`annotation${index}`} style="height: 100px" />
          <label for={`annotation${index}`}>Kommentare</label>
        </div>
      </div>
      {activeTab === 'pills-general' ? 'active' : ''}
    </div>
  {/each}
  <hr />
  <button type="button" class="btn btn-primary" on:click={goToNextTab}>Weiter</button>

  <div class="w-100 d-inline-flex justify-content-center">
    <button class="btn btn-primary" type="button" on:click={addRequest}>
      <i class="bi bi-plus-circle" style="font-size: 2rem;" />
    </button>
  </div>
</form>
