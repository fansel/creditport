<script>
  import Modal from '$lib/components/FormModal.svelte';
  import { page } from '$app/stores';
  import { onMount } from 'svelte';
  import * as config from '$lib/config';
  import { truncateText } from '$lib/util';
  import { format, parseISO } from 'date-fns';
  import RequestStatus from '$lib/components/RequestStatus.svelte';
  import { procedure_schema } from '$root/lib/schema';
  import { superForm, dateProxy } from 'sveltekit-superforms';
  import { add_course_schema, update_course_schema, update_internal_modul_schema, user_schema } from '$root/lib/schema';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import SuperDebug from 'sveltekit-superforms';
  import { invalidateAll } from '$app/navigation';
  import ProcedureStatus from '$root/lib/components/ProcedureStatus.svelte';

  //   export let request;

  //   let allExternalModules = data.allExternalModules;
  //   let allInternalModules = data.allInternalModules;

  export let externalModules;
  export let internalModules;
  export let comment;
</script>

<div class="align-items-start flex-wrap flex-md-nowrap gap-2">
  <div class="row">
    <div class="col-md-3 externe-module">
      <div class="fw-bold">Fremdmodule (10)</div>
      {#each externalModules as module, z}
        <input disabled type="text" class="form-control my-2" placeholder={module.moduleName} />
      {/each}
    </div>
    <div class="col-md-3 internen-module">
      <div class="fw-bold">anzurechnende Module (10)</div>
      {#each internalModules as module, z}
        <input disabled type="text" class="form-control my-2" placeholder={module.moduleName} />
      {/each}
    </div>
    <div class="col-md-6 comment d-flex flex-grow-1">
      {#if comment}
        <div class="mb-3">
          <label class="mb-2 fw-bold" for="">Anmkerung des Studienbüros</label>
          <p class="moduleDescription border p-2 rounded">
            {comment}
          </p>
        </div>
      {/if}
    </div>
  </div>
</div>
