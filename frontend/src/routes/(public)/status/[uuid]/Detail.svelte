<script>
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';

  export let request;

  let green = '#28a745';
  let yellow = '#ffc107';
  let red = '#dc3545';
</script>

<div class="card">
  <div class="card-header d-flex justify-content-between">
    <div class="FM-M">{request.externalModuleId} → {request.internalModuleId}</div>
    <div class="CP">LP: {request.creditPoints}</div>
  </div>
  <div class="card-body d-flex justify-content-between">
    {request.annotationStudent}
    <RequestStatus status={request.statusRequest} />
  </div>
</div>
