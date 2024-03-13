<script>
  import { page } from '$app/stores';
  import Modal from '$lib/components/InfoModal.svelte';

  export let showModal;
  export let annotationStudent;
  export let annotationCommittee;

  let request = $page.data.request;
  let requestBackup = JSON.parse(JSON.stringify($page.data.request));

  console.log('comment request?', request);

  async function submitForm(event) {
    const body = request;
    delete body.relatedRequests;
    const res = await fetch('/update-request', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      body: JSON.stringify(body)
    });

    console.log(res);
    requestBackup = JSON.parse(JSON.stringify(request));
  }

  async function cancelChanges(event) {
    request = requestBackup;
    submitForm();
  }
</script>

<Modal bind:showModal>
  <div slot="headline"></div>
  <div slot="body">
    <div class="form-row m-2 mb-2">
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
          <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4" name="annotationStudent" bind:value={annotationStudent}></textarea>
        </div>
        <div class="tab-pane fade" id="office-tab-pane" role="tabpanel" aria-labelledby="office-tab" tabindex="0">
          <textarea class="form-control" id="input" placeholder="Begründen Sie Ihren Entscheid..." rows="4" name="annotationCommittee" bind:value={annotationCommittee}></textarea>
        </div>
      </div>
    </div>
  </div>
  <div slot="footer" class="mb-2 mx-2">
    <button type="submit" class="btn btn-primary" on:click={submitForm}>Speichern</button>
    <div class="btn btn-outline-secondary" on:click={cancelChanges}>Abbrechen</div>
  </div>
</Modal>

<style>
  textarea#input {
    border-top: none;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
  textarea#input:focus {
    outline: none !important;
    border-color: #ced4da !important;
    box-shadow: none !important;
  }
</style>
