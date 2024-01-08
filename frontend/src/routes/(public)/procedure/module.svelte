<script>
  import { enhance } from '$app/forms';
  export let modulesForm;
  export let requests;
  export let removeRequest;
  export let generalData;
  export let modules;
  // export let activeTab;
  export let goToNextTab;
  export let addRequest;
</script>

<!-- Tab Module -->

<!-- <h1>Anträge zur Modulanrechnung von Modulen der {generalData.university}</h1> -->

<form action="?/requests" method="POST" enctype="multipart/form-data" id="requests" bind:this={modulesForm} use:enhance>
  <input type="hidden" name="globalAnnotation" value={generalData.annotation} />
  <input type="hidden" name="university" id="university" value={generalData.university} />
  <input type="hidden" name="externalCourseName" id="externalCourseName" value={generalData.externalCourseName} />
  <input type="hidden" name="modulesCount" bind:value={requests.length} />

  <div class="accordion" id="accordionExample">
    {#each requests as { moduleData }, index (index)}
      <div class="accordion-item">
        <div class="row">
          <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button position-relative" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{index}" aria-expanded="false" aria-controls="collapseOne">
              <div class="col-md col-md-62">
                <h6 class="h6">{moduleData.title ? `${moduleData.title} ` : 'Neues Modul'}</h6>
              </div>
              <div class="col-md">
                <h6 class="h6">{moduleData.title ? `${modules[moduleData.selectedModul].moduleName}` : 'Neues Modul'}</h6>
                <button class="btn p-0 position-absolute top-50 end-0 translate-middle-y me-5" on:click={() => removeRequest(index)} type="button"><i class="bi bi-trash3-fill" /> </button>
              </div>
            </button>
          </h2>
        </div>
        <div id="collapse{index}" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
          <div class="accordion-body">
            <div class="row">
              <div class="col-md">
                <h5 class="h5">Fremdmodul</h5>

                <div class="row">
                  <div class="col-md-10">
                    <div class="mb-3">
                      <label for={`externalModule${index}`} class="mb-2">Name des Moduls</label>
                      <input
                        type="text"
                        class="form-control"
                        name={`externalModule${index}`}
                        id={`externalModule${index}`}
                        placeholder="Modellierung und Programmierung"
                        bind:value={moduleData.title}
                      />
                    </div>
                  </div>
                  <div class="col-md-2">
                    <div class="mb-3">
                      <label for={`creditPoints${index}`} class="mb-2">LP</label>
                      <input type="number" class="form-control" name={`creditPoints${index}`} id={`creditPoints${index}`} placeholder="5" />
                    </div>
                  </div>
                </div>

                <div class="mb-3 col-md">
                  <label for={`formFile${index}`} class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
                  <input class="form-control mb-3" type="file" name={`formFile${index}`} id={`formFile${index}`} accept="application/pdf" />
                </div>

                <div class="mb-3">
                  <label for={`moduleLink${index}`} class="mb-2">Website zum Modul</label>
                  <input type="text" class="form-control" name={`moduleLink${index}`} id={`moduleLink${index}`} placeholder="http://uni-leipzig.de/module_xyz" />
                </div>
              </div>

              <div class="col-md">
                <h5 class="h5">Modulvorschlag</h5>
                <div class="row">
                  <div class="" />
                  <div class="mb-3 col-md-10">
                    <label for={`internalModule${index}`} class="mb-2">Name</label>
                    <select class="form-select" name={`internalModule${index}`} id={`internalModule${index}`} aria-label="Default select example" bind:value={moduleData.selectedModul}>
                      {#each modules as modul, index}
                        <option value={index}>{modul.moduleName}</option>
                      {/each}
                    </select>
                  </div>
                  <div class="col-md-2">
                    {#if modules[moduleData.selectedModul].creditPoints}
                      <p>{modules[moduleData.selectedModul].creditPoints ?? ''}</p>
                    {/if}
                  </div>
                  <div class="col-md">
                    {#if modules[moduleData.selectedModul].moduleDescription}
                      <p class="moduleDescription border p-3 rounded">{modules[moduleData.selectedModul].moduleDescription ?? ''}</p>
                    {/if}
                  </div>
                </div>
              </div>
            </div>
            <div class="form-floating">
              <textarea class="form-control" name={`annotation${index}`} placeholder="Leave a comment here" id={`annotation${index}`} style="height: 100px" />
              <label for={`annotation${index}`}>Kommentare</label>
            </div>
          </div>
        </div>
      </div>
    {/each}
  </div>

  <!-- {#each requests as { moduleData }, index (index)}
    <div class="card w-100 mb-3" key={index}>
      <div class="card-body">
        <div class="position-relative">
          <button class="btn btn-primary position-absolute top-0 end-0" on:click={() => removeRequest(index)} type="button">Antrag löschen</button>
        </div>
      </div>
    </div>
  {/each} -->
  <div class="my-3 d-flex justify-content-between">
    <button class="btn btn-primary d-inline-flex align-items-center" type="button" on:click={addRequest}>Modul hinzufügen<i class="ms-2 bi bi-plus-circle" /></button>
    <!-- <button type="button" class="btn btn-primary">Alle ausklappen</button> -->

    <button type="button" class="btn btn-primary" on:click={goToNextTab}>Weiter</button>
  </div>
</form>

<style>
  .accordion-button:focus {
    box-shadow: none;
  }

  .accordion-button:not(.collapsed) {
    background-color: var(--bs-accordion-bg);
    /* border-bottom: var(--bs-border-width) var(--bs-border-style) var(--bs-border-color) !important; */
    box-shadow: none;
  }

  .moduleDescription {
    max-height: 155px;
    overflow-y: auto;
  }

  .col-md-62 {
    margin-right: 45px;
  }

  /* .form-control::placeholder {
    color: red;
    opacity: 1; 
  }

  .form-control:-ms-input-placeholder {
    color: red;
  }

  .form-control::-ms-input-placeholder {
    color: red;
  } */
</style>
