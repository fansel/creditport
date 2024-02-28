<script>
  import { page } from '$app/stores';
  import { createEventDispatcher } from 'svelte';

  export let request = $page.data.request;
</script>

<div class="form-row mb-2">
  <ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
      <button class="nav-link active" id="studi-tab" data-bs-toggle="tab" data-bs-target="#studi-tab-pane" type="button" role="tab" aria-controls="studi-tab-pane" aria-selected="true"
        >Studierende</button
      >
    </li>
    <li class="nav-item" role="presentation">
      <button class="nav-link" id="office-tab" data-bs-toggle="tab" data-bs-target="#office-tab-pane" type="button" role="tab" aria-controls="office-tab-pane" aria-selected="false"
        >Prüfungsauschuss</button
      >
    </li>
  </ul>

  <div class="tab-content" id="myTabContent">
    <div class="tab-pane fade show active" id="studi-tab-pane" role="tabpanel" aria-labelledby="studi-tab" tabindex="0">
      <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4" name="annotationStudent" bind:value={request.annotationStudent}></textarea>
    </div>
    <div class="tab-pane fade" id="office-tab-pane" role="tabpanel" aria-labelledby="office-tab" tabindex="0">
      <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4" name="annotationCommittee" bind:value={request.annotationCommittee}></textarea>
    </div>
  </div>
</div>

<style>
  textarea#input {
    border-top: none;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
</style>
