<script>
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';
  import * as config from '$lib/config';
  import { add_course_schema, update_course_schema, update_external_module, user_schema } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';

  export let data;
  // export let showModal;

  let dialog;

  // $: if (dialog && showModal) dialog.showModal();

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
    validators: zod(update_external_module),
    validationMethod: 'auto',
    customValidity: false,
    onResult({ result }) {
      if (result.status == 200) dialog_close();
    }
  });

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
    <h2 class="m-0">Externes Modul bearbeiten</h2>
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog_close()} />
  </div>

  <!-- <SuperDebug data={$form} /> -->

  <form action="?/updateExternalModule" method="POST" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <div class="row mb-3">
        <input type="hidden" bind:value={$form.moduleId} name="moduleId" />
        <div class="col-md-3">
          <label for="name" class="col-form-label">Name</label>
        </div>
        <div class="col">
          <input type="text" name="moduleName" placeholder="" class="form-control {$errors.moduleName ? 'is-invalid' : ''}" bind:value={$form.moduleName} />
          {#if $errors.moduleName}
            <div class="invalid-feedback">{$errors.moduleName}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3">
          <label for="name" class="col-form-label">Nummer</label>
        </div>
        <div class="col">
          <input type="text" name="moduleNumber" placeholder="" class="form-control {$errors.moduleNumber ? 'is-invalid' : ''}" bind:value={$form.moduleNumber} />
          {#if $errors.moduleNumber}
            <div class="invalid-feedback">{$errors.moduleNumber}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3">
          <label for="name" class="col-form-label">Modulbeschreibung</label>
        </div>
        <div class="col">
          <textarea type="text" name="moduleDescription" placeholder="" class="form-control {$errors.moduleDescription ? 'is-invalid' : ''}" bind:value={$form.moduleDescription} rows="6" />
          {#if $errors.moduleDescription}
            <div class="invalid-feedback">{$errors.moduleDescription}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3">
          <label for="name" class="col-form-label">Leistungspunkte</label>
        </div>
        <div class="col">
          <input type="number" name="creditPoints" placeholder="" class="form-control {$errors.creditPoints ? 'is-invalid' : ''}" bind:value={$form.creditPoints} />
          {#if $errors.creditPoints}
            <div class="invalid-feedback">{$errors.creditPoints}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3">
          <label for="" class="col-form-label">Verifiziert</label>
        </div>
        <div class="col">
          <input type="checkbox" name="verified" class="form-check-input {$errors.verified ? 'is-invalid' : ''}" bind:checked={$form.verified} />
          {#if $errors.verified}
            <div class="invalid-feedback">{$errors.verified}</div>
          {/if}
        </div>
      </div>
    </div>

    <input type="hidden" bind:value={$form.university.uniId} />
    <input type="hidden" bind:value={$form.university.uniName} />
    <input type="hidden" bind:value={$form.university.verified} />

    <!-- Footer -->
    <div class="p-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={dialog_close} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Speichern</button>
    </div>
  </form>
</dialog>
