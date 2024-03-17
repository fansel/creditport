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
  import AddUni from './AddUni.svelte';

  export let data;
  let showAddFremdmodul;
  let showAddUni;
</script>

<!-- MODALS -->
<AddExternalModule bind:this={showAddFremdmodul} {data} />
<AddUni bind:this={showAddUni} {data} />

<ul class="d-flex nav nav-pills mb-3 my-2" id="pills-tab" role="tablist">
  <li class="nav-item d-flex justify-content-between" role="presentation">
    <button
      class="nav-link active rounded-circle border-3 border border-primary status-circle"
      id="pills-home-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-home"
      type="button"
      role="tab"
      aria-controls="pills-1"
      aria-selected="true">1</button
    >
  </li>
  <li class="nav-item d-flex justify-content-between" role="presentation">
    <button
      class="nav-link rounded-circle border-3 border border-primary status-circle"
      id="pills-profile-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-profile"
      type="button"
      role="tab"
      aria-controls="pills-2"
      aria-selected="false">2</button
    >
  </li>
  <li class="nav-item d-flex justify-content-between" role="presentation">
    <button
      class="nav-link rounded-circle border-3 border border-primary status-circle"
      id="pills-contact-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-contact"
      type="button"
      role="tab"
      aria-controls="pills-3"
      aria-selected="false">3</button
    >
  </li>
</ul>
<hr />
<div class="tab-content my-2" id="pills-tabContent">
  <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
    <div class="uni">
      <label class="mb-2" for="">Universität der anzurechnenden Module</label>
      <select class="form-select" value="0">
        {#each data.universities as university, index}
          <option>{university.uniName}</option>
        {/each}
      </select>

      <div class="py-2">Universität nicht gefunden <i class="bi bi-question-circle"></i></div>

      <button class="btn btn-primary btn-sm" on:click={() => showAddUni.dialog_open()}>
        <i class="bi bi-plus-circle" />
        Universität
      </button>
    </div>

    <div class="studiengang mt-3">
      <label class="mb-2" for="">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
      <select class="form-select" value="0">
        {#each data.courses as course, index}
          <option>{course.courseName}</option>
        {/each}
      </select>
    </div>
  </div>

  <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">2</div>
  <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">3</div>
</div>

<!-- <button class="btn btn-primary btn-sm" on:click={() => showAddFremdmodul.dialog_open()}>
  <i class="bi bi-plus-circle" />
  Fremdmodul
</button> -->
