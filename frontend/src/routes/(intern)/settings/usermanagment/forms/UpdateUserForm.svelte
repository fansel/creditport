<script>
  import Modal from '$lib/components/FormModal.svelte';
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';

  let dialog;

  let showUpdateModal = getContext('showUpdateModal');

  export let data;
  const { form, errors, enhance } = superForm(data);
</script>

<Modal bind:showUpdateModal bind:this={dialog} action="?/changeUser">
  <div class="" slot="h1" />

  <form action="">

  </form>
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
</Modal>

<style>
</style>
