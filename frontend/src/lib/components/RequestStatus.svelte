<script>
  import { getProcedureStatus, getRequestStatus } from '$lib/config';
  export let status;
  export let extern = false;

  let error = false;

  if (getRequestStatus(status) == null) {
    error = true;
  }
</script>

{#if error}
  <div class="text-warning">
    Error: {status}
  </div>
{:else}
  <div class="ms-2 badge rounded-pill d-inline-flex align-items-center" style="background-color: {getRequestStatus(status).color}; color: {getRequestStatus(status).text_color};">
    {#if extern}
      {getRequestStatus(status).text_extern}
    {:else}
      {getRequestStatus(status).text_intern}
    {/if}
  </div>
{/if}
