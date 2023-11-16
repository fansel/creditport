<script>
  import { writable } from 'svelte/store';

  const showPopup = writable(false);

  function togglePopup() {
    showPopup.update((current) => !current);
  }

  let test_data = [
    {
      old_uni: 'Uni Halle',
      old_sub: 'Angewandte Informatik',
      module: 'Mathe I',
      cp: '5',
      lpz_module: 'Analysis I'
    }
  ]
</script>

<div class="container d-flex p-5">
  <!-- popup button -->
  <button on:click={togglePopup} class="btn btn-sm btn-primary btn-group-right"><i class="bi bi-pencil-square" /></button>
</div>


{#if $showPopup}
  <div class="popup card card-body">
    <!-- pop up content -->
    <div class="container">
      <div class="row">
        <div class="col">Bisherige Universität</div>
        <div class="col">Bisheriger Studiengang</div>

        <div class="w-100"></div>

        <div class="col card"></div>
        <div class="col card"></div>

        <div class="w-100"></div>

        <div class="col">Fremdmodul</div>
        <div class="col">Modulvorschlag</div> <!-- Liste aus .json auslesen -->

        <div class="w-100"></div>

        <div class="col card"><input type="text" placeholder="Enter your text here...">
        </div>
        <div class="col card">
          <input type="text" placeholder="Enter your text here...">
        </div>
      </div>
    </div>
    <div class="main-con">
      <div class="left-con">Links</div>
      <div class="right-con">Rechts</div>
    </div>
    

    <div class="card card-body" id="pop-up-cart">
      <p>card element</p>
    </div>
    <p>This is the pop-up content.</p>
    <!-- close button -->
    <button on:click={togglePopup} class="btn-close"></button>
  </div>
{/if}

<style>
  .card{
    margin: 0.5rem;
    margin-top: 0;
  }
  .popup {
    position: center;
    top: 20px;
    left: 50px;
    background-color: white;
    padding: 20px;
    width: 80%;
    height: 80%;
    border: 1px solid #000;
  }
</style>
