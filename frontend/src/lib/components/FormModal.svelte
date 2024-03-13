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
  <div class="dialog-header border-bottom">
    <slot name="headline" />
    <button class="btn-close" type="button" aria-label="Close" on:click={() => closeDialog()} />
  </div>
  <form {action} {method} use:enhance={handleSubmit} bind:this={form}>
    <slot name="body" />
    <slot name="footer" />
  </form>
</dialog>