<script>
  import { format, parseISO } from 'date-fns';
  import ProcedureStatus from '$root/lib/components/ProcedureStatus.svelte';
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';
  import StatusComponent from './StatusComponent.svelte';
  import Antrag from './Antrag.svelte';
  import Accordion from '$root/lib/components/Accordion.svelte';
  import Mapping from './Mapping.svelte';

  export let data;

  const procedure = data.procedure;
  const requests = data.procedure.requests;
  const allInternalModules = data.allInternalModules;
  const allExternalModules = data.allExternalModules;

  // console.log('all internal: ', allInternalModules);
  // console.log('all external: ', allExternalModules);

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
</script>

<div class="card mt-5">
  <div class="card-header">
    <div class="d-flex justify-content-between">
      <div class="align-self-start"><strong>Vorgang:</strong> {procedureId} mit <strong>{requests.length}</strong> Anträgen</div>
      <div class="d-flex justify-content-center flex-grow-1"><strong>Status:</strong> <ProcedureStatus extern={true} status={data.procedure.status} /></div>
      <div class="align-self-end">
        erstellt am <strong>{format(new Date(procedure.createdAt), 'dd.MM.yyyy')}</strong>
      </div>
    </div>
  </div>
  <div class="card-body status-container d-flex">
    <StatusComponent header="ANGENOMMEN" statusCount={countRequests(data.procedure, 'ANGENOMMEN')} colorTag="success" />
    <StatusComponent header="ABGELEHNT" statusCount={countRequests(data.procedure, 'ABGELEHNT')} colorTag="danger" />
    <StatusComponent header="IN BEARBEITUNG" statusCount={countRequests(data.procedure, 'IN_BEARBEITET')} colorTag="warning" />
    <StatusComponent header="NICHT BEARBEITET" statusCount={countRequests(data.procedure, 'NICHT_BEARBEITET')} />
  </div>

  <!-- NEUE VERSION -->
  <div class="new-version mx-2">
    {#each requests as request}
      <Accordion>
        <div slot="head" class="fs-6 d-flex">
          <div class="fw-bold justify-content-start">LP</div>
          <RequestStatus class="ml-auto" status={request.statusRequest} />
        </div>
        <div slot="details">
          <div class=" p-2">
            <Mapping
              externalModules={allExternalModules.filter((module) => request.externalModuleIds.includes(module.moduleId))}
              internalModules={allInternalModules.filter((module) => request.internalModuleIds.includes(module.moduleId))}
              comment={request.annotationStudent}
            ></Mapping>
          </div>
        </div>
      </Accordion>
    {/each}
  </div>
</div>

<!-- <pre>
    {JSON.stringify(data.procedure, null, 2)}
</pre> -->

<style>
  .status-container {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
  }
</style>
