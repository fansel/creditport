<script>
  import { page } from '$app/stores';
  import { format, parseISO } from 'date-fns';

  let procedure = $page.data.procedure;
  let request = $page.data.request;
</script>

<div class="border-bottom m-3 d-flex">
  <div class="dates">
    <i class="bi bi-calendar"></i>
    {format(new Date(request.createdAt), 'dd.MM.yyyy')}

    <i class="bi bi-calendar-range"></i>
    {format(new Date(request.createdAt), 'dd.MM.yyyy')}
  </div>

  <div class="mx-2 d-flex">
    <div class="vorgangsnummer">
      <strong>Vorgangsnummer: </strong>
      {request.procedureId}
    </div>
    <div class="studium mx-3">
      <strong>{procedure.course.courseName} </strong>
    </div>
  </div>

  <div class="status justify-content-center">NEU</div>
</div>

<style>
  i {
    margin-left: 20px;
  }
</style>
