<script>
  import { getProcedureStatus } from '$lib/config';
  export let status;
  export let extern = false;

  let error = false;

  if (getProcedureStatus(status) == null) {
    error = true;
  }
</script>

{#if error}
  <div class="ms-2 badge rounded-pill d-inline-flex align-items-center">
    Error: {status}
  </div>
{:else}
  <div class="ms-2 badge rounded-pill d-inline-flex align-items-center" style="background-color: {getProcedureStatus(status).color}; color: {getProcedureStatus(status).text_color};">
    {#if extern}
      {getProcedureStatus(status).text_extern}
    {:else}
      {getProcedureStatus(status).text_intern}
    {/if}
  </div>
{/if}
