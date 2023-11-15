<div class="row py-3 mb-3 pb-md-1 border-bottom align-items-center">
  <div class="col-md">
    <h1>Einstellungen</h1>
  </div>
</div>

<ul class="nav nav-tabs border-left border-right" id="myTab" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="home-tab" data-bs-toggle="tab" data-bs-target="#home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane" aria-selected="true">Allgemein</button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile-tab-pane" type="button" role="tab" aria-controls="profile-tab-pane" aria-selected="false"
      >Benutzer&Gruppen</button
    >
  </li>
</ul>

<!-- Einstellungs INHALT -->
<div class="tab-content border-left border-right" id="myTabContent">
  <div class="tab-pane fade show active" id="home-tab-pane" role="tabpanel" aria-labelledby="home-tab" tabindex="0">
    <!-- change password -->
    <p>
      <a class="btn btn-outline-primary dropdown-toggle" data-bs-toggle="collapse" href="#collapsePassword" role="button" aria-expanded="false" aria-controls="collapsePassword"> Passwort ändern </a>
    </p>
    <div class="collapse" id="collapsePassword">
      <div class="card card-body">
        <div class="mb-3">
          <label for="" class="mb-2">Bisheriges Passwort</label>
          <input type="text" class="form-control" />
        </div>
        <div class="mb-3">
          <label for="" class="mb-2">Neues Passwort</label>
          <input type="text" class="form-control" />
        </div>
        <div class="mb-3">
          <label for="" class="mb-2">Neues Passwort bestätigen</label>
          <input type="text" class="form-control" />
        </div>
        <button type="button" class="btn btn-primary">Password ändern</button>
      </div>
    </div>
    <!-- change password end -->

    <!-- filter settings -->
    <p style="">Filter</p>
    <p>
      <a class="btn btn-primary" data-bs-toggle="collapse" href="#collapseFilter" role="button" aria-expanded="false" aria-controls="collapseFilter"> Suchfilter </a>
    </p>
    <div class="collapse" id="collapseFilter">
      <!-- CONTENT filter settings -->
      <div class="card card-body">
        <p>CONTENT</p>
        <button class="dropdown-toggle btn btn-outline-secondary btn-sm" type="button" data-bs-toggle="dropdown">Status</button>
      </div>
    </div>
    <!-- filter settings end -->
  </div>
  <div class="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab" tabindex="0">
    <table class="table table-striped">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Vorname</th>
          <th scope="col">Nachname</th>
          <th scope="col">Zugriffsrechte</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>Leopold</td>
          <td>Flemming</td>
          <td>
            <span class="badge bg-success-subtle border border-success-subtle text-secondary-emphasis rounded-pill">alle</span>
          </td>
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>Elias</td>
          <td>Taibi Haschke</td>
          <td>
            <span class="badge bg-danger-subtle border border-danger-subtle text-secondary-emphasis rounded-pill">keine</span>
          </td>
        </tr>
        <tr>
          <th scope="row">3</th>
          <td>Clara</td>
          <td>Schmidt</td>
          <td>
            <span class="badge bg-success-subtle border border-success-subtle text-secondary-emphasis rounded-pill">alle</span>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</div>

<style>
  .tab-content {
    border-left: 1px solid #ddd;
    border-right: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
    padding: 10px;
  }

  .nav-item {
    font-size: 1.25em;
  }
</style>
