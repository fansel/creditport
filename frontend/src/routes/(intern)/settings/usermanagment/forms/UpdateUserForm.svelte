<script>
  import SuperformModal from '$lib/components/SuperformModal.svelte';
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';
  import * as config from '$lib/config';
  import { user_schema } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';

  export let data;
  // export let showModal;

  let dialog;

  // $: if (dialog && showModal) dialog.showModal();

  const { form, errors, enhance, reset } = superForm(data, {
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    clearOnSubmit: 'none',
    resetForm: false,
    validators: zod(user_schema),
    validationMethod: 'auto',
    customValidity: false,
    onResult({ result }) {
      if (result.status == 200) dialog_close();
    },
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
  <div class="headline header border-bottom">
    <h2 class="m-0">Benutzer bearbeiten</h2>
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog_close()} />
  </div>

  <!-- <SuperDebug data={$form} /> -->

  <form action="?/updateUser" method="POST" use:enhance>
    <!-- Body -->
    <div class="body p-3">
      <input type="hidden" bind:value={$form.userId} name="userId" />
      <div class="row mb-3">
        <div class="col-3">
          <label for="name" class="col-form-label">Nutzername</label>
        </div>
        <div class="col">
          <input type="text" name="username" placeholder="" class="form-control {$errors.username ? 'is-invalid' : ''}" bind:value={$form.username} />
          {#if $errors.username}
            <div class="invalid-feedback">{$errors.username}</div>
          {/if}
        </div>
      </div>

      <div class="row mb-3">
        <div class="col-3">
          <label for="roleSelectUpdate" class="col-form-label">Rolle</label>
        </div>
        <div class="col">
          <select name="role" id="roleSelectUpdate" class="form-select {$errors.role ? 'is-invalid' : ''}" bind:value={$form.role}>
            {#each Object.entries(config.user_roles) as [key, value] (key)}
              <option {value}>{value}</option>
            {/each}
          </select>
          {#if $errors.role}
            <div class="invalid-feedback">{$errors.role}</div>
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

<!-- <SuperformModal bind:showModal bind:this={dialog} action="?/changeUser" data={data.regForm} invalidateAll={false} let:form let:message> -->
<!-- <div slot="headline">Update Benutzer</div>

  <div slot="body" /> -->

<!-- <h2 slot="headline" class="m-0">Benutzer hinzufügen</h2>
  <form action="?/updateUser">
    <div slot="body" class="p-3">
      <div class="row mb-3">
        <div class="col-3">
          <label for="name" class="col-form-label">Name</label>
        </div>
        <div class="col">
          <input type="text" name="name" placeholder="" class="form-control {$page.form?.errors?.name ? 'is-invalid' : ''}" value={$page.form?.data?.name ?? ''} />
          {#if $page.form?.errors?.name}
            <div class="invalid-feedback">{$page.form?.errors?.name}</div>
          {/if}
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-3">
          <label for="password" class="col-form-label">Passwort</label>
        </div>
        <div class="col">
          <input type="password" name="password" class="form-control {$page.form?.errors?.password ? 'is-invalid' : ''}" value={$page.form?.data?.password ?? ''} placeholder="*********" />
          {#if $page.form?.errors?.password}
            <div class="invalid-feedback">{$page.form?.errors?.password}</div>
          {/if}
        </div>
      </div>
      <div class="row mb-3">
        <div class="col-3">
          <label for="role" class="col-form-label">Rolle</label>
        </div>
        <div class="col">
          <select name="role" id="roleSelect" class="form-select">
            {#each Object.entries(roles) as [key, value] (key)}
              <option {value}>{value}</option>
            {/each}
          </select>
          {#if $page.form?.errors?.role}
            <div class="invalid-feedback">{$page.form?.errors?.role}</div>
          {/if}
        </div>
      </div>
    </div>
    <div slot="footer" class="p-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Erstellen</button>
    </div>
  </form> -->

<style>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  dialog {
    border: none;
    min-width: 50rem;
    border-radius: 10px;
    position: relative;
    padding: 0;
  }

  @media screen and (max-width: 1200px) {
    dialog {
      min-width: calc(100vw - 3rem) !important ;
    }
  }

  dialog::backdrop {
    background: rgba(0, 0, 0, 0.3);
  }
  dialog > div {
    padding: 1em;
  }
  dialog[open] {
    animation: zoom 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
  }
  @keyframes zoom {
    from {
      transform: scale(0.95);
    }
    to {
      transform: scale(1);
    }
  }
  dialog[open]::backdrop {
    animation: fade 0.2s ease-out;
  }
  @keyframes fade {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
  button {
    display: block;
  }
</style>
