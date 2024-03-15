<script>
  import { format, parseISO } from 'date-fns';
  import ProcedureStatus from '$root/lib/components/ProcedureStatus.svelte';
  import RequestStatus from '$root/lib/components/RequestStatus.svelte';
  import StatusComponent from './StatusComponent.svelte';
  import Accordion from '$root/lib/components/Accordion.svelte';
  import Mapping from './Mapping.svelte';
  import { api_endpoint, status_requests } from '$lib/config';
  import * as api from '$lib/api';

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
  function countRequestsHelper(procedure, status) {
    let count = 0;

    procedure.requests.forEach((request) => {
      if (request.statusRequest === status_requests[status].match) {
        count++;
      }
    });

    return count;
  }

  function countRequests(status) {
    return countRequestsHelper(data.procedure, status);
  }

  const sumLP = (list) => list.reduce((sum, item) => sum + item.creditPoints, 0);
</script>

<div class="card mt-5">
  <div class="card-header">
    <div class="row">
      <div class="col-12 col-md-4 justify-content-center justify-content-md-start d-flex"><strong>Vorgang:</strong> {procedureId}</div>
      <div class="col-12 col-md-4 d-flex justify-content-center flex-grow-1"><strong>Status:</strong> <ProcedureStatus extern={true} status={data.procedure.status} /></div>
      <label for="" class="col-12 col-md-4 d-flex justify-content-center justify-content-md-end">
        erstellt am&nbsp; <strong>{format(new Date(procedure.createdAt), 'dd.MM.yyyy')}</strong>
      </label>
    </div>
  </div>

  <!-- <div class="overview">
    <div class="row pt-2">
      <div class="col-6 col-md-3 d-flex justify-content-center align-items-center">
        {countRequests(3)}&nbsp;x<RequestStatus status={status_requests[3].match} extern={true}></RequestStatus>
      </div>
      <div class="col-6 col-md-3 d-flex justify-content-center align-items-center">
        {countRequests(4)}&nbsp;x<RequestStatus status={status_requests[4].match} extern={true}></RequestStatus>
      </div>
      <div class="col-6 col-md-3 d-flex justify-content-center align-items-center">
        {countRequests(2) + countRequests(1)}&nbsp;x<RequestStatus status={status_requests[1].match} extern={true}></RequestStatus>
      </div>
      <div class="col-6 col-md-3 d-flex justify-content-center align-items-center">
        {countRequests(0)}&nbsp;x<RequestStatus status={status_requests[0].match} extern={true}></RequestStatus>
      </div>
    </div>
  </div> -->

  <!-- NEUE VERSION -->
  <div class="new-version mx-2">
    <div class="pdf d-flex justify-content-center mt-3">
      <a target="_blank" class="btn btn-primary" href={api.routes.pdf_overview_download(data.uuid)}>Download Overview PDF</a>
    </div>
    {#each requests as request, index}
      <Accordion>
        <div slot="head" class="fs-6 d-flex w-100">
          <!-- <div class="fw-bold justify-content-start">requestID: {request.requestId}</div> -->
          Antrag: {index + 1}
          <RequestStatus status={request.statusRequest} extern={true} />
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
