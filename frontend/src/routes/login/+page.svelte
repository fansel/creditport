<script>
  import { enhance } from '$app/forms';
  import { onMount } from 'svelte';
  import { failureToast } from '$lib/toast';

  /** @type {import('./$types').ActionData} */
  export let form;

  $: if (form?.success == false) {
    failureToast(form?.errorMsg);
  }
</script>

<div class="mt-5 d-flex justify-content-center">
  <form data-bitwarden-watching="1" class="border rounded-3 p-4 login-form" method="POST" use:enhance>
    <h1 class="display-6 fw-bold mb-4 text-center">Einloggen</h1>

    <div class="mb-3">
      <label for="floatingInput">Nutzername</label>
      <input type="text" class="form-control mt-2 {form?.success == false ? 'is-invalid' : ''}" id="floatingInput" name="username" required />
    </div>

    <div class="mb-3">
      <label for="floatingPassword">Password</label>
      <input type="password" class="form-control mt-2 {form?.success == false ? 'is-invalid' : ''}" id="floatingPassword" name="password" required />
    </div>

    <div class="form-check text-start mt-4 mb-3">
      <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault" />
      <label class="form-check-label" for="flexCheckDefault"> eingeloggt bleiben </label>
    </div>
    <button class="btn btn-primary py-2 w-100 my-3" type="submit">Einloggen</button>
  </form>
</div>

<style>
  .login-form {
    min-width: 30rem;
  }

  @media screen and (max-width: 768px) {
    .login-form {
      min-width: 100%;
      margin-left: 1rem;
      margin-right: 1rem;
    }
  }
</style>
