<script>
  import * as config from '$lib/config';
  import { format, parseISO } from 'date-fns';
  import { page } from '$app/stores';

  import { truncateText } from '$root/lib/util.js';

  import Header from '$lib/components/InternHeader.svelte';
  import Comment from './Comment.svelte';
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';
  import Accordion from '$root/lib/components/Accordion.svelte';

  export let data;
  let procedure = data.procedure;
  let request = data.request;
  let requestBackup = JSON.parse(JSON.stringify(data.request));
  let modules = data.modules;

  let selectedModule = [];
  for (let i = 0; i < request.internalModules.length; i++) {
    const moduleId = request.internalModules[i].moduleId;
    const index = findCurrentModule(modules, moduleId);
    selectedModule[i] = index;
  }

  async function submitForm(event) {
    const body = request;
    delete body.relatedRequests;
    const res = await fetch('/update-request', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(body)
    });
    // successToast('Erfolreich gespeichert');

    console.log(res);
    requestBackup = JSON.parse(JSON.stringify(request));
  }

  async function cancelChanges(event) {
    request = requestBackup;
    submitForm();
  }

  function sumLP(list) {
    let sum = 0;
    for (let i = 0; i < list.length; i++) {
      sum += list[i].creditPoints;
    }
    return sum;
  }

  function findCurrentModule(array, moduleId) {
    for (let i = 0; i < array.length; i++) {
      if (array[i].moduleId === moduleId) {
        return i; // Rückgabe des Index, wenn das gesuchte moduleId gefunden wurde
      }
    }
    return -1; // Rückgabe -1, wenn das gesuchte moduleId nicht gefunden wurde
  }
</script>

<!-- <Header wide={true} /> -->
<div class="m-2">
  <div class="border-bottom m-3 d-flex">
    <div class="vorgangsnummer">
      <strong>Vorgangsnummer: </strong>
      {request.procedureId}
    </div>
    <div class="dates d-none d-lg-block">
      <i class="bi bi-calendar"></i>
      {format(new Date(request.createdAt), 'dd.MM.yyyy')}

      <i class="bi bi-calendar-range"></i>
      {format(new Date(request.createdAt), 'dd.MM.yyyy')}
    </div>

    <div class="studium mx-3">
      <strong>{procedure.course.courseName} </strong>
    </div>

    <div class="mx-2 d-flex align-items-center justify-content-between flex-grow-1">
      <div></div>

      <div class="mx-2 status">
        <RequestStatus status={request.statusRequest} />
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-6">
      {#each request.internalModules as module, index}
        <Accordion>
          <div slot="head">
            <i class="bi {module.verified ? 'bi-patch-check-fill' : 'bi-patch-check'}" />
          </div>
          <div slot="details">
            <div class=" mb-1 p-1">
              <div class="row">
                <div class="col-md-9">
                  <div class="mb-2">
                    <div class="row">
                      <label class="mb-2">Name des Moduls </label>
                    </div>

                    <input type="text" class="form-control" placeholder="Modellierung und Programmierung" bind:value={module.moduleName} />
                  </div>
                </div>
                <div class="col-md-3">
                  <div class="mb-3">
                    <label class="mb-2">LP</label>
                    <input type="number" class="form-control" placeholder={module.creditPoints} />
                  </div>
                </div>
              </div>

              <div class="mb-3">
                <label class="mb-2">Website zum Modul</label>
                <input type="text" class="form-control" placeholder="Füge Link ein" />
              </div>
            </div>
          </div>
        </Accordion>
      {/each}
    </div>
    <div class="col-md-6">
      {#each request.internalModules as module, index}
        <Accordion>
          <div slot="head" class="d-flex justify-content-">
            <span>{truncateText(module.moduleName, 55)}</span>
          </div>
          <div slot="details">
            <div class="p-1">
              <div class="row">
                <div class="col-md-9">
                  <div class="row">
                    <label class="mb-1"><strong>Name des Moduls</strong> </label>
                  </div>

                  <select class="form-select" aria-label="Default select example" bind:value={selectedModule[index]}>
                    {#each modules as modul, index}
                      <option value={index}>{modul.moduleName}</option>
                    {/each}
                  </select>
                </div>
                <div class="col-md-3">
                  <div class="mb-3">
                    <label class="mb-1"><strong>LP</strong></label>
                    <input type="text" class="form-control" disabled placeholder={(request.internalModules[index] = modules[selectedModule[index]]).creditPoints} />
                    <!-- <p class="border p-2 rounded">{module.creditPoints}</p> -->
                  </div>
                </div>
              </div>

              <div class="mb-3">
                <label class="mb-2"><strong>Modulbeschreibung</strong></label>
                <p class="moduleDescription border p-2 rounded">
                  <!-- {#if modules[moduleData.selectedModul[selectedModulIndex]].moduleDescription} -->
                  {(module = modules[selectedModule[index]].moduleDescription)}
                  <!-- {/if} -->
                </p>
                <!-- <input type="text" class="form-control" disabled placeholder={truncateText(modules[0].moduleDescription, 25)} /> -->
              </div>
            </div>
          </div>
        </Accordion>
      {/each}
    </div>
  </div>

  <!-- OVERVIEW MODULE MAPPING -->
  <div class="modules-mapping row">
    <div class="external col-md-6">
      <div class="accordion" id="accordionExample">
        {#each request.externalModules as module, index}
          <div class="accordion-item">
            <h2 class="accordion-header">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse-external-{index}" aria-expanded="true" aria-controls="collapseOne">
                <span>{truncateText(module.moduleName, 55)}</span>
                <span><i class="bi {module.verified ? 'bi-patch-check-fill' : 'bi-patch-check'}"></i></span>
              </button>
            </h2>
            <div id="collapse-external-{index}" class="accordion-collapse collapse {index === 0 ? 'show' : ''}" data-bs-parent="#accordionExample">
              <div class="accordion-body">
                <div class=" mb-1 p-1">
                  <div class="row">
                    <div class="col-md-9">
                      <div class="mb-2">
                        <div class="row">
                          <label class="mb-2">Name des Moduls </label>
                        </div>

                        <input type="text" class="form-control" placeholder="Modellierung und Programmierung" bind:value={module.moduleName} />
                      </div>
                    </div>
                    <div class="col-md-3">
                      <div class="mb-3">
                        <label class="mb-2">LP</label>
                        <input type="number" class="form-control" placeholder={module.creditPoints} />
                      </div>
                    </div>
                  </div>

                  <div class="mb-3">
                    <label class="mb-2">Website zum Modul</label>
                    <input type="text" class="form-control" placeholder="Füge Link ein" />
                  </div>
                </div>
              </div>
            </div>
          </div>
        {/each}
      </div>
    </div>

    <div class="internal col-md-6">
      <div class="accordion" id="accordionExample">
        {#each request.internalModules as module, index}
          <div class="accordion-item">
            <h2 class="accordion-header">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{index}" aria-expanded="true" aria-controls="collapseOne">
                <span>{truncateText((request.internalModules[index] = modules[selectedModule[index]]).moduleName, 50)}</span>
              </button>
            </h2>
            <div id="collapse{index}" class="accordion-collapse collapse {index === 0 ? 'show' : ''}" data-bs-parent="#accordionExample">
              <div class="accordion-body">
                <div class=" mb-1 p-1">
                  <div class="row">
                    <div class="col-md-9">
                      <div class="mb-2">
                        <div class="row">
                          <label class="mb-2">Name des Moduls </label>
                        </div>

                        <select class="form-select" aria-label="Default select example" bind:value={selectedModule[index]}>
                          {#each modules as modul, index}
                            <option value={index}>{modul.moduleName}</option>
                          {/each}
                        </select>
                      </div>
                    </div>
                    <div class="col-md-3">
                      <div class="mb-3">
                        <label class="mb-2">LP</label>
                        <input type="text" class="form-control" disabled placeholder={(request.internalModules[index] = modules[selectedModule[index]]).creditPoints} />
                        <!-- <p class="border p-2 rounded">{module.creditPoints}</p> -->
                      </div>
                    </div>
                  </div>

                  <div class="mb-3">
                    <label class="mb-2">Modulbeschreibung</label>
                    <p class="moduleDescription border p-2 rounded">
                      <!-- {#if modules[moduleData.selectedModul[selectedModulIndex]].moduleDescription} -->
                      {(module = modules[selectedModule[index]].moduleDescription)}
                      <!-- {/if} -->
                    </p>
                    <!-- <input type="text" class="form-control" disabled placeholder={truncateText(modules[0].moduleDescription, 25)} /> -->
                  </div>
                </div>
              </div>
            </div>
          </div>
        {/each}
      </div>
    </div>
  </div>
  <div class="row">
    <div class="col-md-6">
      <div class="m-2">
        <strong>LP Summe: </strong>
        {sumLP(request.externalModules)}
      </div>
    </div>
    <div class="col-md-6">
      <div class="m-2">
        <strong>LP Summe: </strong>
        {sumLP(request.internalModules)}
      </div>
    </div>
  </div>
  <!-- <div class="comment my-3" style="margin-right: 20px">
    <Comment bind:annotationCommittee={request.annotationCommittee} bind:annotationStudent={request.annotationStudent} />
  </div>
  <div class="buttons d-flex ms-auto">
    <button type="submit" class="btn btn-sm btn-primary d-flex" on:click={submitForm}>Speichern</button>
    <div class="btn btn-sm btn-outline-secondary mx-1 d-flex" on:click={cancelChanges}>Abbrechen</div>
    <button type="submit" class="btn btn-sm btn-success mx-1 d-flex justfiy-content-end">Weiterleiten</button>
    <button type="submit" class="btn btn-sm btn-danger mx-1 d-flex justfiy-content-end">Formal ablehnen</button>
  </div> -->
  PDF
</div>

<style>
  i {
    margin-left: 20px;
  }

  .accordion-button:focus {
    box-shadow: none;
  }

  .accordion-button:not(.collapsed) {
    background-color: var(--bs-accordion-bg);
    /* border-bottom: var(--bs-border-width) var(--bs-border-style) var(--bs-border-color) !important; */
    box-shadow: none;
  }
  .moduleDescription {
    min-height: 38px;
    max-height: 120px;
    overflow-y: auto;
  }
  .dates {
  }
</style>
