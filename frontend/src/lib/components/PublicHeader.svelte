<script>
  import { page } from '$app/stores';
</script>

<nav class="navbar navbar-expand-md navbar-light bg-light shadow-sm p-3" aria-label="Fourth navbar example">
  <div class="container">
    <a class="navbar-brand" href="/"><i class="bi bi-send" /> </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon" />
    </button>

    <div class="collapse navbar-collapse" id="navbarsExample04">
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <li class="nav-item"><a href="/" class="nav-link px-2 {$page.data.title == 'Home' ? 'text-dark' : 'text-secondary'}">Home</a></li>

        <li class="nav-item"><a href="/faq" class="nav-link px-2 {$page.data.title == 'Faq' ? 'text-dark' : 'text-secondary'}">FAQs</a></li>
        <li class="nav-item"><a href="/impressum" class="nav-link px-2 {$page.data.title == 'Impressum' ? 'text-dark' : 'text-secondary'}">Impressum</a></li>
      </ul>
      <a href="/login" type="button" class="btn btn-outline-secondary me-2">Login</a>
      <a href="/vorgang" type="button" class="btn btn-primary">Neuer Vorgang</a>
    </div>
  </div>
</nav>
