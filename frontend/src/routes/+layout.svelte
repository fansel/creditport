<script>
  import { SvelteToast } from '@zerodevx/svelte-toast';

  const options = {
    duration: 4000,
    initial: 1,
    theme: {
      '--toastBorderRadius': '0.375rem',
      '--toastBarBackground': '#0d6efd',
      '--toastBorder': '1px solid rgba(0, 0, 0, 0.175)'
    },
    classes: [],
    dismissable: true
  };
</script>

<SvelteToast {options} />

<slot />
