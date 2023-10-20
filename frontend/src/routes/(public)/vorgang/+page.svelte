<script>
  export let data;

  const modules = data.modules.courses[0].modules;
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
          <div class="col-md">
            <div class="mb-3">
              <label for="" class="mb-2">Bisherige Universität</label>
              <input type="text" class="form-control" />
            </div>
            <div class="mb-3">
              <label for="" class="mb-2">Bisheriger Studiengang</label>
              <input type="text" class="form-control" />
            </div>
          </div>
          <div class="col-md">
            <div class="alert alert-primary" role="alert"><span class="fw-bold me-2">Vorgangsnummer: </span>3987-5309-2432</div>
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
          <div class="card-header fw-bold">Modulantrag für Anrechnung der Universität Halle</div>
          <div class="card-body">
            <div class="row">
              <div class="col-md">
                <h2 class="h4">Fremdmodul</h2>

                <div class="mb-3">
                  <label for="" class="mb-2">Name</label>
                  <input type="text" class="form-control" placeholder="Modellierung und Programmierung" />
                </div>

                <div class="row">
                  <div class="col-md-8">
                    <div class="mb-3">
                      <label for="" class="mb-2">Website zum Modul</label>
                      <input type="text" class="form-control" placeholder="http://uni-leipzig.de/module_xyz" />
                    </div>
                  </div>
                  <div class="col-md-4">
                    <div class="mb-3">
                      <label for="" class="mb-2">LP</label>
                      <input type="text" class="form-control" placeholder="5" />
                    </div>
                  </div>
                </div>
                <div class="mb-3">
                  <label for="formFile" class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
                  <input class="form-control" type="file" id="formFile" />
                </div>
              </div>
              <div class="col-md">
                <h2 class="h4">Modul Vorschlag</h2>

                <div class="mb-3">
                  <label for="" class="mb-2">Name</label>
                  <select class="form-select" aria-label="Default select example">
                    {#each modules as modul}
                      <option value={modul.name}>{modul.name}</option>
                    {/each}
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="w-100 d-inline-flex justify-content-center"><a href="/"><i class="bi bi-plus-circle" style="font-size: 2rem;" /></a></div>

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

  @media screen and (max-width: 768px) {
    .status-circle {
      font-size: 1rem;
    }

    .status-list {
      flex-direction: column;
    }
  }
</style>
