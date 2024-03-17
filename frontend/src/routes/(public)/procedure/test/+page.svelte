<script>
  import { page } from '$app/stores';
  import { superForm } from 'sveltekit-superforms';
  import { add_external_module } from '$root/lib/schema';
  import { zod } from 'sveltekit-superforms/adapters';
  import * as config from '$lib/config';
  import * as api from '$lib/api';
  import * as flashModule from 'sveltekit-flash-message/client';

  import SuperDebug from 'sveltekit-superforms';
  import AddExternalModule from './AddExternalModule.svelte';

  export let data;

  const { form, messages, errors, enhance } = superForm(data.form, {
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
    validators: zod(add_external_module),
    validationMethod: 'auto',
    customValidity: false
  });

  $form.moduleNumber = '';
  $form.moduleDescription = '';
  $form.creditPoints = 5;

  $form.university.uniId = data.testUni.uniId;
  $form.university.uniName = data.testUni.uniName;
  $form.university.verified = data.testUni.verified;

  let showModal;
</script>

<AddExternalModule bind:this={showModal} {data} />

<!-- <SuperDebug data={form} /> -->

<button class="btn btn-primary btn-sm" on:click={() => showModal.dialog_open()}>
  <i class="bi bi-plus-circle" />
  Fremdmodul
</button>

<form action="?/addExternalModule" method="POST" use:enhance>
  <label for="" class="mt-2">Name des Moduls </label>
  <input type="text" class="form-control" placeholder="" bind:value={$form.moduleName} />

  <label for="" class="mt-2">Nummer des Moduls </label>
  <input type="text" class="form-control" placeholder="" bind:value={$form.moduleNumber} />

  <label for="" class="mt-2">Beschreibung des Moduls </label>
  <input type="text" class="form-control" placeholder="" bind:value={$form.moduleDescription} />

  <label for="" class="mt-2">Universität </label>
  <input disabled type="text" class="form-control" placeholder="" value={$form.university.uniName} />

  <label for="" class="mt-2">Credit Points </label>
  <input type="number" class="form-control" placeholder="" bind:value={$form.creditPoints} />

  <button type="submit" class="my-3 btn btn-primary">Speichern</button>
</form>
