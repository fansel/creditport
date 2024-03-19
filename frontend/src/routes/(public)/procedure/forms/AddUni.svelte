<script>
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';
  import * as config from '$lib/config';
  import { add_university } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';
  import { setContext } from 'svelte';

  export let data;
  export let selectedUni;
  export let selectedUniPush;

  let dialog;

  const { form, messages, errors, enhance, reset } = superForm(data.uniForm, {
    dataType: 'json',
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    invalidateAll: false,
    resetForm: true,
    onError({ result }) {
      console.error(result.error.message);
    },
    validators: zod(add_university),
    validationMethod: 'auto',
    customValidity: false,
    onResult({ result }) {
      if (result.status == 200) {
        selectedUni = result.data.form.message.selectedUni;
        selectedUniPush = true;
        dialog_close();
      }
    }
  });

  export function dialog_open() {
    reset({});
    dialog.showModal();
  }

  export function dialog_close() {
    dialog.close();
  }

  // console.log('uniForm: ', $form);

  // $form.uniName = '';
</script>

<dialog bind:this={dialog}>
  <div class="headline dialog-header border-bottom">
    <h2 class="m-0">Universität hinzufügen</h2>
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog_close()} />
  </div>

  <!-- <SuperDebug data={$form} /> -->

  <form action="?/addUni" method="POST" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <div class="row">
        <div class="col-md-3">
          <label for="" class="mt-2">Name</label>
        </div>
        <div class="col">
          <input type="text" class="form-control {$errors.uniName ? 'is-invalid' : ''}" placeholder="Neue Universität" bind:value={$form.uniName} />
          {#if $errors.uniName}
            <div class="invalid-feedback">{$errors.uniName}</div>
          {/if}
        </div>
      </div>
    </div>

    <!-- Footer -->
    <div class="p-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={dialog_close} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Speichern</button>
    </div>
  </form>
</dialog>
