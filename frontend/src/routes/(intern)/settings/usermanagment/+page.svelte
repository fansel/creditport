<script>
  import * as config from '$lib/config';
  import { superValidate } from 'sveltekit-superforms';
  import AddUserForm from './forms/AddUserForm.svelte';
  import UpdateUserForm from './forms/UpdateUserForm.svelte';
  import { enhance } from '$app/forms';

  export let data;

  $: users = data.users;

  let showAddModal = false;

  let updateForm;

  function dialog_open(id) {
    const user = users.find((u) => u.userId == id);
    if (!user) {
      console.error('User not found');
    }
    updateForm.dialog_open(user);
  }
</script>

<AddUserForm bind:showModal={showAddModal} roles={config.user_roles} />
<UpdateUserForm bind:this={updateForm} data={data.updateUserForm} />

<h4 class="mb-3 d-flex flex-wrap gap-3">
  Benutzer
  <button class="btn btn-primary btn-sm text-nowrap" on:click={() => (showAddModal = true)}>
    <i class="bi bi-plus-circle" />
    Benutzer hinzufügen
  </button>
</h4>

<ul class="list-group mb-4">
  <li class="list-group-item font-sm">
    <div class="row">
      <div class="col">Nutzername</div>
      <div class="col">Rolle</div>
      <div class="col">Aktionen</div>
    </div>
  </li>

  {#each users as user, index}
    <li class="list-group-item">
      <div class="row">
        <div class="col d-flex align-items-center">
          <button class="btn btn-link p-0">
            {user.username}
          </button>
        </div>
        <div class="col d-flex align-items-center"><span class="badge bg-secondary-subtle border border-secondary-subtle text-secondary-emphasis rounded-pill">{user.role}</span></div>
        <div class="col d-flex align-items-center">
          <div class="btn-group">
            <button class="btn btn-sm btn-outline-primary" on:click={() => dialog_open(user.userId)}>Bearbeiten</button>
            <form action="?/deleteUser" method="POST" use:enhance>
              <input type="hidden" bind:value={user.userId} name="userId" />
              <button class="btn btn-sm btn-outline-danger left-no-radius">Löschen</button>
            </form>
          </div>
        </div>
      </div>
    </li>
  {/each}
</ul>

<h4 class="mb-3">Rollen</h4>
<p>Die Nutzerrollen sind unveränderlich</p>

<ul class="list-group mb-4 font-sm">
  <li class="list-group-item">
    <div class="row">
      <div class="col-4">Berechtigungen</div>
      {#each Object.entries(config.user_roles) as [key, value] (key)}
        <div class="col"><span class="badge bg-secondary-subtle border border-secondary-subtle text-secondary-emphasis rounded-pill">{value}</span></div>
      {/each}
    </div>
  </li>

  <li class="list-group-item">
    <div class="row">
      <div class="col-4 d-flex align-items-center">Anträge ansehen</div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" checked disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
    </div>
  </li>

  <li class="list-group-item">
    <div class="row">
      <div class="col-4 d-flex align-items-center">Anträge bearbeiten</div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" checked disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
    </div>
  </li>

  <li class="list-group-item">
    <div class="row">
      <div class="col-4 d-flex align-items-center">Anträge weiterleiten</div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" checked disabled /></div>
    </div>
  </li>

  <li class="list-group-item">
    <div class="row">
      <div class="col-4 d-flex align-items-center">Anträge löschen</div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" checked disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
      <div class="col d-flex align-items-center"><input type="checkbox" name="" id="" class="form-check-input" disabled /></div>
    </div>
  </li>
</ul>

<!-- <button class="btn btn-primary mt-2 mb-2">Speichern</button> -->

<style>
  .btn-link {
    text-decoration: none;
  }

  .font-sm {
    font-size: 0.875rem;
  }

  .left-no-radius {
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    border-left: 0;
  }
</style>
