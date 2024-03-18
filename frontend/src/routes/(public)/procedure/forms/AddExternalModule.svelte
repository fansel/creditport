<script>
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';
  import * as config from '$lib/config';
  import { add_external_module } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';

  export let data;

  let dialog;

  const { form, messages, errors, enhance, reset } = superForm(data.externalModuleForm, {
    dataType: 'json',
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    validators: zod(add_external_module),
    validationMethod: 'auto',
    customValidity: false,
    onResult({ result }) {
      if (result.status == 200) dialog_close();
    }
  });

  export function dialog_open(uni_id) {
    const data = {
      university: {
        uniId: uni_id
      }
    };
    reset({ data });
    dialog.showModal();
  }

  export function dialog_close() {
    dialog.close();
  }

  // $form.moduleNumber = '';
  // $form.moduleDescription = '';
  // $form.creditPoints = 5;

  // $form.university.uniId = data.testUni.uniId;
  // $form.university.uniName = data.testUni.uniName;
  // $form.university.verified = data.testUni.verified;

  // console.log('moduleForm: ', $form);
</script>

<dialog bind:this={dialog}>
  <div class="headline dialog-header border-bottom">
    <h2 class="m-0">Fremdmodul hinzufügen</h2>
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog_close()} />
  </div>

  <SuperDebug data={$form} />

  <form action="?/addExternalModule" method="POST" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <label for="" class="mt-2">Name des Moduls </label>
      <input type="text" class="form-control {$errors.moduleName ? 'is-invalid' : ''}" placeholder="" bind:value={$form.moduleName} />
      {#if $errors.moduleName}
        <div class="invalid-feedback">{$errors.moduleName}</div>
      {/if}

      <label for="" class="mt-2">Nummer des Moduls </label>
      <input type="text" class="form-control" placeholder="" bind:value={$form.moduleNumber} />
      {#if $errors.moduleNumber}
        <div class="invalid-feedback">{$errors.moduleNumber}</div>
      {/if}
      
      <label for="" class="mt-2">Beschreibung des Moduls </label>
      <input type="text" class="form-control" placeholder="" bind:value={$form.moduleDescription} />

      <label for="" class="mt-2">Credit Points </label>
      <input type="number" class="form-control" placeholder="" bind:value={$form.creditPoints} />
    </div>

    <!-- Footer -->
    <div class="p-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={dialog_close} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Speichern</button>
    </div>
  </form>
</dialog>
