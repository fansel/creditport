<script>
  import { page } from '$app/stores';
</script>

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="text-center">
      <h1 class="">{$page.status} - {$page.error.message}</h1>
      <p>Leider ist es zu einem unerwarteten Fehler gekommen. Bitte versuchen Sie es später erneut...</p>
      {#if $page.error.errorId}
        <div class="mb-3">Error ID: {$page.error.errorId}</div>
      {/if}
      <a href="/" class="btn btn-primary">Zurück zur Startseite</a>
    </div>
  </div>
</div>
