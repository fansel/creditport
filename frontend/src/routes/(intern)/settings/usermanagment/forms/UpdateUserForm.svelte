<script>
  import SuperformModal from '$lib/components/SuperformModal.svelte';
  import { page } from '$app/stores';
  import { getContext } from 'svelte';
  import { superForm } from 'sveltekit-superforms';

  let dialog;

  let showUpdateModal = getContext('showUpdateModal');

  $: console.log($showUpdateModal)

  export let data;
</script>

<SuperformModal bind:showModal={$showUpdateModal} bind:this={dialog} action="?/changeUser" data={data.regForm} invalidateAll={false} let:form let:message>
  <div slot="headline" >
    Update Benutzer
  </div>

  <div slot="body">

  </div>

    <div slot="footer" class="p-3 d-flex justify-content-end align-items-center border-top">
      <button class="btn btn-outline-secondary me-3" on:click={() => dialog.closeDialog()} type="button">Abbrechen</button>
      <button class="btn btn-primary" type="submit">Erstellen</button>
    </div>

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
</SuperformModal>

<style>
</style>
