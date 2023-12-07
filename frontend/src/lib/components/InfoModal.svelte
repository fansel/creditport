<script>
  export let showModal; // boolean

  let dialog; // HTMLDialogElement

  $: if (dialog && showModal) dialog.showModal();
</script>

<!-- svelte-ignore a11y-click-events-have-key-events a11y-no-noninteractive-element-interactions -->
<dialog bind:this={dialog} on:close={() => (showModal = false)}>
  <div class="header border-bottom">
    <slot name="headline" />
    <button class="btn-close" type="button" aria-label="Close" on:click={() => dialog.close()} />
  </div>
  <slot name="body" />
  <slot name="footer" />
</dialog>

<style>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  dialog {
    border: none;
    min-width: 50rem;
    border-radius: 10px;
    position: relative;
    padding: 0;
  }

  @media screen and (max-width: 450px) {
    dialog {
      min-width: calc(100vw - 3rem);
    }
  }

  dialog::backdrop {
    background: rgba(0, 0, 0, 0.3);
  }
  dialog > div {
    padding: 1em;
  }
  dialog[open] {
    animation: zoom 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
  }
  @keyframes zoom {
    from {
      transform: scale(0.95);
    }
    to {
      transform: scale(1);
    }
  }
  dialog[open]::backdrop {
    animation: fade 0.2s ease-out;
  }
  @keyframes fade {
    from {
      opacity: 0;
    }
    to {
      opacity: 1;
    }
  }
  button {
    display: block;
  }
</style>
