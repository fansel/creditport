<script>
  import { format, parseISO } from 'date-fns';
  import Modal from '$lib/components/InfoModal.svelte';
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';

  export let showModal;
  export let similarRequests;

  let searchTerm = '';
  let searchResults;
  let searchResultsCount = 0;
</script>

<Modal bind:showModal>
  <h2 slot="headline" class="m-0">ähnliche Anträge</h2>

  <div slot="body">
    <div class="m-1">
      <ul class="list-group mb-3 cp-table border">
        <li class="cp-table-header border-bottom font-sm">
          <div class="row text-center fw-bold">
            <div class="col-3">erstellt am</div>
            <div class="col-3">AntragsId</div>
            <div class="col-3">Status</div>
            <div class="col-3">Link</div>
          </div>
        </li>

        <div class="cp-table-body font-sm">
          {#each similarRequests as request}
            <div class="row p-2 text-center scrollable">
              <div class="col-3">{format(new Date(request.createdAt), 'dd.MM.yyyy')}</div>
              <div class="col-3 text-center">{request.requestId}</div>
              <div class="col-3">
                <RequestStatus status={request.statusRequest} />
              </div>
              <div class="col-3"><a href="/procedures/{request.requestId}" target="_blank"><i class="bi bi-box-arrow-up-right"></i></a></div>
            </div>
          {/each}
        </div>
      </ul>
    </div>
  </div>
</Modal>

<style>
  .scrollable {
    max-height: 200px;
    overflow-y: auto;
  }
</style>
