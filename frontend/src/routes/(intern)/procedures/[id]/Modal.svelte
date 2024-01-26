<script>
  export let showModal; // boolean

  let dialog; // HTMLDialogElement

  $: if (dialog && showModal) dialog.showModal();
</script>

<dialog bind:this={dialog} on:close={() => (showModal = false)} on:click|self={() => dialog.close()} width="100%">
  <!-- svelte-ignore a11y-no-static-element-interactions -->
  <div on:click|stopPropagation>
    <slot />
    <!-- svelte-ignore a11y-autofocus -->
    <!-- <button autofocus on:click={() => dialog.close()}>close modal</button> -->
  </div>
</dialog>

<style>
  dialog {
    min-width: 90rem;
    max-width: 100rem;
    width: max-content;
  }
</style>
