<script>
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';

  export let request;

  let green = '#28a745';
  let yellow = '#ffc107';
  let red = '#dc3545';
</script>

<div class="card">
  <div class="card-header d-flex justify-content-between">
    <!-- <div class="FM-M">{request.externalModules[0].moduleName} <i class="bi bi-arrow-right"></i> {request.internalModules[0].moduleName}</div> -->
    <div class="CP">LP: {request.creditPoints}</div>
  </div>
  <div class="card-body d-flex justify-content-between">
    <div class="comment">
      {request.annotationStudent}
      <i class="bi bi-chat-fill"></i>
    </div>

    <RequestStatus status={request.statusRequest} />
  </div>
</div>
