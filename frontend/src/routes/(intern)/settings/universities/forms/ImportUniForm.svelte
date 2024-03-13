<script>
  import SuperformModal from '$lib/components/SuperformModal.svelte';
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';
  import * as config from '$lib/config';
  import { universities_upload_schema } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';
  import { stringProxy } from 'sveltekit-superforms';

  export let data;

  let dialog;

  const { form, errors, enhance, reset } = superForm(data, {
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    clearOnSubmit: 'none',
    resetForm: true,
    validators: zod(universities_upload_schema),
    validationMethod: 'auto',
    customValidity: false,
    onResult({ result }) {
      if (result.status == 200) dialog_close();
    }
  });

  export function dialog_open() {
    dialog.showModal();
  }

  export function dialog_close() {
    dialog.close();
  }

</script>

<!-- svelte-ignore a11y-click-events-have-key-events a11y-no-noninteractive-element-interactions -->
<dialog bind:this={dialog}>
  <div class="headline dialog-header border-bottom">
    <h2 class="m-0">Universitäten importieren</h2>
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog_close()} />
  </div>

  <form action="?/importUni" method="POST" enctype="multipart/form-data" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <div class="row mb-3">
        <div class="col-12 mb-2">
         <p class="text-secondary">Bitte lade eine JSON-Datei im richtigen Format hoch. Lade <a href="/examples/example_import_uni.json" target="_blank">hier</a> eine Beispiel Datei herunter.</p>
        </div>
        <div class="col-12">
          <input type="file" name="file" placeholder="" class="form-control" on:input={(e) => ($form.file = e.currentTarget.files?.item(0))} accept="application/json" />
          {#if $errors.file}
            <div class="invalid-feedback d-block">{$errors.file}</div>
          {/if}
        </div>
      </div>

     </div>

    <!-- Footer -->
    <div class="p-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={dialog_close} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Importieren</button>
    </div>
  </form>
</dialog>