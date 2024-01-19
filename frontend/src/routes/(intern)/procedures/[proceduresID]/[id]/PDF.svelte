<script>
  import { page } from '$app/stores';
  import * as config from '$lib/config';

  export let request = $page.data.request;
</script>

<div class="pdf-container">
  <div class="pdf-content">
    {#if request.pdfExists}
      <iframe src="{config.pdf_endpoint + request.requestId}#zoom=FitH" class="pdf-iframe" title="pdf" />
    {:else}
      <iframe src="https://www.orimi.com/pdf-test.pdf#zoom=FitH" class="pdf-iframe" title="pdf" />
    {/if}
  </div>
</div>

<style>
  /* pdf */
  .pdf-iframe {
    width: 100%;
    height: calc(100vh - 250px);
  }
</style>
