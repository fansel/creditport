<script>
    import Header from "$lib/components/Header.svelte";
    import * as Config from "$lib/config";
</script>

<svelte:head>
<title>{ Config.title }</title>
</svelte:head>

<Header />

<main class="container">
    <slot />
</main>