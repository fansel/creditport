<script>
</script>

<ul class="status-list my-3 m-0 p-0" id="pills-tab" role="tablist">
  <li class="status-item" role="presentation">
    <button
      class="status-button active fw-bold text-primary"
      id="pills-general-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-general"
      type="button"
      role="tab"
      aria-controls="pills-general"
      aria-selected="true"
    >
      <div class="rounded-circle border-3 border border-primary status-circle">
        <span>1</span>
      </div>

      Allgemeine Angaben
    </button>
  </li>
  <li class="status-item" role="presentation">
    <button
      class="status-button fw-bold text-primary"
      id="pills-module-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-module"
      type="button"
      role="tab"
      aria-controls="pills-module"
      aria-selected="false"
    >
      <div class="rounded-circle border-3 border border-primary status-circle">
        <span>2</span>
      </div>

      Module
    </button>
  </li>
  <li class="status-item" role="presentation">
    <button class="status-button fw-bold text-primary" id="pills-send-tab" data-bs-toggle="pill" data-bs-target="#pills-send" type="button" role="tab" aria-controls="pills-send" aria-selected="false">
      <div class="rounded-circle border-3 border border-primary status-circle">
        <span>3</span>
      </div>

      Abschicken
    </button>
  </li>
</ul>
<hr />

<div class="tab-content" id="pills-tabContent">
  <div class="tab-pane fade show active" id="pills-general" role="tabpanel" aria-labelledby="pills-general-tab" tabindex="0">
    <div class="w-100">
      <!-- <h1 class="my-3 h2">Allgemeine Angaben</h1> -->
      <form action="/?add" method="POST">
        <div class="row">
          <div class="col">
            <div class="mb-3">
              <label for="" class="mb-2">Bisherige Universität</label>
              <input type="text" class="form-control" />
            </div>
            <div class="mb-3">
              <label for="" class="mb-2">Bisheriger Studiengang</label>
              <input type="text" class="form-control" />
            </div>
          </div>
          <div class="col">
            <p />
          </div>
        </div>

        <button class="btn btn-primary">Weiter</button>
      </form>
    </div>
  </div>
  <div class="tab-pane fade" id="pills-module" role="tabpanel" aria-labelledby="pills-module-tab" tabindex="0">
    <div class="w-100">
      <h1 class="my-3 display-6">Wähle deine Module und für was du sie angerechnet bekommen willst</h1>

      <form action="POST">
        <div class="card w-100 mb-3">
          <div class="card-header fw-bold">Modul Antrag 1</div>
          <div class="card-body">
            <div class="row">
              <div class="col">
                <h2 class="h4">Fremdmodul</h2>
                <div class="mb-3">
                  <label for="" class="mb-2">Modulname</label>
                  <input type="text" class="form-control" />
                </div>
              </div>
              <div class="col">
                <div class="mb-3">
                  <label for="" class="mb-2">Modulename</label>
                  <select class="form-select" aria-label="Default select example">
                    <option selected>Open this select menu</option>
                    <option value="1">One</option>
                    <option value="2">Two</option>
                    <option value="3">Three</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="w-100 d-inline-flex justify-content-center"><a href="/"><i class="bi bi-plus-circle" style="width: 3rem;"/> </a></div>

        <button class="btn btn-primary">Weiter</button>
      </form>
    </div>
  </div>
  <div class="tab-pane fade" id="pills-send" role="tabpanel" aria-labelledby="pills-send-tab" tabindex="0" />
</div>

<style>
  .status-circle {
    width: 50px;
    height: 50px;

    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.25rem; /* Schriftgröße der Zahl */
    margin-right: 1rem;
  }

  .status-list {
    list-style-type: none;
    display: flex;
    justify-content: space-between;
  }

  .status-button.active > .status-circle {
    background-color: #0d6efd;
    color: white;
  }

  .status-button {
    background-color: white;
    border: none;
    display: flex;
    align-items: center;
  }
</style>
