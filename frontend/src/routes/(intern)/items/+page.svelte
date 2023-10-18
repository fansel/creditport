<script>
  import { format } from 'date-fns';
  import { enhance } from '$app/forms';
</script>

<h1 class="my-3">Vorgänge</h1>

<input type="text" placeholder="Suche" class="form-control mb-3" />

<!-- <div class="d-flex justify-content-between align-items-center">
  <select class="form-select form-select-sm w-auto" aria-label="Select pagesize">
    <option value="25">25</option>
    <option value="50">50</option>
    <option value="75">75</option>
    <option value="100">100</option>
    <option value="10">Alle</option>
  </select>


  <div>
    <label for="">Filtern nach Status</label>
    <select class="form-select form-select-sm w-auto" aria-label="Select pagesize">
      <option value="25">offen</option>
      <option value="50">bearbeitung</option>
      <option value="75">gehnemigt</option>
    </select>
  </div>

  <div class="d-flex align-items-center">
    <label for="">Sortieren nach</label>
    <select class="form-select form-select-sm w-auto" aria-label="Select pagesize">
      <option value="25">Erstellt am</option>
      <option value="50">Zu letzt bearbeitet</option>
      <option value="75">Universität</option>
    </select>
  </div>

  <nav aria-label="..." class="">
    <ul class="pagination pagination-sm">
      <li class="page-item"><button class="page-link" aria-label="Previous"><span aria-hidden="true">&laquo;</span></button></li>

      <li class="page-item active">
        <button class="page-link">1</button>
      </li>

      <li class="page-item">
        <button class="page-link">2</button>
      </li>
      <li class="page-item">
        <button class="page-link">3</button>
      </li>
      <li class="page-item"><button class="page-link" aria-label="Next"><span aria-hidden="true">&raquo;</span></button></li>
    </ul>
  </nav>
</div> -->

<div class="table-responsive">
  <table class="table table-sm table-hover table-responsive align-middle">
    <thead>
      <tr>
        <th>Eingereicht am</th>
        <th>Universität</th>
        <th>Studiengang</th>
        <th>Anzahl der Anträge</th>
        <th>Status</th>
        <th></th>
      </tr>
    </thead>

    <tbody>
      <tr>
        <td>{format(new Date(), 'dd.MM.yyyy HH:mm')} </td>
        <td>Universität Halle</td>

        <td>Bachlor Informatik</td>
        <td>5</td>

        <td><span class="badge bg-secondary-subtle border border-secondary-subtle text-secondary-emphasis rounded-pill">eingereicht</span></td>

        <td>
          <div class="btn-group text-nowrap float-end" role="group">
            <form method="POST" action="?/delete" use:enhance>
              <input type="hidden" name="id" value="1" />
              <button class="btn btn-sm btn-danger btn-group-left" type="submit"><i class="bi bi-x-lg" /></button>
            </form>
            <a type="button" href="/jobs/2" class="btn btn-sm btn-primary btn-group-right"><i class="bi bi-box-arrow-up-right" /></a>
          </div>
        </td>
      </tr>
    </tbody>
  </table>
</div>

<style>
</style>
