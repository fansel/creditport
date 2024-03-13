<script>
  import Modal from '$lib/components/FormModal.svelte';
  import { page } from '$app/stores';
  import { onMount } from 'svelte';
  import * as config from '$lib/config';
  import { truncateText } from '$lib/util';
  import { format, parseISO } from 'date-fns';
  import RequestStatus from '$lib/components/RequestStatus.svelte';
  import { procedure_schema } from '$root/lib/schema';
  import { superForm, dateProxy } from 'sveltekit-superforms';
  import { add_course_schema, update_course_schema, update_internal_modul_schema, user_schema } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';

  export let data;

  let dialog;

  const { form, errors, enhance, reset } = superForm(data, {
    dataType: 'json',
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    clearOnSubmit: 'none',
    resetForm: false,
    validators: zod(procedure_schema),
    validationMethod: 'auto',
    customValidity: false,
    onResult({ result }) {
      if (result.status == 200) dialog_close();
    }
  });

  $: console.log($errors);

  export function dialog_open(data) {
    reset({ data });
    dialog.showModal();
  }

  export function dialog_close() {
    dialog.close();
  }
</script>

<!-- svelte-ignore a11y-click-events-have-key-events a11y-no-noninteractive-element-interactions -->
<dialog bind:this={dialog}>
  <div class="dialog-header border-bottom">
    <h2 class="m-0">Vorgang bearbeiten</h2>
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog_close()} />
  </div>

  <!-- <SuperDebug data={$form} /> -->

  <form action="?/updateProcedure" method="POST" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <div class="row">
        <div class="col-12 col-lg-6">
          <div class="row mb-3">
            <div class="col-md-3">
              <label for="id" class="col-form-label">Vorgangsnummer</label>
            </div>
            <div class="col">
              <input type="text" bind:value={$form.procedureId} disabled name="procedureId" class="form-control {$errors.procedureId ? 'is-invalid' : ''}" />
              {#if $errors.procedureId}
                <div class="invalid-feedback">
                  {$errors.procedureId}
                </div>
              {/if}
            </div>
          </div>
          <div class=" row mb-3">
            <div class="col-md-3">
              <label for="university" class="col-form-label">Universität</label>
            </div>
            <div class="col">
              <input type="text" bind:value={$form.university.uniName} disabled name="university.uniName" class="form-control {$errors.university?.uniName ? 'is-invalid' : ''}" />
              {#if $errors.university?.uniName}
                <div class="invalid-feedback">
                  {$errors.university?.uniName}
                </div>
              {/if}
              <input type="hidden" bind:value={$form.university.uniId} name="university.uniId">
            </div>
          </div>
          <div class="row mb-3">
            <div class="col-md-3">
              <label for="courseName" class="col-form-label"> Studiengang </label>
            </div>
            <div class="col">
              <input type="text" bind:value={$form.course.courseName} disabled name="course.courseName" class="form-control {$errors.course?.courseName ? 'is-invalid' : ''}" />
              {#if $errors.course?.courseName}
                <div class="invalid-feedback">
                  {$errors.course?.courseName}
                </div>
              {/if}
              <input type="hidden" bind:value={$form.course.courseId} name="course.courseId">

            </div>
          </div>
          <div class="mb-3">
            <label for="annotation" class="col-form-label"> Kommentare </label>

            <textarea type="text" bind:value={$form.annotation} name="annotation" class="form-control {$errors.annotation ? 'is-invalid' : ''}" placeholder="" style="min-height: 120px;" />
            {#if $errors.annotation}
              <div class="invalid-feedback">
                {$errors.annotation}
              </div>
            {/if}
          </div>
        </div>
        <div class="col-12 col-lg-6">
          <div class="list-group">
            <div class="list-group-item d-inline-flex justify-content-between"><strong>Modulanträge ({$form.requestDetails.length})</strong></div>

            {#each $form.requestDetails as _, i}
            <input type="hidden" bind:value={$form.requestDetails[i].moduleLink} name="{$form.requestDetails[i].moduleLink}">
              <div class="list-group-item d-inline-flex justify-content-between">
                <div class="hstack align-items-start">
                  <ul>
                    {#each $form.requestDetails[i].externalModules as _, z}
                      <li>{$form.requestDetails[i].externalModules[z].moduleName}</li>
                    {/each}
                  </ul>
                  <ul>
                    {#each $form.requestDetails[i].internalModules as _, z}
                      <li>{$form.requestDetails[i].internalModules[z].moduleName}</li>
                    {/each}
                  </ul>
                </div>

                <div class="d-flex align-items-start gap-2">
                  <RequestStatus status={$form.requestDetails[i].statusRequest} />

                  <a href="/procedures/{$form.requestDetails[i].requestId}"><i class="bi bi-pencil-square" /></a>
                </div>
              </div>
            {/each}
          </div>
        </div>
      </div>
    </div>

    <!-- Footer -->
    <div class="p-3 d-flex justify-content-between border-top align-items-center flex-wrap gap-2">
      <div>
        {#if $form.createdAt}
          <div class="form-text m-0" id="basic-addon4">Erstellt am {format(parseISO($form.createdAt), 'dd.MM.yyyy HH:mm')}</div>
        {/if}
      </div>
      <div class="vstack flex-md-row justify-content-md-end gap-2">
        <button class="btn btn-outline-primary">Weiterleiten <i class="bi bi-arrow-right" /> </button>
        <button class="btn btn-outline-secondary" on:click={dialog_close} type="button">Abbrechen</button>
        <button class="btn btn-primary" type="submit">Speichern</button>
      </div>
    </div>
  </form>
</dialog>

<style>
  label {
    font-size: 0.9rem;
  }
</style>
