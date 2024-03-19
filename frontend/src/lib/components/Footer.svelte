<script>
  import { env } from '$env/dynamic/public';
  export let status;
</script>

<div class="container">
  <footer class="footer border-top">
    <p class="mb-0 text-body-secondary">© 2023 Universität Leipzig</p>

    {#if env.PUBLIC_ENV === 'development'}
      <span class="badge {status ? 'text-bg-success' : 'text-bg-danger'}">{status ? 'API Online' : 'API Offline'}</span>
    {/if}

    <ul class="nav">
      <li class="nav-item"><a href="/" class="nav-link px-2 text-body-secondary">Home</a></li>
      <li class="nav-item"><a href="/faq" class="nav-link px-2 text-body-secondary">FAQs</a></li>
      <li class="nav-item"><a href="/impressum" class="nav-link px-2 text-body-secondary">Impressum</a></li>
      <!-- <li class="nav-item"><a href="/dashboard" class="nav-link px-2 text-body-secondary">Dashboard</a></li> -->
      <!-- <li class="nav-item dropup">
        <button type="button" class="btn btn-link text-secondary nav-link py-2 px-0 px-lg-2 d-flex align-items-center btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
          <i class="bi bi-sun-fill" />
        </button>
        <ul class="dropdown-menu dropdown-menu-up">
          <li><button class="dropdown-item d-flex align-items-center"><i class="bi bi-sun-fill me-2 opacity-50" />Light</button></li>
          <li><button class="dropdown-item d-flex align-items-center"><i class="bi bi-moon-stars-fill me-2 opacity-50" />Dark</button></li>
          <li><button class="dropdown-item d-flex align-items-center"><i class="bi bi-circle-half me-2 opacity-50" />Auto</button></li>
        </ul>
      </li> -->
    </ul>
  </footer>
</div>

<style>
  .footer {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 1rem;
    margin-top: 2rem;
    padding-bottom: 1rem;
  }

  @media screen and (max-width: 768px) {
    .footer {
      justify-content: center;
      flex-direction: column;
    }
  }
</style>
