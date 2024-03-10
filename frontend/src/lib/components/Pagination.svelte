<!-- <div class="col-8 d-flex flex-row-reverse">
  <nav aria-label="...">
    <ul class="pagination pagination-sm">
      <li class="page-item"><button class="page-link" on:click={setSkip(currentPage - 1)} aria-label="Previous"><span aria-hidden="true">&laquo;</span></button></li>

      {#each Array(totalPages) as _, idx}
      <li 
        class={currentPage === idx ? 'page-item active' : 'page-item' }
      >
        <button class="page-link" on:click={() => setSkip(idx)} >{idx + 1}</button>
      </li>
      {/each}
      <li class="page-item"><button class="page-link" on:click={setSkip(currentPage + 1)} aria-label="Next"><span aria-hidden="true">&raquo;</span></button></li>
    </ul>
  </nav>
</div> -->