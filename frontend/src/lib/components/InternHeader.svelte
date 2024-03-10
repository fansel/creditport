<script>
  import { page } from '$app/stores';

  export let wide = false;
</script>

<nav class="navbar navbar-expand-md navbar-light shadow-sm p-3 sticky-top" aria-label="Fourth navbar example">
  <div class={wide ? 'container-fluid' : 'container'}>
    <a class="navbar-brand" href="/"><i class="bi bi-send" /> </a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon" />
    </button>

    <div class="collapse navbar-collapse" id="navbarsExample04">
      <ul class="navbar-nav me-auto mb-2 mb-md-0">
        <li class="nav-item"><a href="/dashboard" class="nav-link px-2 {$page.data.title == 'Dashboard' ? 'fw-bold' : ''}">Dashboard</a></li>
        <li class="nav-item"><a href="/procedures" class="nav-link px-2 {$page.data.title == 'Vorgänge' ? 'fw-bold' : ''}">Vorgänge</a></li>

        <li class="nav-item"><a href="/procedures" class="nav-link px-2 {$page.data.title == 'Faq' ? 'fw-bold' : ''}">Markierte</a></li>
        <li class="nav-item"><a href="/procedures" class="nav-link px-2 {$page.data.title == 'Impressum' ? 'fw-bold' : ''}">Archiv</a></li>
      </ul>

      <div class="dropdown profil-dropdown">
        <button class="profil-btn d-flex align-items-center link-body-emphasis text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
          <strong>{$page.data?.user?.username}</strong>
          <img src="https://github.com/mdo.png" alt="" width="32" height="32" class="rounded-circle ms-2" />
        </button>
        <ul class="dropdown-menu text-small">
          <li><a class="dropdown-item" href="/settings">Einstellungen</a></li>
          <li><hr class="dropdown-divider" /></li>
          <li><a class="dropdown-item" href="/exit">Logout</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>

<style>
  .profil-btn {
    border: none;
    background-color: transparent;
  }
</style>
