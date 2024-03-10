<script>
  import { page } from "$app/stores";
  export let modulesForm;
</script>

<!-- Tab Send -->

<button class="btn btn-primary" on:click={() => modulesForm.submit()}>Senden</button>
