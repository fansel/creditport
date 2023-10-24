<script>
  import { page } from '$app/stores';
</script>

<nav class="navbar navbar-expand-md navbar-light bg-light shadow-sm p-3 sticky-top" aria-label="Fourth navbar example">
  <div class="container">
    <a class="navbar-brand" href="/"><i class="bi bi-send" /> </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon" />
    </button>

    <div class="collapse navbar-collapse" id="navbarsExample04">
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <li class="nav-item"><a href="/dashboard" class="nav-link px-2 {$page.data.title == 'Dashboard' ? 'text-dark' : 'text-secondary'}">Dashboard</a></li>
        <li class="nav-item"><a href="/procedures" class="nav-link px-2 {$page.data.title == 'Vorgänge' ? 'text-dark' : 'text-secondary'}">Vorgänge</a></li>

        <li class="nav-item"><a href="/items" class="nav-link px-2 {$page.data.title == 'Faq' ? 'text-dark' : 'text-secondary'}">Markierte</a></li>
        <li class="nav-item"><a href="/items" class="nav-link px-2 {$page.data.title == 'Impressum' ? 'text-dark' : 'text-secondary'}">Archiv</a></li>
      </ul>
      <!-- <a href="/login" type="button" class="btn btn-outline-secondary me-2">Logout</a>
      <a href="/vorgang" type="button" class="btn btn-primary">Einstellungen</a> -->
      <div class="dropdown ms-3">
        <a href="#" class="d-flex align-items-center link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
          <strong>Studienbüro</strong>
          <img src="https://github.com/mdo.png" alt="" width="32" height="32" class="rounded-circle ms-2" />
        </a>
        <ul class="dropdown-menu text-small">
          <li><a class="dropdown-item" href="#">Einstellungen</a></li>
          <li><a class="dropdown-item" href="/profile">Profil</a></li>
          <li><hr class="dropdown-divider" /></li>
          <li><a class="dropdown-item" href="#">Logout</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>
