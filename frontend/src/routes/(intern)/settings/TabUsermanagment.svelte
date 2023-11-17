<script>
  export let users;
</script>

<div class="">
  <table class="table table-striped">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Username</th>
        <th scope="col">Rolle</th>
      </tr>
    </thead>
    <tbody>
      {#each users as user, index}
        <tr>
          <th scope="row">{user.userId}</th>
          <td>{user.username}</td>
          <td>
            <span class="badge bg-success-subtle border border-success-subtle text-secondary-emphasis rounded-pill">{user.role}</span>
          </td>
        </tr>
      {/each}
    </tbody>
  </table>
</div>
