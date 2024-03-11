<script>
  import { superForm } from 'sveltekit-superforms';
  import * as flashModule from 'sveltekit-flash-message/client';
  import { zod } from 'sveltekit-superforms/adapters';
  import { login_schema } from '$lib/schema';

  export let data;

  // Superform Client API:
  const { form, errors, enhance, message } = superForm(data.form, {
    syncFlashMessage: true,
    flashMessage: {
      module: flashModule
    },
    onError({ result }) {
      console.error(result.error.message);
    },
    resetForm: false,
    validators: zod(login_schema),
    validationMethod: 'auto',
    customValidity: false
  });
</script>

<div class="mt-5 d-flex justify-content-center mx-3">
  <form data-bitwarden-watching="1" class="border rounded-3 p-4 login-form" method="POST" action="?/login" use:enhance>
    <h1 class="display-6 fw-bold mb-4 text-center">Einloggen</h1>

    <div class="mb-3">
      <label for="floatingInput">Nutzername</label>
      <input
        type="text"
        class="form-control mt-2 {$errors.username ? 'is-invalid' : ''}"
        id="floatingInput"
        name="username"
        bind:value={$form.username}
        aria-invalid={$errors.username ? 'true' : undefined}
      />
      {#if $errors.username}<div class="invalid-feedback">{$errors.username}</div>{/if}
    </div>

    <div class="mb-3">
      <label for="floatingPassword">Password</label>
      <input
        type="password"
        class="form-control mt-2 {$errors.password ? 'is-invalid' : ''}"
        id="floatingPassword"
        name="password"
        bind:value={$form.password}
        aria-invalid={$errors.password ? 'true' : undefined}
      />
      {#if $errors.password}<div class="invalid-feedback">{$errors.password}</div>{/if}
    </div>

    <!-- <div class="form-check text-start mt-4 mb-3">
      <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault" />
      <label class="form-check-label" for="flexCheckDefault"> eingeloggt bleiben </label>
    </div> -->
    <button class="btn btn-primary py-2 w-100 my-3" type="submit">Einloggen</button>
  </form>
</div>

<style>
  .invalid-feedback {
    display: block;
  }
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
