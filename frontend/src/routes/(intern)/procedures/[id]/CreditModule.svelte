<script>
  import { page } from '$app/stores';

  export let modules = $page.data.modules;
  export let selectedModul;
</script>

<div class="mb-3">
  <label for="" class="mb-2"><strong>Anrechnungsmodul</strong></label>
  <select class="form-select" aria-label="Default select example" bind:value={selectedModul}>
    {#each modules as modul, index}
      <option value={index}>{modul.moduleName}</option>
    {/each}
  </select>
</div>
