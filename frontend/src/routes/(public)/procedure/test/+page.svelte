<script>
  import { page } from '$app/stores';
  import { superForm } from 'sveltekit-superforms';
  import { allgemeine_angaben, default_request, modulantraege } from '$root/lib/schema';
  import { zod } from 'sveltekit-superforms/adapters';
  import * as config from '$lib/config';
  import * as api from '$lib/api';
  import * as flashModule from 'sveltekit-flash-message/client';

  import SuperDebug from 'sveltekit-superforms';
  import AddExternalModule from './forms/AddExternalModule.svelte';
  import AddUni from './forms/AddUni.svelte';
  import Accordion from '$root/lib/components/Accordion.svelte';

  export let data;

  let showAddFremdmodul;
  let showAddUni;

  const steps = [zod(allgemeine_angaben), zod(modulantraege)];
  let step = 1;

	$: options.validators = steps[step - 1];

  const { form, errors, message, enhance, validateForm, options } = superForm(data.multiForm, {
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    dataType: 'json',
    validationMethod: 'onblur',
    async onSubmit({ cancel }) {
      console.log('Submit on Client');
      if (step == steps.length) return;
      else cancel();

      const result = await validateForm({ update: true });
      if (result.valid) step = step + 1;
    },

    async onUpdated({ form }) {
      if (form.valid) step = 1;
    }
  });

  $: externalModulsByUni = data.external_modules.filter((m) => m.university?.uniId == $form.universityId);
  $: internalModulesByCourse = data.internal_modules.filter((m) => m.courses?.some((c) => c.courseId == $form.courseId));

  function findExternalModuleById(id) {
    return data.external_modules.find((m) => m.moduleId == id);
  }

  function findInternalModuleById(id) {
    return data.internal_modules.find((m) => m.moduleId == id);
  }

  $: console.log($errors);
</script>

<!-- MODALS -->
<AddExternalModule bind:this={showAddFremdmodul} {data} />
<AddUni bind:this={showAddUni} {data} />

<!-- <ul class="d-flex nav nav-pills mb-3 my-2" id="pills-tab" role="tablist">
  <li class="nav-item d-flex justify-content-between" role="presentation">
    <button
      class="nav-link active rounded-circle border-3 border border-primary status-circle"
      id="pills-home-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-home"
      type="button"
      role="tab"
      aria-controls="pills-1"
      aria-selected="true">1</button
    >
  </li>
  <li class="nav-item d-flex justify-content-between" role="presentation">
    <button
      class="nav-link rounded-circle border-3 border border-primary status-circle"
      id="pills-profile-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-profile"
      type="button"
      role="tab"
      aria-controls="pills-2"
      aria-selected="false">2</button
    >
  </li>
  <li class="nav-item d-flex justify-content-between" role="presentation">
    <button
      class="nav-link rounded-circle border-3 border border-primary status-circle"
      id="pills-contact-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-contact"
      type="button"
      role="tab"
      aria-controls="pills-3"
      aria-selected="false">3</button
    >
  </li>
</ul> -->

<div class="position-relative m-4">
  <div class="progress" role="progressbar" aria-label="Progress" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="height: 1px;">
    <div class="progress-bar" style="width: {(step / 3) * 100}" />
  </div>
  <button type="button" class="position-absolute top-0 start-0 translate-middle btn btn-sm btn-primary rounded-pill" style="width: 2rem; height:2rem;">1</button>
  <button type="button" class="position-absolute top-0 start-50 translate-middle btn btn-sm btn-primary rounded-pill" style="width: 2rem; height:2rem;">2</button>
  <button type="button" class="position-absolute top-0 start-100 translate-middle btn btn-sm btn-secondary rounded-pill" style="width: 2rem; height:2rem;">3</button>
</div>

<hr />

<form action="?/multiForm" method="POST" use:enhance>
  {#if step == 1}
    <div class="uni mb-3">
      <label class="mb-2" for="">Universität der anzurechnenden Module</label>
      <select class="form-select {$errors.universityId ? 'is-invalid' : ''}" bind:value={$form.universityId}>
        {#each data.universities as university, index}
          <option value={university.uniId}>{university.uniName}</option>
        {/each}
      </select>
      {#if $errors.universityId}
        <div class="invalid-feedback">{$errors.universityId}</div>
      {/if}

      <div class="py-2">Universität nicht gefunden <i class="bi bi-question-circle" /></div>

      <button type="button" class="btn btn-primary btn-sm" on:click={() => showAddUni.dialog_open()}>
        <i class="bi bi-plus-circle" />
        Universität
      </button>
    </div>

    <div class="studiengang mb-3">
      <label class="mb-2" for="">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
      <select class="form-select {$errors.courseId ? 'is-invalid' : ''}" bind:value={$form.courseId}>
        {#each data.courses as course, index}
          <option value={course.courseId}>{course.courseName}</option>
        {/each}
      </select>
      {#if $errors.courseId}
        <div class="invalid-feedback">{$errors.courseId}</div>
      {/if}
    </div>
    <div class="annotation mb-3">
      <label class="col-form-label" for=""> Anmerkungen für den Bearbeiter </label>

      <textarea rows="4" type="text" bind:value={$form.annotation} class="form-control {$errors.annotation ? 'is-invalid' : ''}" />
      {#if $errors.annotation}
        <div class="invalid-feedback">{$errors.annotation}</div>
      {/if}
    </div>
    <button class="btn btn-primary" type="submit">Next step</button>
    <!-- <SuperDebug data={$form} /> -->
  {:else}
    {#each $form.requests as _, i}
      <Accordion>
        <div slot="head">
          <h1 class="fs-4 m-0 fw-bold">Antrag {i + 1}</h1>
        </div>
        <button class="btn text-danger" slot="icon" on:click={() => ($form.requests = [...$form.requests.slice(0, i), ...$form.requests.slice(i + 1)])}><i class="bi bi-trash" /> </button>
        <div slot="details">
          <div class="mb-3">
            <div class="row">
              <div class="col-md-6">
                <h2 class="fs-5">Externe Module ({data.external_modules.reduce((sum, obj) => ($form.requests[i].externalModuleId.includes(obj.moduleId) ? sum + obj.creditPoints : sum), 0)} LP)</h2>
                {#each $form.requests[i].externalModuleId as _, j}
                  <!-- {$form.requests[i].externalModuleId[j]} -->

                  <div class="border-bottom pb-4 mb-3">
                    <div class="row">
                      <div class="col-md-9">
                        <label for="" class="col-form-label">Name</label>
                        <select name="" id="" class="form-control {$errors.requests[i]?.externalModuleId[j] ? 'is-invalid' : ''}" bind:value={$form.requests[i].externalModuleId[j]}>
                          <option value="">-</option>
                          {#each externalModulsByUni as module}
                            <option value={module.moduleId}>{module.moduleName}</option>
                          {/each}
                        </select>
                        {#if $errors.requests[i]?.externalModuleId[j]}
                          <div class="invalid-feedback">{$errors.requests[i]?.externalModuleId[j]}</div>
                        {/if}
                      </div>
                      <div class="col-md-3">
                        <label for="" class="col-form-label">LP</label>
                        <input type="number" class="form-control reset-disable-look" disabled value={findExternalModuleById($form.requests[i].externalModuleId[j])?.creditPoints} />
                      </div>
                    </div>
                    <div class="row">
                      <div class="col">
                        <label for="" class="col-form-label">Modulbeschreibung</label>
                        <p class="fake-textarea">{findExternalModuleById($form.requests[i].externalModuleId[j])?.moduleDescription ?? ''}</p>
                      </div>
                    </div>
                    <button
                      class="btn btn-sm btn-outline-danger w-100 mt-3"
                      type="button"
                      on:click={() => ($form.requests[i].externalModuleId = [...$form.requests[i].externalModuleId.slice(0, j), ...$form.requests[i].externalModuleId.slice(j + 1)])}
                    >
                      <i class="bi bi-trash" /> Löschen</button
                    >
                  </div>
                {/each}

                {#if $errors.requests[i]?.externalModuleId?._errors}
                  <div class="invalid-feedback d-block mb-3">{$errors.requests[i]?.externalModuleId?._errors}</div>
                {/if}

                <div class="mb-3">
                  <button class="btn btn-sm btn-outline-primary" type="button" on:click={() => ($form.requests[i].externalModuleId = [...$form.requests[i].externalModuleId, ''])}
                    ><i class="bi bi-plus-circle" />
                    Hinzufügen</button
                  >
                  <button class="btn btn-primary btn-sm" type="button" on:click={() => showAddFremdmodul.dialog_open($form.universityId)}>
                    <i class="bi bi-plus-circle" />
                    Fremdmodul erstellen
                  </button>
                </div>
              </div>
              <div class="col-md-6">
                <h2 class="fs-5">Interne Module ({data.internal_modules.reduce((sum, obj) => ($form.requests[i].internalModuleId.includes(obj.moduleId) ? sum + obj.creditPoints : sum), 0)} LP)</h2>
                {#each $form.requests[i].internalModuleId as _, j}
                  <!-- {$form.requests[i].externalModuleId[j]} -->

                  <div class="border-bottom pb-4 mb-3">
                    <div class="row">
                      <div class="col-md-9">
                        <label for="" class="col-form-label">Name</label>
                        <select name="" id="" class="form-control {$errors.requests[i]?.externalModuleId[j] ? 'is-invalid' : ''}" bind:value={$form.requests[i].internalModuleId[j]}>
                          <option value="">-</option>
                          {#each internalModulesByCourse as module}
                            <option value={module.moduleId}>{module.moduleName}</option>
                          {/each}
                        </select>
                        {#if $errors.requests[i]?.internalModuleId[j]}
                          <div class="invalid-feedback">{$errors.requests[i]?.internalModuleId[j]}</div>
                        {/if}
                      </div>
                      <div class="col-md-3">
                        <label for="" class="col-form-label">LP</label>
                        <input type="number" class="form-control reset-disable-look" disabled value={findInternalModuleById($form.requests[i].internalModuleId[j])?.creditPoints} />
                      </div>
                    </div>
                    <div class="row">
                      <div class="col">
                        <label for="" class="col-form-label">Modulbeschreibung</label>
                        <p class="fake-textarea">
                          {findInternalModuleById($form.requests[i].internalModuleId[j])?.moduleDescription ?? ''}
                        </p>
                      </div>
                    </div>
                    <button
                      class="btn btn-sm btn-outline-danger w-100 mt-3"
                      type="button"
                      on:click={() => ($form.requests[i].internalModuleId = [...$form.requests[i].internalModuleId.slice(0, j), ...$form.requests[i].internalModuleId.slice(j + 1)])}
                    >
                      <i class="bi bi-trash" /> Löschen</button
                    >
                  </div>
                {/each}

                {#if $errors.requests[i]?.internalModuleId?._errors}
                  <div class="invalid-feedback d-block mb-3">{$errors.requests[i]?.internalModuleId?._errors}</div>
                {/if}

                <div class="mb-3">
                  <button class="btn btn-sm btn-outline-primary" type="button" on:click={() => ($form.requests[i].internalModuleId = [...$form.requests[i].internalModuleId, ''])}
                    ><i class="bi bi-plus-circle" />
                    Hinzufügen</button
                  >
                </div>
              </div>
            </div>
          </div>
          <div class="mb-3">
            <label for="" class="col-form-label">Website zum Modul</label>
            <input type="text" class="form-control {$errors.requests[i]?.moduleLink ? 'is-invalid' : ''}" placeholder="https://uni-leipzig.de/module_xyz" bind:value={$form.requests[i].moduleLink} />
            {#if $errors.requests[i]?.moduleLink}
              <div class="invalid-feedback">{$errors.requests[i]?.moduleLink}</div>
            {/if}
          </div>
          <div class="mb-3">
            <label for="" class="col-form-label">Modulbeschreibung</label>
            <input
              type="file"
              name="file"
              class="form-control {$errors.requests[i]?.file ? 'is-invalid' : ''}"
              on:input={(e) => ($form.requests[i].file = e.currentTarget.files?.item(0))}
              accept="application/pdf"
            />
            {#if $errors.requests[i]?.file}
              <div class="invalid-feedback">{$errors.requests[i]?.file}</div>
            {/if}
          </div>
        </div>
      </Accordion>
    {/each}

    {#if $errors.requests?._errors}
      <div class="invalid-feedback d-block mb-3">{$errors.requests?._errors}</div>
    {/if}

    <div class="mb-3 border-bottom pb-3">
      <button class="btn btn-sm btn-outline-primary" type="button" on:click={() => ($form.requests = [...$form.requests, default_request])}><i class="bi bi-plus-circle" /> Neuen Antrag</button>
    </div>

    <div class="annotation mb-3">
      <label class="col-form-label" for=""> Anmerkungen für den Bearbeiter </label>

      <textarea rows="4" type="text" bind:value={$form.annotation} class="form-control {$errors.annotation ? 'is-invalid' : ''}" />
      {#if $errors.annotation}
        <div class="invalid-feedback">{$errors.annotation}</div>
      {/if}
    </div>

    <SuperDebug data={$form} />

    <button class="btn btn-primary w-100">Weiter <i class="bi bi-arrow-right" /> </button>
  {/if}
</form>

<!-- <div class="tab-content my-2" id="pills-tabContent">
  <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab" />

  <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">2</div>
  <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">3</div>
</div> -->

<!-- <button class="btn btn-primary btn-sm" on:click={() => showAddFremdmodul.dialog_open()}>
  <i class="bi bi-plus-circle" />
  Fremdmodul
</button> -->

<style>
  .reset-disable-look {
    background-color: var(--bs-body-bg);
  }

  .fake-textarea {
    height: 110px;
    width: 100%;
    padding: 0.375rem 0.75rem;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    border-radius: var(--bs-border-radius);
    border: var(--bs-border-width) solid var(--bs-border-color);
    margin: 0;
    overflow-y: auto;
  }
</style>
