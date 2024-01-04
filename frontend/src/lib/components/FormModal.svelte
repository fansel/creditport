<script>
  import { enhance } from '$app/forms';
  import { page } from '$app/stores';
  import { invalidateAll, goto } from '$app/navigation';

  export let showModal; // boolean
  export let action;
  export let method = 'POST';
  export let reset = true;
  export let min_width = '50rem';

  let dialog; // HTMLDialogElement
  let form; //HTMLFormElement

  $: if (dialog && showModal) dialog.showModal();

  function handleSubmit() {
    return async ({ update, result }) => {
      await update({ reset });
      if (result.type === 'success') closeDialog();
    };
  }

  export function closeDialog() {
    if (reset) form.reset();
    invalidateAll();
    dialog.close();
  }
</script>

<!-- svelte-ignore a11y-click-events-have-key-events a11y-no-noninteractive-element-interactions -->
<dialog bind:this={dialog} on:close={() => (showModal = false)} style="min-width: {min_width};">
  <div class="header border-bottom">
    <slot name="headline" />
    <button class="btn-close" type="button" aria-label="Close" on:click={() => closeDialog()} />
  </div>
  <form {action} {method} use:enhance={handleSubmit} bind:this={form}>
    <slot name="body" />
    <slot name="footer" />
  </form>
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

  @media screen and (max-width: 1200px) {
    dialog {
      min-width: calc(100vw - 3rem) !important ;
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
