<script>
  import Footer from "$lib/components/Footer.svelte";
</script>


<div class="px-4 pt-5 mt-5 mb-2 text-center">
    <h1 class="display-4 fw-bold text-body-emphasis">Anrechnungsprozess starten</h1>
    <div class="col-lg-6 mx-auto">
      <p class="lead mb-4">Starte ganz einfach in dem du einen neuen Vorgang erstellst oder den Status eines bestehenden Vorangs einsiehst. Gebe dafür deine Vorgangsnummer ein..</p>
      <div class="d-grid gap-2 d-sm-flex justify-content-sm-center mb-5">
        <a href="/vorgang" type="button" class="btn btn-primary btn-lg px-4 me-sm-3">Neuer Vorgang</a>
        <a href="/status" type="button" class="btn btn-outline-secondary btn-lg px-4">Status einsehen</a>
      </div>
    </div>
</div>

<div class="d-flex justify-content-center align-items-center">
    <div class="border rounded-3 p-3 m-3">
        <img src="/uni-logo.png" class="uni-logo" alt="">
    </div>
</div>

<Footer />

<style>
  .uni-logo {
    max-width: 25rem;
  }

  @media screen and (max-width: 768px) {
    .uni-logo {
      max-width: 100%;
    }
  }

</style>

  