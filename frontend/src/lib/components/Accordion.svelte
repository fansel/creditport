<script>
  export let open = false;
  import { slide } from 'svelte/transition';
  const handleClick = () => (open = !open);
</script>

<div class="accordion">
  <div class="card">
    <button class="reset-btn card-header btn m-0" on:click={handleClick}>
      <h5 class="mb-0 d-flex justify-content-between">
        <slot name="head" />
        <!-- <button class="btn btn-link" on:click={handleClick}> </button> -->
        <span class="bi-chevron-down small"></span>
      </h5>
    </button>

    {#if open}
      <div class="collapse show" transition:slide>
        <div class="card-body">
          <slot name="details" />
        </div>
      </div>
    {/if}
  </div>
</div>

<style>
  .accordion {
    margin: 1rem 0;
  }

  .card {
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 0.25rem;
  }

  .card-header {
    background-color: #f8f9fa;
    border-bottom: 1px solid rgba(0, 0, 0, 0.125);
    padding: 0.75rem 1.25rem;
    margin-bottom: 0;
  }

  .card-header h5 {
    margin-bottom: 0;
  }

  .btn-link {
    color: #007bff;
    background-color: transparent;
    text-decoration: none;
    font-weight: 400;
  }

  .btn-link:hover {
    color: #0056b3;
    text-decoration: underline;
  }

  .collapse {
    display: none;
  }

  .collapse.show {
    display: block;
  }

  .card-body {
    padding: 1.25rem;
  }
</style>
