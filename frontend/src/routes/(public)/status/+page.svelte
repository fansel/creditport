<div class="mt-5 d-flex flex-column align-items-center">
  <h1 class="display-4 fw-bold text-body-emphasis">Statusabfrage</h1>
  <p class="lead mb-4">Gib deine Vorgangsnummer an und erhalte alle Infos zum aktuellen Bearbeitungsstand deines Vorgangs.</p>

  <form action="" class="status-form rounded-3 my-4">
    <div class="form-floating mb-3">
      <input type="text" class="form-control" id="vorgangsnummerInput" />
      <label for="vorgangsnummerInput">Vorgangsnummer</label>
    </div>

    <button class="btn btn-primary">Status abfragen</button>
  </form>
</div>

<style>
  .status-form {
    width: 500px;
  }

  @media screen and (max-width: 768px) {
    .status-form {
      max-width: 100%;
      margin-left: 1rem;
      margin-right: 1rem;
    }
  }
</style>
