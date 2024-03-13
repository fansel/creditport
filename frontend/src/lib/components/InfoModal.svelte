<script>
  export let showModal; // boolean

  let dialog; // HTMLDialogElement

  $: if (dialog && showModal) dialog.showModal();
</script>

<!-- svelte-ignore a11y-click-events-have-key-events a11y-no-noninteractive-element-interactions -->
<dialog bind:this={dialog} on:close={() => (showModal = false)}>
  <div class="dialog-header border-bottom">
    <slot name="headline" />
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog.close()} />
  </div>
  <slot name="body" />
  <slot name="footer" />
</dialog>