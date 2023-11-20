<script>
  import { browser } from '$app/environment';
  import { enhance } from '$app/forms';
  import { invalidateAll } from '$app/navigation';

  export let darkMode;
  export let form;

  let useSystemModeEnabled = false;
  let darkModeEnabled;
  let pageCount = 10;
  let systemLanguage = 0;

  $: darkModeEnabled = darkMode === 'dark';

  function setDarkMode(event) {
    const theme = darkModeEnabled ? 'dark' : 'light';
    document.documentElement.setAttribute('data-bs-theme', theme);

    const one_year = 60 * 60 * 24 * 365;
    document.cookie = `theme=${theme}; max-age=${one_year}; path=/`;
  }

  // if (browser) {
  //   if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
  //     // Dark Mode ist aktiviert
  //     console.log('Dark Mode ist aktiviert.');
  //   } else {
  //     // Dark Mode ist deaktiviert
  //     console.log('Dark Mode ist deaktiviert.');
  //   }
  // }
</script>

<h4 class="mb-3">Erscheinungsbild</h4>
<div class="row mb-3">
  <div class="col-md-4 col-form-label"><span>Anzeigesprache</span></div>
  <div class="col">
    <select name="language" id="languageSelect" class="form-select">
      <option value="1">Systemsprache</option>
      <option value="2">Deutsch</option>
      <option value="3">English</option>
    </select>
  </div>
</div>

<div class="row mb-3">
  <div class="col-md-4 col-form-label"><span>Treffer pro Seite</span></div>
  <div class="col">
    <select name="pageCount" id="pageCountSelect" class="form-select">
      <option value="10">10</option>
      <option value="25">25</option>
      <option value="50">50</option>
      <option value="100">100</option>
    </select>
  </div>
</div>

<div class="row mb-3">
  <div class="col-md-4 col-form-label"><span>Dunkler Modus</span></div>
  <div class="col">
    <div class="form-check mb-3">
      <input class="form-check-input" type="checkbox" id="useSystemModeCheckbox" name="useSystemMode" bind:checked={useSystemModeEnabled} />
      <label class="form-check-label" for="useSystemModeCheckbox">Benutze Systemeinstellungen </label>
    </div>

    {#if !useSystemModeEnabled}
      <div class="form-check">
        <input class="form-check-input" type="checkbox" id="darkModeCheckbox" name="darkMode" bind:checked={darkModeEnabled} on:change={setDarkMode} />
        <label class="form-check-label" for="darkModeCheckbox">aktivieren</label>
      </div>
    {/if}
  </div>
</div>

<form action="?/changePassword" method="POST">
  <h4 class="mb-3">Sicherheit</h4>
  <div class="row mb-3">
    <div class="col-md-4 col-form-label"><span>Passwort ändern</span></div>
    <div class="col">
      <div class="mb-3">
        <label for="formAktuellesPassword" class="form-label">Aktuelles Passwort</label>
        <input type="password" class="form-control" id="formAktuellesPassword" name="aktuellesPassword" />
      </div>
      <div class="mb-3">
        <label for="formNeuesPassword" class="form-label">Neues Passwort</label>
        <input type="password" class="form-control" id="formNeuesPassword" name="neuesPassword" />
      </div>
      <button class="btn btn-primary" type="submit">Passwort ändern</button>
    </div>
  </div>
</form>

<button class="btn btn-primary mt-2 mb-2">Speichern</button>
