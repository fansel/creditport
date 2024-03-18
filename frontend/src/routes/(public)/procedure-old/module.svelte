<script>
  //import { enhance } from '$app/forms';
  export let modulesForm;
  export let requests;
  export let removeRequest;
  export let generalData;
  export let internalModules;
  export let externalModules;
  //export let universities;
  // export let activeTab;
  export let goToNextTab;
  export let addRequest;
  export let addTitleToRequest;
  export let addModuleToRequest;
  export let removeExternalModulFromRequest;
  export let removeInternalModulFromRequest;

  // function handleSelection(event,request,module) {
  //   //selectedModuleId = event.target.value; // Aktualisiere die ausgewählte Modul-ID
  //   requests[request].moduleData[module].selectedInternalIndex = event.target.selectedIndex;
  //   console.log(requests[request].moduleData[module].selectedInternalIndex) ;// Aktualisiere den ausgewählten Index
  // }
</script>

<form action="?/requests" method="POST" enctype="multipart/form-data" id="requests" bind:this={modulesForm}>
  <input type="hidden" name="globalAnnotation" value={generalData.annotation} />
  <input type="hidden" name="universityId" id="universityId" value={generalData.universityId} />
  <input type="hidden" name="internalCourseId" id="internalCourseId" value={generalData.internalCourseId} />

  <input type="hidden" name="requestCount" bind:value={requests.length} />
  <p>{generalData.universityId}</p>
  <div class="accordion" id="accordionExample">
    {#each requests as { moduleData }, index (index)}
      <input type="hidden" name={`internalModulesCount${index}`} bind:value={moduleData.selectedInternalModuleIndex.length} />
      <input type="hidden" name={`externalModulesCount${index}`} bind:value={moduleData.selectedExternalModuleIndex.length} />
      <div class="accordion-item full-border">
        <div class="row">
          <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button position-relative" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{index}" aria-expanded="false" aria-controls="collapseOne">
              <div class="col-md col-md-62">
                <!-- {#each moduleData.selectedExternalModuleIndex as selectedExternalModuleIndex, modulIndex}
                  <h6 class="h6" key= {modulIndex}>{selectedExternalModuleIndex ? `${selectedExternalModuleIndex} ` : 'Neues Modul'}</h6>
                {/each} -->
                {#each moduleData.selectedExternalModuleIndex as selectedExternalModuleIndex, selectedModulIndex}
                  <h6 class="h6" key={selectedModulIndex}>
                    <!-- wie kann ich nichts selecten? -->
                    {selectedExternalModuleIndex !== undefined ? `${externalModules[moduleData.selectedExternalModuleIndex[selectedModulIndex]].moduleName}` : 'Neues Modul'}
                  </h6>
                {/each}
              </div>
              <div class="col-md">
                {#each moduleData.selectedInternalModuleIndex as selectedInternalModuleIndex, selectedModulIndex}
                  <h6 class="h6" key={selectedModulIndex}>
                    <!-- wie kann ich nichts selecten? -->
                    {selectedInternalModuleIndex !== undefined ? `${internalModules[moduleData.selectedInternalModuleIndex[selectedModulIndex]].moduleName}` : 'Neues Modul'}
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

                {#each moduleData.selectedExternalModuleIndex as selectedExternalModuleIndex, selectedModulIndex}
                  <div class="border mb-5 border-3 rounded p-2">
                    <div class="row">
                      <div class="mb-3 col-md-10">
                        <label for={`externalModule${selectedModulIndex}`} class="mb-2"
                          >Name
                          <button class="btn p-0 me-3" on:click={() => removeExternalModulFromRequest(index, selectedModulIndex)} type="button">
                            <i class="bi bi-trash3-fill" />
                          </button>
                        </label>
                        <select
                          class="form-select"
                          name={`externalModule${index}-${selectedModulIndex}`}
                          id={`externalModule${index}-${selectedModulIndex}`}
                          aria-label="Default select example"
                          bind:value={moduleData.selectedExternalModuleIndex[selectedModulIndex]}
                        >
                          {#each externalModules as modul, index}
                            <option value={index}>{modul.moduleName}</option>
                          {/each}
                        </select>
                        <input type="hidden" name={`externalModuleId${index}-${selectedModulIndex}`} value={externalModules[moduleData.selectedExternalModuleIndex[selectedModulIndex]].moduleId} />
                      </div>
                      <div class="col-md-2">
                        <label for={`creditPoints${index}-${selectedModulIndex}`} class="pt-1 mb-2">LP</label>
                        <div type="number" class="form-control border rounded text-center mb-2" style="height: 36px">
                          {#if externalModules[moduleData.selectedExternalModuleIndex[selectedModulIndex]].creditPoints}
                            <p>{externalModules[moduleData.selectedExternalModuleIndex[selectedModulIndex]].creditPoints ?? ''}</p>
                          {/if}
                        </div>
                      </div>
                    </div>
                    <div class="col-md">
                      <p class="moduleDescription border p-3 rounded">
                        {#if externalModules[moduleData.selectedExternalModuleIndex[selectedModulIndex]].moduleDescription}
                          {externalModules[moduleData.selectedExternalModuleIndex[selectedModulIndex]].moduleDescription}
                        {/if}
                      </p>
                    </div>
                  </div>
                  <!-- <div class=" border mb-5 border-3 rounded p-2">
                    <div class="row">
                      <div class="col-md-10">
                        <div class="mb-3">
                          <div class="row">
                            <label for={`externalModule${index}-$ modulIndex}`} class="mb-2"
                              >Name des Moduls
                              <button class="btn p-0 me-3" on:click={() => removeExternalModulFromRequest(index, modulIndex)} type="button">
                                <i class="bi bi-trash3-fill" />
                              </button>
                            </label>
                          </div>

                          <input
                            type="text"
                            class="form-control"
                            name={`externalModule${index}-$ modulIndex}`}
                            id={`externalModule${index}-$ modulIndex}`}
                            placeholder="Modellierung und Programmierung"
                            bind:value={moduleData.selectedExternalModuleIndex[modulIndex]}
                          />
                        </div>
                      </div>
                      <div class="col-md-2">
                        <div class="mb-3">
                          <label for={`creditPoints${index}-$ modulIndex}`} class="mb-2">LP</label>
                          <input type="number" class="form-control" name={`creditPoints${index}-$ modulIndex}`} id={`creditPoints${index}-$ modulIndex}`} placeholder="5" />
                        </div>
                      </div>
                    </div>

                    
                  </div> -->
                {/each}
                
                  <button
                    class="mb-3 btn btn-primary d-inline-flex align-items-center"
                    type="button"
                    on:click={() => {
                      addTitleToRequest(index);
                    }}
                    >Fremdmodul aus Liste<i class="ms-2 bi bi-plus-circle" />
                  </button>
                  <button class="mb-3 btn btn-primary d-inline-flex align-items-center">Fremdmodul neu erstellen</button>
                
              </div>

              <div class="col-md">
                <h5 class="h5">Interne Modul(e)</h5>

                {#each moduleData.selectedInternalModuleIndex as selectedInternalModuleIndex, selectedModulIndex}
                  <div class="border mb-5 border-3 rounded p-2">
                    <div class="row">
                      <div class="mb-3 col-md-10">
                        <label for={`internalModule${selectedModulIndex}`} class="mb-2"
                          >Name
                          <button class="btn p-0 me-3" on:click={() => removeInternalModulFromRequest(index, selectedModulIndex)} type="button">
                            <i class="bi bi-trash3-fill" />
                          </button>
                        </label>
                        <select
                          class="form-select"
                          name={`internalModule${index}-${selectedModulIndex}`}
                          id={`internalModule${index}-${selectedModulIndex}`}
                          aria-label="Default select example"
                          bind:value={moduleData.selectedInternalModuleIndex[selectedModulIndex]}
                        >
                          <!-- <option value={0} disabled selected hidden>Bitte treffen Sie eine Auswahl</option> -->
                          {#each internalModules as modul, index}
                            <option value={index}>{modul.moduleName}</option>
                          {/each}
                        </select>
                        <input type="hidden" name={`internalModuleId${index}-${selectedModulIndex}`} value={internalModules[moduleData.selectedInternalModuleIndex[selectedModulIndex]].moduleId} />
                      </div>
                      <div class="col-md-2">
                        <label for={`creditPoints${index}-${selectedModulIndex}`} class="pt-1 mb-2">LP</label>
                        <div type="number" class="form-control border rounded text-center mb-2" style="height: 36px">
                          {#if internalModules[moduleData.selectedInternalModuleIndex[selectedModulIndex]].creditPoints}
                            <p>{internalModules[moduleData.selectedInternalModuleIndex[selectedModulIndex]].creditPoints ?? ''}</p>
                          {/if}
                        </div>
                      </div>
                    </div>
                    <div class="col-md">
                      <p class="moduleDescription border p-3 rounded">
                        {#if internalModules[moduleData.selectedInternalModuleIndex[selectedModulIndex]].moduleDescription}
                          {internalModules[moduleData.selectedInternalModuleIndex[selectedModulIndex]].moduleDescription}
                        {/if}
                      </p>
                    </div>
                  </div>
                {/each}

                <button class="mb-3 btn btn-primary d-inline-flex align-items-center" type="button" on:click={addModuleToRequest(index)}>Modul Leipzig<i class="ms-2 bi bi-plus-circle" /></button>
              </div>
            </div>
            <div class="mb-3 col-md">
              <label for={`formFile${index}`} class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
              <input class="form-control mb-3" type="file" name={`formFile${index}-$ modulIndex}`} id={`formFile${index}-$ modulIndex}`} accept="application/pdf" />
            </div>

            <div class="mb-3">
              <label for={`moduleLink${index}`} class="mb-2">Website zum Modul</label>
              <input type="text" class="form-control" name={`moduleLink${index}`} id={`moduleLink${index}`} placeholder="http://uni-leipzig.de/module_xyz" />
            </div>
            <div class="form-floating">
              <textarea class="form-control" name={`annotation${index}`} placeholder="Leave a comment here" id={`annotation${index}`} style="height: 100px" />
              <label for={`annotation${index}`}>Kommentare</label>
            </div>
          </div>
        </div>
      </div>

      <br />
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
