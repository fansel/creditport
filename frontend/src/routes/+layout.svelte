<script>
  import { page } from '$app/stores';
  // import { SvelteToast } from '@zerodevx/svelte-toast';

  // const options = {
  //   duration: 4000,
  //   initial: 1,
  //   theme: {
  //     '--toastBorderRadius': '0.375rem',
  //     '--toastBarBackground': '#0d6efd',
  //     '--toastBorder': '1px solid rgba(0, 0, 0, 0.175)'
  //   },
  //   classes: [],
  //   dismissable: true
  // };

  // https://github.com/ciscoheat/sveltekit-flash-message
  import { getFlash } from 'sveltekit-flash-message';
  const flash = getFlash(page);

  // https://svelte-french-toast.com/
  import toast, { Toaster } from 'svelte-french-toast';

  // Simple Toasts TODO: style and integrate in theme
  $: if ($flash) {
    switch ($flash.type) {
      case 'success':
        toast.success($flash.message);
        break;
      case 'error':
        toast.error($flash.message);
        break;
    }

    // Clear the flash message to avoid double-toasting.
    $flash = undefined;
  }
</script>

<Toaster />
<!-- <SvelteToast {options} /> -->

<slot />
