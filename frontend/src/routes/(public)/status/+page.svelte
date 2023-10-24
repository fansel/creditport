<div class="mt-5 d-flex flex-column align-items-center">
  <h1 class="display-4 fw-bold text-body-emphasis">Statusabfrage</h1>
  <p class="lead">Gib deine Vorgangsnummer an und erhalte alle Infos zum aktuellen Bearbeitungsstand deines Vorgangs.</p>

  <form action="" class="status-form rounded-3 mb-4">
    <div class="digit-fields">
      <input type="text" class="digit" maxlength="1" autofocus />
      <input type="text" class="digit" maxlength="1" />
      <input type="text" class="digit" maxlength="1" />
      <input type="text" class="digit" maxlength="1" />
      <input type="text" class="digit" maxlength="1" />
      <input type="text" class="digit" maxlength="1" />
    </div>

    <!-- <div class="form-floating mb-3">
      <input type="text" class="form-control" id="vorgangsnummerInput" />
      <label for="vorgangsnummerInput">Vorgangsnummer</label>
    </div> -->

    <button class="btn btn-primary">Status abfragen</button>
  </form>
</div>

<style>
  .digit-field {
    display: flex;
    flex-direction: row;
  }

  .digit {
    width: 4.9rem;
    padding: 1.5rem;
    font-size: 1.5rem;
    border: none;
    outline: 1px solid #6c757d;
    margin: 0 1rem 0 0;
    background-color: white;
    text-align: center;
    border-radius: 0.5rem;
    transition: all 150ms ease-in-out;
  }

  .digit:focus {
    color: blue;
    outline: 0.3rem solid #0d6efd;
  }

  .digit:last-child {
    margin: 0 0 0 0;
  }

  .digit:nth-child(3) {
    margin: 0 3rem 0 0;
  }

  .status-form {
  }

  @media screen and (max-width: 768px) {
    .status-form {
      max-width: 100%;
      margin-left: 1rem;
      margin-right: 1rem;
    }
  }
</style>
