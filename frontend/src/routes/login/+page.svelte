<div class="mt-5 d-flex justify-content-center">
  <form data-bitwarden-watching="1" class="border rounded-3 p-4 login-form">
    <h1 class="display-5 fw-bold mb-3 text-center">Login</h1>

    <div class="mb-3">
      <label for="floatingInput">Email address</label>
      <input type="email" class="form-control mt-2" id="floatingInput" placeholder="name@example.com" />
    </div>

    <div class="mb-3">
      <label for="floatingPassword">Password</label>
      <input type="password" class="form-control mt-2" id="floatingPassword" placeholder="Password" />
    </div>

    <div class="form-check text-start my-3">
      <input class="form-check-input" type="checkbox" value="remember-me" id="flexCheckDefault" />
      <label class="form-check-label" for="flexCheckDefault"> eingeloggt bleiben </label>
    </div>
    <button class="btn btn-primary py-2 w-100 mb-3" type="submit">Einloggen</button>
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
