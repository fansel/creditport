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
  export let addTitleToRequest;
  export let addModuleToRequest;
  export let removeTitleFromRequest;
  export let removeModuleFromRequest;

</script>


<form action="?/requests" method="POST" enctype="multipart/form-data" id="requests" bind:this={modulesForm} use:enhance>
  <input type="hidden" name="globalAnnotation" value={generalData.annotation} />
  <input type="hidden" name="university" id="university" value={generalData.university} />
  <input type="hidden" name="externalCourseName" id="externalCourseName" value={generalData.externalCourseName} />
  <input type="hidden" name="modulesCount" bind:value={requests.length} />

  <div class="accordion" id="accordionExample">
    {#each requests as { moduleData }, index (index)}
      <div class="accordion-item full-border">
        <div class="row">
          <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button position-relative" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{index}" aria-expanded="false" aria-controls="collapseOne">
              <div class="col-md col-md-62">
                {#each moduleData.title as title, titleIndex}
                  <h6 class="h6" key={titleIndex}>{title ? `${title} ` : 'Neues Modul'}</h6>
                {/each}
              </div>
              <div class="col-md">
                {#each moduleData.selectedModul as selectedModul, selectedModulIndex}
                    <h6 class="h6" key={selectedModulIndex}>
                    <!-- selectedModul == 0 ist Auswahl "nichts" siehe select" -->
                      {selectedModul !== 0 ? `${modules[selectedModul-1].moduleName}` : 'Neues Modul'}
                    </h6>
                {/each}
                <button class="btn p-0 position-absolute top-50 end-0 translate-middle-y me-5" on:click={() => removeRequest(index)} type="button"><i class="bi bi-trash3-fill" /> </button>
              </div>
            </button>
          </h2>
        </div>
        <div id="collapse{index}" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
          <div class="accordion-body">
            <div class="row">
              <div class="col-md">
                <h5 class="h5">Externe Modul(e)</h5>

                {#each moduleData.title as title, titleIndex}
                  <div class=" border mb-5 border-3 rounded p-2">
                    <div class="row">
                      <div class="col-md-10">
                        <div class="mb-3">
                          <div class="row">
                            <label for={`externalModule${titleIndex}`} class="mb-2"
                              >Name des Moduls
                              <button class="btn p-0 me-3" on:click={() => removeTitleFromRequest(index, titleIndex)} type="button">
                                <i class="bi bi-trash3-fill" />
                              </button>
                            </label>
                          </div>

                          <input
                            type="text"
                            class="form-control"
                            name={`externalModule${titleIndex}`}
                            id={`externalModule${titleIndex}`}
                            placeholder="Modellierung und Programmierung"
                            bind:value={moduleData.title[titleIndex]}
                          />
                        </div>
                      </div>
                      <div class="col-md-2">
                        <div class="mb-3">
                          <label for={`creditPoints${titleIndex}`} class="mb-2">LP</label>
                          <input type="number" class="form-control" name={`creditPoints${titleIndex}`} id={`creditPoints${titleIndex}`} placeholder="5" />
                        </div>
                      </div>
                    </div>

                    <div class="mb-3 col-md">
                      <label for={`formFile${titleIndex}`} class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
                      <input class="form-control mb-3" type="file" name={`formFile${titleIndex}`} id={`formFile${titleIndex}`} accept="application/pdf" />
                    </div>

                    <div class="mb-3">
                      <label for={`moduleLink${titleIndex}`} class="mb-2">Website zum Modul</label>
                      <input type="text" class="form-control" name={`moduleLink${titleIndex}`} id={`moduleLink${titleIndex}`} placeholder="http://uni-leipzig.de/module_xyz" />
                    </div>
                  </div>
                {/each}

                <button class="mb-3 btn btn-primary d-inline-flex align-items-center" type="button" on:click={addTitleToRequest(index)}>Fremdmodul<i class="ms-2 bi bi-plus-circle" /></button>
              </div>

              <div class="col-md">
                <h5 class="h5">Interne Modul(e)</h5>


           
                {#each moduleData.selectedModul as selectedModul, selectedModulIndex}
                  <div class="border mb-5 border-3 rounded p-2">
                    <div class="row">
                      <div class="" />
                      <div class="mb-3 col-md-10">
                        <label for={`internalModule${selectedModulIndex}`} class="mb-2"
                          >Name
                          <button class="btn p-0 me-3" on:click={() => removeModuleFromRequest(index, selectedModulIndex)} type="button">
                            <i class="bi bi-trash3-fill" />
                          </button>
                        </label>
                        <select
                          class="form-select"
                          name={`internalModule${selectedModulIndex}`}
                          id={`internalModule${selectedModulIndex}`}
                          aria-label="Default select example"
                          bind:value={moduleData.selectedModul[selectedModulIndex]}
                        >
                        <option value={0} disabled selected hidden>Bitte treffen Sie eine Auswahl</option>
                          {#each modules as modul, index}
                          <option value={index+1} >{modul.moduleName}</option>         
                          {/each}
                        </select>
                      </div>
                      <div class="col-md-2">
                        {#if modules[moduleData.selectedModul[selectedModulIndex]].creditPoints}
                          <p>{modules[moduleData.selectedModul[selectedModulIndex]].creditPoints ?? ''}</p>
                        {/if}
                      </div>
                      <div class="col-md">
                        <p class="moduleDescription border p-3 rounded">
                          {#if modules[moduleData.selectedModul[selectedModulIndex]].moduleDescription}
                            {modules[moduleData.selectedModul[selectedModulIndex]].moduleDescription}
                          {/if}
                        </p>
                      </div>
                    </div>
                  </div>
                {/each}
                
                <button class="mb-3 btn btn-primary d-inline-flex align-items-center" type="button" on:click={addModuleToRequest(index)}>Modul Leipzig<i class="ms-2 bi bi-plus-circle" /></button>
              </div>
            </div>

            <div class="form-floating">
              <textarea class="form-control" name={`annotation${index}`} placeholder="Leave a comment here" id={`annotation${index}`} style="height: 100px" />
              <label for={`annotation${index}`}>Kommentare</label>
            </div>
          </div>
        </div>
        
      </div>
      <br>
    {/each}
  </div>

  
  <div class="my-3 d-flex justify-content-between">
    <button class="btn btn-primary d-inline-flex align-items-center" type="button" on:click={addRequest}>Antrag Hinzufügen<i class="ms-2 bi bi-plus-circle" /></button>
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
    height: 155px;
    overflow-y: auto;
  }

  .col-md-62 {
    margin-right: 45px;
  }

  .full-border {
    border: 5px solid #dee2e6; /* Ändere die Breite und den Stil nach Bedarf */
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
