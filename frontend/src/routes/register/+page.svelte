<script>
  import { enhance } from '$app/forms';
</script>

<div class="mt-5 d-flex justify-content-center">
  <form data-bitwarden-watching="1" class="border rounded-3 p-4 login-form" use:enhance method="POST">
    <h1 class="display-6 fw-bold mb-4 text-center">Registrierung</h1>

    <div class="mb-3">
      <label for="floatingInput">Nutzername</label>
      <input type="text" class="form-control mt-2" id="floatingInput" placeholder="admin" name="username" />
    </div>

    <div class="mb-3">
      <label for="floatingPassword">Password</label>
      <input type="password" class="form-control mt-2" id="floatingPassword" placeholder="password" name="password" />
    </div>

    <button class="btn btn-primary py-2 w-100 my-3" type="submit">Registrieren</button>
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
