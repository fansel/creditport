<script>
  export let status;
</script>

<div class="container">
  <footer class="footer border-top">
    <p class="mb-0 text-body-secondary">© 2023 Universität Leipzig</p>
    <span class="badge {status ? 'text-bg-success' : 'text-bg-danger'}">{status ? 'API Online' : 'API Offline'}</span>

    <ul class="nav">
      <li class="nav-item"><a href="/" class="nav-link px-2 text-body-secondary">Home</a></li>
      <li class="nav-item"><a href="/faq" class="nav-link px-2 text-body-secondary">FAQs</a></li>
      <li class="nav-item"><a href="/impressum" class="nav-link px-2 text-body-secondary">Impressum</a></li>
      <li class="nav-item"><a href="/dashboard" class="nav-link px-2 text-body-secondary">Dashboard</a></li>
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
