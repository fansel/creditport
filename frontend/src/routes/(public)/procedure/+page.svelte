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
  import { tweened } from 'svelte/motion';
  import { cubicOut } from 'svelte/easing';

  export let data;

  let showAddFremdmodul;
  let showAddUni;

  const stepPercentage = tweened(1, { duration: 300, easing: cubicOut });

  $: $stepPercentage = ((step - 1) / 2) * 100;

  const steps = [zod(allgemeine_angaben), zod(modulantraege), zod(modulantraege)];
  let step = 1;

  $: options.validators = steps[step - 1];

  const { form, errors, message, enhance, validateForm, options } = superForm(data.multiForm, {
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    dataType: 'json',
    validationMethod: 'onsubmit',

    async onSubmit({ cancel }) {
      console.log('Submit on Client');
      if (step == 3) return;
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

  function findUniById(id) {
    return data.universities.find((u) => u.uniId == id);
  }

  function findCourseById(id) {
    return data.courses.find((c) => c.courseId == id);
  }

  function switchStep(toStep) {
    if (toStep < step) {
      step = toStep;
    } else {
      alert('Du kannst nicht vorspringen.');
    }
  }

  $: console.log($errors);
</script>

<!-- MODALS -->
<AddExternalModule bind:this={showAddFremdmodul} {data} />
<AddUni bind:this={showAddUni} {data} />

<!-- Status Leiste -->
<div class="position-relative status-leiste">
  <div class="progress" role="progressbar" aria-label="Progress" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100" style="height: 2px;">
    <div class="progress-bar" style="width: {$stepPercentage}%" />
  </div>
  <button type="button" on:click={() => switchStep(1)} class="fw-bold {step == 1 ? 'active' : ''} status-circle position-absolute top-0 start-0 translate-middle btn rounded-pill">1</button>
  <button type="button" on:click={() => switchStep(2)} class="fw-bold {step == 2 ? 'active' : ''} status-circle position-absolute top-0 start-50 translate-middle btn rounded-pill">2</button>
  <button type="button" on:click={() => switchStep(3)} class="fw-bold {step == 3 ? 'active' : ''} status-circle position-absolute top-0 start-100 translate-middle btn rounded-pill">3</button>
</div>

<!-- {#if step == 1}
  <div class="w-100 d-inline-flex justify-content-start"><div class="fw-bold text-primary">Allgemeine Angaben</div></div>
{:else if step == 2}
  <div class="w-100 d-inline-flex justify-content-center"><div class="fw-bold text-primary">Modulanträge</div></div>
{:else if step == 3}
  <div class="w-100 d-inline-flex justify-content-end"><div class="fw-bold text-primary">Zusammenfassung</div></div>
{/if} -->

<hr />

<form action="?/multiForm" method="POST" use:enhance>
  {#if step == 1}
    <div class="uni mb-3">
      <label class="mb-2" for="">Universität der anzurechnenden Module</label>
      <select class="form-select {$errors?.universityId ? 'is-invalid' : ''}" bind:value={$form.universityId}>
        <option value="">-</option>
        {#each data.universities as university, index}
          <option value={university.uniId}>{university.uniName}</option>
        {/each}
      </select>
      {#if $errors?.universityId}
        <div class="invalid-feedback">{$errors?.universityId}</div>
      {/if}

      <div class="py-2">Universität nicht gefunden <i class="bi bi-question-circle" /></div>

      <button type="button" class="btn btn-primary btn-sm" on:click={() => showAddUni.dialog_open()}>
        <i class="bi bi-plus-circle" />
        Universität
      </button>
    </div>

    <div class="studiengang mb-3">
      <label class="mb-2" for="">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
      <select class="form-select {$errors?.courseId ? 'is-invalid' : ''}" bind:value={$form.courseId}>
        <option value="">-</option>
        {#each data.courses as course, index}
          <option value={course.courseId}>{course.courseName}</option>
        {/each}
      </select>
      {#if $errors?.courseId}
        <div class="invalid-feedback">{$errors?.courseId}</div>
      {/if}
    </div>
    <div class="annotation mb-3">
      <label class="col-form-label" for=""> Anmerkungen für den Bearbeiter </label>

      <textarea rows="4" type="text" bind:value={$form.annotation} class="form-control {$errors?.annotation ? 'is-invalid' : ''}" />
      {#if $errors?.annotation}
        <div class="invalid-feedback">{$errors?.annotation}</div>
      {/if}
    </div>
    <hr />
    <button class="btn btn-primary" type="submit">Weiter</button>
    <!-- <SuperDebug data={$form} /> -->
  {:else if step == 2}
    {#each $form.requests as _, i}
      <Accordion>
        <div slot="head">
          <h1 class="fs-4 m-0 fw-bold">Antrag {i + 1}</h1>
        </div>
        <button type="button" class="btn text-danger" slot="icon" on:click={() => ($form.requests = [...$form.requests.slice(0, i), ...$form.requests.slice(i + 1)])}
          ><i class="bi bi-trash" />
        </button>
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
                        <select name="" id="" class="form-control {$errors?.requests?.[i]?.externalModuleId?.[j] ? 'is-invalid' : ''}" bind:value={$form.requests[i].externalModuleId[j]}>
                          <option value="">-</option>
                          {#each externalModulsByUni as module}
                            <option value={module.moduleId}>{module.moduleName}</option>
                          {/each}
                        </select>
                        {#if $errors?.requests?.[i]?.externalModuleId?.[j]}
                          <div class="invalid-feedback">{$errors?.requests?.[i]?.externalModuleId?.[j]}</div>
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
                      class="btn btn-sm btn-outline-secondary w-100 mt-3"
                      type="button"
                      on:click={() => ($form.requests[i].externalModuleId = [...$form.requests[i].externalModuleId.slice(0, j), ...$form.requests[i].externalModuleId.slice(j + 1)])}
                    >
                      <i class="bi bi-trash" /> Löschen</button
                    >
                  </div>
                {/each}

                {#if $errors?.requests?.[i]?.externalModuleId?._errors}
                  <div class="invalid-feedback d-block mb-3">{$errors?.requests?.[i]?.externalModuleId?._errors}</div>
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
                  <div class="border-bottom pb-4 mb-3">
                    <div class="row">
                      <div class="col-md-9">
                        <label for="" class="col-form-label">Name</label>
                        <select name="" id="" class="form-control {$errors?.requests?.[i]?.internalModuleId?.[j] ? 'is-invalid' : ''}" bind:value={$form.requests[i].internalModuleId[j]}>
                          <option value="">-</option>
                          {#each internalModulesByCourse as module}
                            <option value={module.moduleId}>{module.moduleName}</option>
                          {/each}
                        </select>
                        {#if $errors?.requests?.[i]?.internalModuleId?.[j]}
                          <div class="invalid-feedback">{$errors?.requests?.[i]?.internalModuleId?.[j]}</div>
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
                      class="btn btn-sm btn-outline-secondary w-100 mt-3"
                      type="button"
                      on:click={() => ($form.requests[i].internalModuleId = [...$form.requests[i].internalModuleId.slice(0, j), ...$form.requests[i].internalModuleId.slice(j + 1)])}
                    >
                      <i class="bi bi-trash" /> Löschen</button
                    >
                  </div>
                {/each}

                {#if $errors?.requests?.[i]?.internalModuleId?._errors}
                  <div class="invalid-feedback d-block mb-3">{$errors?.requests?.[i]?.internalModuleId?._errors}</div>
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
            <input
              type="text"
              class="form-control {$errors?.requests?.[i]?.moduleLink ? 'is-invalid' : ''}"
              placeholder="https://uni-leipzig.de/module_xyz"
              bind:value={$form.requests[i].moduleLink}
            />
            {#if $errors?.requests?.[i]?.moduleLink}
              <div class="invalid-feedback">{$errors?.requests?.[i]?.moduleLink}</div>
            {/if}
          </div>
          <div class="mb-3">
            <label for="" class="col-form-label">Modulbeschreibung</label>
            <input
              type="file"
              name="file"
              class="form-control {$errors?.requests?.[i]?.file ? 'is-invalid' : ''}"
              on:input={(e) => ($form.requests[i].file = e.currentTarget.files?.item(0))}
              accept="application/pdf"
            />
            {#if $errors?.requests?.[i]?.file}
              <div class="invalid-feedback">{$errors?.requests?.[i]?.file}</div>
            {/if}
          </div>
        </div>
      </Accordion>
    {/each}

    {#if $errors?.requests?._errors}
      <div class="invalid-feedback d-block mb-3">{$errors?.requests?._errors}</div>
    {/if}

    <div class="mb-3 border-bottom pb-3">
      <button class="btn btn-sm btn-outline-primary" type="button" on:click={() => ($form.requests = [...$form.requests, default_request])}><i class="bi bi-plus-circle" /> Neuen Antrag</button>
    </div>

    <div class="annotation mb-3">
      <label class="col-form-label" for=""> Anmerkungen für den Bearbeiter </label>

      <textarea rows="4" type="text" bind:value={$form.annotation} class="form-control {$errors?.annotation ? 'is-invalid' : ''}" />
      {#if $errors?.annotation}
        <div class="invalid-feedback">{$errors?.annotation}</div>
      {/if}
    </div>

    <!-- <SuperDebug data={$form} /> -->
    <hr />

    <button class="btn btn-primary" type="submit">Weiter</button>
  {:else}
    <div class="list-group mb-3">
      <div class="list-group-item d-inline-flex justify-content-between">
        <span class="fw-bold">Universität</span>
        {findUniById($form.universityId)?.uniName}
      </div>
      <div class="list-group-item d-inline-flex justify-content-between">
        <span class="fw-bold">Studiengang</span>
        {findCourseById($form.courseId)?.courseName}
      </div>
    </div>

    <div class="list-group mb-3">
      <div class="list-group-item d-inline-flex justify-content-between"><strong>Modulanträge ({$form.requests?.length})</strong></div>
      {#each $form.requests as _, i}
        <div class="list-group-item d-flex justify-content-between flex-wrap">
          <div class="hstack align-items-start flex-wrap flex-md-nowrap">
            <ul>
              {#each $form.requests[i].externalModuleId as _, z}
                <li>{findExternalModuleById($form.requests?.[i]?.externalModuleId[z])?.moduleName}</li>
              {/each}
            </ul>
            <ul>
              {#each $form.requests[i].internalModuleId as _, z}
                <li>{findInternalModuleById($form.requests?.[i]?.internalModuleId[z])?.moduleName}</li>
              {/each}
            </ul>
          </div>
          <div class="d-flex align-items-center gap-2">
            {#if $form.requests?.[i]?.moduleLink}
              <i class="icon-xl bi bi-link" />
            {/if}
            {#if $form.requests?.[i]?.file}
              <i class="icon-xl bi bi-file-earmark-pdf" />
            {/if}
          </div>
        </div>
      {/each}
    </div>

    <div class="annotation mb-3">
      <label class="col-form-label" for="">Anmerkungen für den Bearbeiter</label>

      <textarea rows="4" type="text" bind:value={$form.annotation} class="form-control {$errors?.annotation ? 'is-invalid' : ''}" />
      {#if $errors?.annotation}
        <div class="invalid-feedback">{$errors?.annotation}</div>
      {/if}
    </div>
    <hr />

    <button class="btn btn-primary" type="submit">Senden</button>

    <!-- <br />
    <SuperDebug data={$form} /> -->
  {/if}
</form>

<style>
  .reset-disable-look {
    background-color: var(--bs-body-bg);
  }

  .icon-xl {
    font-size: 1.5rem;
  }

  ul {
    margin: 1rem 0rem;
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

  .status-circle:focus {
    background-color: var(--bs-body-bg);
    color: var(--bs-primary);
    border: 3px solid var(--bs-primary);
  }

  .status-circle:hover {
    background-color: var(--bs-primary);
    color: var(--bs-body-bg);
    border: 3px solid var(--bs-primary);
  }

  .status-circle {
    width: 50px;
    height: 50px;
    background-color: var(--bs-body-bg);
    color: var(--bs-primary);
    border: 3px solid var(--bs-primary);
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.25rem; /* Schriftgröße der Zahl */
  }

  .status-circle.active {
    background-color: var(--bs-primary);
    color: var(--bs-body-bg);
  }

  .status-leiste {
    margin: 3rem 1.5rem 2rem 1.5rem;
  }

  @media screen and (max-width: 768px) {
    .status-circle {
      font-size: 1rem;
    }
  }
</style>
