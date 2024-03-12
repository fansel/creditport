<script>
  import { page } from '$app/stores';
  import { getNachfolger, getVorgänger } from '$lib/util';

  export let request = $page.data.request;

  export let showModalExtern = false;
  export let showModalIntern = false;
</script>

<div class="nav-bar my-3" width="100%">
  {#if request.relatedRequests.length > 0}
    {#if getVorgänger(request.requestId, request.relatedRequests) != null}
      <a type="button" class="btn btn-sm btn-outline-primary" rel="external" href="/procedures/{getVorgänger(request.requestId, request.relatedRequests)}">
        <i class="bi bi-arrow-left" />
      </a>
    {/if}
    {#if getNachfolger(request.requestId, request.relatedRequests) != null}
      <a type="button" class="btn btn-sm btn-outline-primary" rel="external" href="/procedures/{getNachfolger(request.requestId, request.relatedRequests)}">
        <i class="bi bi-arrow-right" />
      </a>
    {/if}
  {/if}

  <div class="btn-group dropend">
    <button type="button" class="btn btn-sm btn-outline-primary dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">ähnliche Anträge</button>
    <div class="dropdown-menu">
      <button class="dropdown-item" on:click={() => (showModalExtern = true)}>Module für aktuelles Fremdmodul</button>
      <button class="dropdown-item" on:click={() => (showModalIntern = true)}>Akzeptierte Fremdmodule für Modulvorschlag</button>
    </div>
  </div>

  <div class="btn btn-sm btn-success align-self-end">Weiterleiten</div>
</div>
