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

  console.log('ex: ', externalModules);
</script>

<div class="align-items-start flex-wrap flex-md-nowrap gap-2">
  <div class="row">
    <div class="col-sm-5 col-xs-12 col-lg-4 externe-module">
      <div class=""><span class="fw-bold">Fremdmodule</span> ({externalModules.reduce((sum, item) => sum + item.creditPoints, 0)} LP)</div>
      {#each externalModules as module, z}
        <input disabled type="text" class="form-control my-2" placeholder={module.moduleName} />
      {/each}
    </div>
    <div class="col-sm-7 col-xs-12 col-lg-4 internen-module">
      <div><span class="fw-bold">anzurechnende Module</span> ({internalModules.reduce((sum, item) => sum + item.creditPoints, 0)} LP)</div>
      {#each internalModules as module, z}
        <input disabled type="text" class="form-control my-2" placeholder={module.moduleName} />
      {/each}
    </div>
    <div class="col-sm-12 col-xs-12 col-lg-4 comment d-flex flex-grow-1">
      {#if comment}
        <div class="mb-3 vstack">
          <label class="mb-2 fw-bold" for="">Anmerkung des Studienbüros</label>
          <!-- <textarea type="text" class="form-control auto-resize-textarea" rows={(x, y) => Math.ceil(Math.max(x, y) * 1.5)}>{comment}</textarea> -->
          <textarea readonly type="text" class="form-control auto-resize-textarea" rows="4">{comment}</textarea>
        </div>
      {:else}
        <label class="fw-bold">
          <i class="bi bi-exclamation-triangle" />
          Keine Anmerkungen verfügbar</label
        >
      {/if}
    </div>
  </div>
</div>

<style>
  .auto-resize-textarea {
    resize: vertical;
    height: 100%;
    /* max-height: 150px; */
    /* min-height: 200px;
    max-height: 50%; */
    /* width: 100%;  */
    /* box-sizing: border-box; */
    /* overflow-y: hidden;  */
    /* padding: 6px 12px; */
    /* border: 1px solid #ced4da;  */
    /* border-radius: 4px;  */
    /* background-color: #f8f9fa; */
  }
</style>
