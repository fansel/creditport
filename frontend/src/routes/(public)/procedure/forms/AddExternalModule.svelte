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
  export let selectedModules;
  export let selectedModulesPush;

  let dialog;
  let requestId;
  let j;

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
    invalidateAll: false,
    resetForm: true,
    onResult({ result }) {
      if (result.status == 200) {
        selectedModules.get(requestId).set(j, result.data.form.message.selectedModule)
        selectedModulesPush = true;
        dialog_close();
      }
    }
  });

  export function dialog_open(uni_id, id, newJ) {
    j = newJ;
    requestId = id;
    console.log(j)
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

  <!-- <SuperDebug data={$form} /> -->

  <form action="?/addExternalModule" method="POST" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <div class="row mb-3">
        <div class="col-md-3"><label for="" class="mt-2" />Name</div>
        <div class="col">
          <input type="text" class="form-control {$errors.moduleName ? 'is-invalid' : ''}" placeholder="Name des Moduls" bind:value={$form.moduleName} />
          {#if $errors.moduleName}
            <div class="invalid-feedback">{$errors.moduleName}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3"><label for="" class="mt-2">Modulnummer</label></div>
        <div class="col">
          <input type="text" class="form-control {$errors.moduleNumber ? 'is-invalid' : ''}" placeholder="Nummer des Moduls" bind:value={$form.moduleNumber} />
          {#if $errors.moduleNumber}
            <div class="invalid-feedback">{$errors.moduleNumber}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3">
          <label for="" class="mt-2">Modulbeschreibung</label>
        </div>
        <div class="col">
          <textarea type="text" rows="4" class="form-control {$errors.moduleDescription ? 'is-invalid' : ''}" placeholder="Beschreibung des Moduls " bind:value={$form.moduleDescription} />
          {#if $errors.moduleDescription}
            <div class="invalid-feedback">{$errors.moduleDescription}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-md-3"><label for="" class="mt-2">Leistungspunkte</label></div>
        <div class="col">
          <input type="number" class="form-control {$errors.creditPoints ? 'is-invalid' : ''}" placeholder="" bind:value={$form.creditPoints} />
          {#if $errors.creditPoints}
            <div class="invalid-feedback">{$errors.creditPoints}</div>
          {/if}
        </div>
      </div>
    </div>

    <!-- Footer -->
    <div class="p-3 mb-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={dialog_close} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Speichern</button>
    </div>
  </form>
</dialog>
