<script>
  import Header from '$root/lib/components/InternHeader.svelte';
  import * as config from '$lib/config';
  import { page } from '$app/stores';

  export let data;
</script>

<svelte:head>
  <title>{config.title} - {$page.data.title}</title>
</svelte:head>

<Header />

<main class="container">
  <slot />
</main>
