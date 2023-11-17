<script>
  import TabGeneral from './TabGeneral.svelte';
  import TabNotifcations from './TabNotifcations.svelte';
  import TabUsermanagment from './TabUsermanagment.svelte';

  let activeSlide = 0;

  $: console.log(activeSlide);
</script>

<div class="row py-3 mb-3 pb-md-1 border-bottom align-items-center">
  <div class="col-md">
    <h1>Einstellungen</h1>
  </div>
</div>

<ul class="nav nav-tabs border-left border-right">
  <li class="nav-item">
    <button
      class="nav-link {activeSlide == 0 ? 'active' : ''}"
      on:click={() => {
        activeSlide = 0;
      }}>Allgemein</button
    >
  </li>
  <li class="nav-item">
    <button
      class="nav-link {activeSlide == 1 ? 'active' : ''}"
      on:click={() => {
        activeSlide = 1;
      }}>Benachrichtigungen</button
    >
  </li>
  <li class="nav-item">
    <button
      class="nav-link {activeSlide == 2 ? 'active' : ''}"
      on:click={() => {
        activeSlide = 2;
      }}>Benutzer & Rollen</button
    >
  </li>
</ul>

<!-- Einstellungs INHALT -->
<div class="tab-content border-left border-right">
  {#if activeSlide === 0}
    <TabGeneral />
  {:else if activeSlide === 1}
    <TabNotifcations />
  {:else if activeSlide === 2}
    <TabUsermanagment />
  {/if}
</div>

<style>
  .tab-content {
    border-left: 1px solid #ddd;
    border-right: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
    padding: 10px;
  }

  .nav-item {
    font-size: 1.25em;
  }
</style>
