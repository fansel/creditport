<script>
  import Header from '$root/lib/components/PublicHeader.svelte';
  import * as config from '$lib/config';
  import { page } from '$app/stores';
</script>

<svelte:head>
  <title>{config.title} - {$page.data.title}</title>
</svelte:head>

<Header />

<main class="container">
  <slot />
</main>
