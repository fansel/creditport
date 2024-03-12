<script>
  import { page } from '$app/stores';
  import { onMount } from 'svelte';

  export let modules = $page.data.modules;
  export let selectedOption;
  export let index;

  let currentModule = $page.data.request.internalModules[index];
  let isOpen = false;
  let dropdown;

  const options = modules;

  // DELETE
  $: {
    console.log('currentModule: ', currentModule);
  }
  $: {
    console.log(selectedOption);
  }

  function selectOption(option) {
    selectedOption = option;
    isOpen = false;
  }

  onMount(() => {
    const handleClickOutside = (event) => {
      if (!dropdown.contains(event.target)) {
        isOpen = false;
      }
    };

    document.addEventListener('click', handleClickOutside);
    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  });
</script>

<div class="dropdown pb-2" bind:this={dropdown}>
  <button on:click={() => (isOpen = !isOpen)}>
    {#if !selectedOption}
      {currentModule.moduleName} <span class="bi-chevron-down small" style="margin-left: auto;"></span>
    {:else}
      {selectedOption.moduleName} <span class="bi-chevron-down small" style="margin-left: auto;"></span>
    {/if}
  </button>
  <div class={`dropdown-content ${isOpen ? 'show' : ''}`}>
    {#each options as option}
      <!-- svelte-ignore a11y-click-events-have-key-events -->
      <!-- svelte-ignore a11y-no-static-element-interactions -->
      <!-- svelte-ignore a11y-missing-attribute -->
      <a class="option" on:click={() => selectOption(option)}>
        {option.moduleName}
      </a>
    {/each}
  </div>
</div>

<style>
  .dropdown {
    width: 100%;
    position: relative;
    display: inline-block;
    font-family: sans-serif;
    font-size: 17px;
  }

  .dropdown button {
    display: flex;
    justify-content: space-between;
    align-items: center;

    background-color: #ffffff;
    color: black;
    padding: 8px 12px;
    border: 1px solid #cfcfcf;
    border-radius: 4px;
    cursor: pointer;
    width: 100%;
    text-align: left;
  }

  .dropdown button:after {
    /* content: '▼'; */
    float: right;
  }

  .dropdown-content {
    width: 100%;
    display: none;
    position: absolute;
    background-color: #f9f9f9;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
    z-index: 1;
    border-radius: 4px;
    overflow-y: auto;
    max-height: 232px;
  }

  .dropdown-content.show {
    display: block;
  }

  .option {
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    display: block;
    white-space: nowrap;
  }

  .option:hover {
    background-color: #f1f1f1;
  }

  .bi-chevron-down {
    font-size: 80%;
    font-weight: 800;
  }
</style>
