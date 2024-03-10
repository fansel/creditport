<script>
  import { format, parseISO } from 'date-fns';
  import ProcedureStatus from '$root/lib/components/ProcedureStatus.svelte';
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';
  import StatusComponent from './StatusComponent.svelte';
  import Antrag from './Antrag.svelte';

  export let data;

  const procedure = data.procedure;
  const requests = data.procedure.requests;

  const procedureId = procedure.procedureId;
  const fremduni = procedure.university;

  // function to count request within procedure
  function countRequests(procedure, status) {
    let count = 0;

    procedure.requests.forEach((request) => {
      if (request.statusRequest === status) {
        count++;
      }
    });

    return count;
  }

  // function that returns all data necessary for Detail.svelte

  console.log(data.procedure.status);
</script>

<div class="card mt-5">
  <div class="card-header">
    <div class="d-flex justify-content-between">
      <div class="align-self-start"><strong>Vorgang:</strong> {procedureId} mit <strong>{requests.length}</strong> Anträgen</div>
      <div class="d-flex justify-content-center flex-grow-1"><strong>Status:</strong> <ProcedureStatus status={data.procedure.status} /></div>
      <div class="align-self-end">
        erstellt am <strong>{format(new Date(procedure.createdAt), 'dd.MM.yyyy')}</strong>
      </div>
    </div>
  </div>
  <div class="card-body status-container">
    <StatusComponent header="NICHT BEARBEITET" statusCount={countRequests(data.procedure, 'NICHT_BEARBEITET')} />
    <StatusComponent header="IN BEARBEITUNG" statusCount={countRequests(data.procedure, 'IN_BEARBEITET')} colorTag="warning" />
    <StatusComponent header="ANGENOMMEN" statusCount={countRequests(data.procedure, 'ANGENOMMEN')} colorTag="success" />
    <StatusComponent header="ABGELEHNT" statusCount={countRequests(data.procedure, 'ABGELEHNT')} colorTag="danger" />
  </div>
  <div class="my-1">
    {#each requests as antrag}
      <!-- Würde Sinn ergegben ein if statement zu setzen für die unbearbeiteten Anträge, weil es dort keine Annotations gibt -->
      <div class="m-3">
        <Antrag bind:request={antrag} />
      </div>
    {/each}
  </div>
</div>

<pre>
    {JSON.stringify(data.procedure, null, 2)}
</pre>

<style>
  .status-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
  }
</style>
