<script>
  import { page } from '$app/stores';
  import * as config from '$lib/config.js';
  export let data;
</script>

<div class="row py-3 mb-3 pb-md-1 border-bottom align-items-center">
  <div class="col-md">
    <h1>Einstellungen</h1>
  </div>
</div>

<ul class="nav nav-tabs border-left border-right">
  <li class="nav-item">
    <a href="/settings/" class="nav-link {$page.data.subtitle == 'Allgemein' ? 'active' : ''}">Allgemein</a>
  </li>
  <!-- <li class="nav-item">
    <a href="/settings/notifications" class="nav-link {$page.data.subtitle == 'Benachrichtigungen' ? 'active' : ''}">Benachrichtigungen</a>
  </li> -->

  {#if $page.data.user.role == config.user_roles.ADMIN}
    <li class="nav-item">
      <a href="/settings/usermanagment" class="nav-link {$page.data.subtitle == 'Benutzer' ? 'active' : ''}">Benutzer</a>
    </li>

    <li class="nav-item">
      <a href="/settings/universities" class="nav-link {$page.data.subtitle == 'Universitäten' ? 'active' : ''}">Universitäten</a>
    </li>

    <li class="nav-item">
      <a href="/settings/courses" class="nav-link {$page.data.subtitle == 'Studiengänge' ? 'active' : ''}">Studiengänge</a>
    </li>
  {/if}
</ul>

<!-- Einstellungs INHALT -->
<div class="tab-content border-start border-end p-3 border-bottom mb-3 shadow-sm">
  <slot />
</div>

<style>
  .nav-item {
    font-size: 1rem;
  }
</style>
