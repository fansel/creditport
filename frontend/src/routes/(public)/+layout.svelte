<script>
  import Header from '$root/lib/components/PublicHeader.svelte';
  import * as config from '$lib/config';
  import Footer from '$lib/components/Footer.svelte';

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

<Footer status={data.status} />
