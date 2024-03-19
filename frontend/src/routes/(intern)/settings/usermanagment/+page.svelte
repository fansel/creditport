<script>
  import * as config from '$lib/config';
  import { superValidate } from 'sveltekit-superforms';
  import AddUserForm from './forms/AddUserForm.svelte';
  import UpdateUserForm from './forms/UpdateUserForm.svelte';
  import DeleteUserForm from './forms/DeleteUserForm.svelte';
  import { enhance } from '$app/forms';

  export let data;

  $: users = data.users;

  // let showAddModal = false;
  let showDeleteModal = false;

  let selectedUser;

  let updateForm;
  let addForm;

  function dialog_open(id) {
    const user = users.find((u) => u.userId == id);
    if (!user) {
      console.error('User not found');
    }
    updateForm.dialog_open(user);
  }
</script>

<!-- <AddUserForm bind:showModal={showAddModal} roles={config.user_roles} /> -->
<UpdateUserForm bind:this={updateForm} data={data.updateUserForm} />
<AddUserForm bind:this={addForm} data={data.addUserForm} />
<DeleteUserForm user={selectedUser} bind:showModal={showDeleteModal} />

<h4 class="mb-3 d-flex justify-content-between flex-wrap gap-2">
  Benutzer
  <button class="btn btn-primary btn-sm text-nowrap" on:click={addForm.dialog_open()}>
    <i class="bi bi-plus-circle" />
    Hinzufügen
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
            <button class="btn btn-sm btn-outline-danger" on:click={() => ((showDeleteModal = true), (selectedUser = user))}>Löschen</button>
          </div>
        </div>
      </div>
    </li>
  {/each}
</ul>

<!-- <button class="btn btn-primary mt-2 mb-2">Speichern</button> -->

<style>
  .btn-link {
    text-decoration: none;
  }

   .break-word {
    word-break: break-all;
  } 
</style>
