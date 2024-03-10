<script>
  import { page } from '$app/stores';
</script>

<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="text-center">
      <h1 class="">{$page.status} - {$page.error.message}</h1>
      <p>Die von Ihnen gesuchte Seite existiert nicht oder wurde verschoben.</p>
      <a href="/" class="btn btn-primary">Zurück zum Startseite</a>
    </div>
  </div>
</div>
