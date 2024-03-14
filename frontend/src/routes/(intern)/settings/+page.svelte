<script>
  import { browser } from '$app/environment';
  import { invalidateAll } from '$app/navigation';
  import { change_password_schema } from '$root/lib/schema.js';
  import { successToast } from '$root/lib/toast';
  import { superForm } from 'sveltekit-superforms/client';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';

  export let data;

  let useSystemModeEnabled = data.settings.useSystemMode === 'true';
  let darkModeEnabled = data.settings.darkMode === 'dark';
  let pageCount = String(data.settings.pageCount);
  let systemLanguage = 0;

  // $: darkModeEnabled = settings.darkMode === 'dark';
  // $: useSystemModeEnabled =

  function setDarkMode() {
    const theme = darkModeEnabled ? 'dark' : 'light';
    document.documentElement.setAttribute('data-bs-theme', theme);
  }

  function setFromSystemMode() {
    if (browser) {
      if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
        darkModeEnabled = true;
        setDarkMode();
      } else {
        darkModeEnabled = false;
        setDarkMode();
      }
    }
  }

  const { form, errors, enhance } = superForm(data.changePasswordForm, {
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    validators: zod(change_password_schema),
    validationMethod: 'auto',
    customValidity: false
  });

  async function submitSettings(event) {
    const data = {
      darkMode: darkModeEnabled,
      useSystemMode: useSystemModeEnabled,
      pageCount: pageCount
    };

    const response = await fetch('', {
      method: 'POST',
      body: JSON.stringify(data),
      headers: {
        'Content-Type': 'application/json'
      }
    });
    const res = await response.json();

    if (res.success) {
      successToast('Erfolreich gespeichert');
    } else {
      failureToast('Leider ist ein Fehler aufgetreten');
    }

    await invalidateAll();
  }
</script>

<h4 class="mb-3">Erscheinungsbild</h4>
<div class="row mb-3">
  <div class="col-md-4 col-form-label"><span>Anzeigesprache</span></div>
  <div class="col">
    <select name="language" id="languageSelect" class="form-select">
      <option value="1">Deutsch</option>
      <option value="2">English</option>
    </select>
  </div>
</div>

<div class="row mb-3">
  <div class="col-md-4 col-form-label"><span>Zeitzone</span></div>
  <div class="col">
    <select name="timezone" id="timezoneSelect" class="form-select">
      <option value="1">Europe/Berlin</option>
      <option value="2">UTC</option>
    </select>
  </div>
</div>

<div class="row mb-3">
  <div class="col-md-4 col-form-label"><span>Treffer pro Seite</span></div>
  <div class="col">
    <select name="pageCount" id="pageCountSelect" class="form-select" bind:value={pageCount}>
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
      <input class="form-check-input" type="checkbox" id="useSystemModeCheckbox" name="useSystemMode" bind:checked={useSystemModeEnabled} on:change={setFromSystemMode} />
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

<form action="?/changePassword" method="POST" use:enhance>
  <h4 class="mb-3">Sicherheit</h4>
  <div class="row mb-3">
    <div class="col-md-4 col-form-label"><span>Passwort ändern</span></div>
    <div class="col">
      <div class="mb-3">
        <label for="formAktuellesPassword" class="form-label">Passwort</label>
        <input type="password" name="password" placeholder="" class="form-control {$errors.password ? 'is-invalid' : ''}" bind:value={$form.password} />
        {#if $errors.password}
          <div class="invalid-feedback">{$errors.password}</div>
        {/if}
      </div>
      <div class="mb-3">
        <label for="formNeuesPassword" class="form-label">Wiederhole Passwort</label>
        <input type="password" name="confirm_password" placeholder="" class="form-control {$errors.confirm_password ? 'is-invalid' : ''}" bind:value={$form.confirm_password} />
        {#if $errors.confirm_password}
          <div class="invalid-feedback">{$errors.confirm_password}</div>
        {/if}
      </div>
      <button class="btn btn-primary" type="submit">Passwort ändern</button>
    </div>
  </div>
</form>

<button class="btn btn-primary mt-2 mb-2" on:click={submitSettings}>Speichern</button>
