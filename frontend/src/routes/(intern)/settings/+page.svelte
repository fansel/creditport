<script>
  import TabGeneral from './TabGeneral.svelte';
  import TabNotifcations from './TabNotifcations.svelte';
  import TabUsermanagment from './TabUsermanagment.svelte';
  import TabUniversity from './TabUniversity.svelte';

  export let data;

  let activeSlide = 0;
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

  {#if data.user.username === 'admin'}
    <li class="nav-item">
      <button
        class="nav-link {activeSlide == 2 ? 'active' : ''}"
        on:click={() => {
          activeSlide = 2;
        }}>Benutzer & Rollen</button
      >
    </li>

    <li class="nav-item">
      <button
        class="nav-link {activeSlide == 3 ? 'active' : ''}"
        on:click={() => {
          activeSlide = 3;
        }}>Universitäten</button
      >
    </li>
  {/if}
</ul>

<!-- Einstellungs INHALT -->
<div class="tab-content border-start border-end p-3 border-bottom mb-3 shadow-sm">
  {#if activeSlide === 0}
    <TabGeneral />
  {:else if activeSlide === 1}
    <TabNotifcations />
  {:else if activeSlide === 2}
    <TabUsermanagment users={data.users} />
  {:else if activeSlide === 3}
    <TabUniversity universities={data.universities}/>
  {/if}
</div>

<style>
  .nav-item {
    font-size: 1rem;
  }
</style>
