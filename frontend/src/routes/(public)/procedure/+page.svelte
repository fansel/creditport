<script>
  import { onMount } from 'svelte';
  import { enhance } from '$app/forms';
  import { page } from '$app/stores';
  export let data;

  const modules = data.modules;

  //Logik zum wechseln des Tabs
  let activeTab = 'pills-general';

  const switchTab = (tab) => {
    activeTab = tab;
  };

  const tabOrder = ['pills-general', 'pills-module', 'pills-send'];
  let currentTabIndex = 0;

  const goToNextTab = () => {
    currentTabIndex = (currentTabIndex + 1) % tabOrder.length;
    activeTab = tabOrder[currentTabIndex];
  };

  //Logik zum einlesen der Daten aus den allgemeinen Angaben
  let generalData = {
    uni: '',
    formerStudies: '',
    futureStudies: ''
  };
  //Logik für Anträge
  let requests = [];
  let modulesForm;

  onMount(() => {
    // Füge einen initialen Request hinzu
    addRequest();
  });

  function addRequest() {
    requests = [
      ...requests,
      {
        moduleData: {
          selectedModul: 0
        }
      }
    ];
  }

  function removeRequest(index) {
    requests = requests.filter((_, i) => i !== index);
  }
</script>

<ul class="status-list my-3 m-0 p-0" id="pills-tab" role="tablist">
  <li class="status-item" role="presentation">
    <button
      class="status-button fw-bold text-primary"
      on:click={() => switchTab('pills-general')}
      id="pills-general-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-general"
      type="button"
      role="tab"
      aria-controls="pills-general"
      aria-selected={activeTab === 'pills-general'}
    >
      <div class="rounded-circle border-3 border border-primary status-circle {activeTab === 'pills-general' ? 'active' : ''}">
        <span>1</span>
      </div>

      Allgemeine Angaben
    </button>
  </li>

  <li class="status-item" role="presentation">
    <button
      class="status-button fw-bold text-primary"
      on:click={() => switchTab('pills-module')}
      id="pills-module-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-module"
      type="button"
      role="tab"
      aria-controls="pills-module"
      aria-selected={activeTab === 'pills-module'}
    >
      <div class="rounded-circle border-3 border border-primary status-circle {activeTab === 'pills-module' ? 'active' : ''}">
        <span>2</span>
      </div>

      Modulanträge
    </button>
  </li>

  <li class="status-item" role="presentation">
    <button
      class="status-button fw-bold text-primary"
      on:click={() => switchTab('pills-send')}
      id="pills-send-tab"
      data-bs-toggle="pill"
      data-bs-target="#pills-send"
      type="button"
      role="tab"
      aria-controls="pills-send"
      aria-selected="false"
    >
      <div class="rounded-circle border-3 border border-primary status-circle {activeTab === 'pills-send' ? 'active' : ''}">
        <span>3</span>
      </div>

      Zusammenfassung
    </button>
  </li>
</ul>

<!-- Tab General -->
<div class={activeTab == 'pills-general' ? '' : 'visually-hidden'}>
  <!-- <form action="/?add" method="POST"> -->
  <div class="row">
    <div class="col-md">
      <div class="mb-3">
        <label for="uni" class="mb-2">Universität der anzurechnenden Module </label>
        <input type="text" id="uni" bind:value={generalData.uni} class="form-control" placeholder="Universität Bremen" />
      </div>
      <div class="mb-3">
        <label for="" class="mb-2">Studiengang der anzurechnenden Module</label>
        <input type="text" id="formerStudies" bind:value={generalData.formerStudies} class="form-control" placeholder="B.Sc. Informatik" />
      </div>
      <div class="mb-3">
        <label for="" class="mb-2">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
        <input type="text" id="futureStudies" bind:value={generalData.futureStudies} class="form-control" placeholder="B.Sc. Wirtschaftsinformatik" />
      </div>
      <div class="form-floating">
        <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea" style="height: 100px" />
        <label for="floatingTextarea">Kommentare</label>
      </div>
    </div>
  </div>
  <hr />
  <button type="button" class="btn btn-primary" on:click={goToNextTab}>Weiter</button>
  <!-- </form> -->
</div>

<!-- Tab Module -->
<div class={activeTab == 'pills-module' ? '' : 'visually-hidden'}>
  <h1>Überschrift</h1>

  <form action="?/requests" method="POST" enctype="multipart/form-data" use:enhance id="requests" bind:this={modulesForm}>
    <input type="hidden" name="university" value={1} />
    <input type="hidden" name="course" value={1} />
    <input type="hidden" name="modulesCount" bind:value={requests.length} />
    {#each requests as { moduleData }, index (index)}
      <div class="card w-100 mb-3" key={index}>
        <div class="card-body">
          <div class="position-relative">
            <button class="btn btn-primary position-absolute top-0 end-0" on:click={() => removeRequest(index)} type="button">Antrag löschen</button>
          </div>
          <div class="row">
            <div class="col-md">
              <h2 class="h4">Fremdmodul</h2>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="" class="mb-2">Universität</label>
                  <input type="text" class="form-control" name={`fremduni${index}`} value={generalData.uni} />
                </div>
                <div class="col-md-6">
                  <label for="" class="mb-2">Studiengang</label>
                  <input type="text" class="form-control" name={`fremdstudiengang${index}`} value={generalData.formerStudies} />
                </div>
              </div>

              <div class="row">
                <div class="col-md-10">
                  <div class="mb-3">
                    <label for="" class="mb-2">Name des Studiengangs</label>
                    <input type="text" class="form-control" name="fremdstudiengangName" placeholder="Modellierung und Programmierung" />
                  </div>
                </div>
                <div class="col-md-2">
                  <div class="mb-3">
                    <label for="" class="mb-2">LP</label>
                    <input type="text" class="form-control" name="lp" placeholder="5" />
                  </div>
                </div>
              </div>

              <div class="mb-3">
                <label for="formFile" class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
                <input class="form-control" type="file" name="modulbeschreibung" id="formFile" />
              </div>

              <div>
                <div class="mb-3">
                  <label for="" class="mb-2">Website zum Modul</label>
                  <input type="text" class="form-control" name="website" placeholder="http://uni-leipzig.de/module_xyz" />
                </div>
              </div>
            </div>
            <div class="col-md">
              <h2 class="h4">Modul Vorschlag</h2>

              <div class="mb-2" />
              <div class="mb-3">
                <label for="" class="mb-2">Name</label>
                <select class="form-select" name="studiengangUniLeipzig" aria-label="Default select example" bind:value={moduleData.selectedModul}>
                  {#each modules as modul, index}
                    <option value={index}>{modul.moduleName}</option>
                  {/each}
                </select>
              </div>

              <p>{modules[moduleData.selectedModul].moduleDescription ?? ''}</p>
            </div>
          </div>
          <div class="form-floating">
            <textarea class="form-control" name="kommentar" placeholder="Leave a comment here" id="floatingTextarea" style="height: 100px" />
            <label for="floatingTextarea">Kommentare</label>
          </div>
        </div>
        {activeTab === 'pills-general' ? 'active' : ''}
      </div>
    {/each}
    <hr />
    <button type="button" class="btn btn-primary" on:click={goToNextTab}>Weiter</button>

    <div class="w-100 d-inline-flex justify-content-center">
      <button class="btn btn-primary" on:click={addRequest}>
        <i class="bi bi-plus-circle" style="font-size: 2rem;" />
      </button>
    </div>
  </form>
</div>

<!-- Tab Send -->
<div class={activeTab == 'pills-send' ? '' : 'visually-hidden'}>
  <button class="btn btn-primary" on:click={() => modulesForm.submit()}>Senden</button>
</div>

<!-- <hr />
<button type="button" class="btn btn-primary" on:click={goToNextTab} >Weiter</button> -->

<style>
  .status-circle {
    width: 50px;
    height: 50px;

    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.25rem; /* Schriftgröße der Zahl */
    margin-right: 1rem;
  }

  .status-list {
    list-style-type: none;
    display: flex;
    justify-content: space-between;
  }

  .status-circle.active {
    background-color: #0d6efd;
    color: white;
  }

  .status-circle {
    /*background-color: white;*/
    color: #0d6efd;
  }

  .status-button {
    /* background-color: white; */
    background-color: var(--bs-body-bg);
    border: none;
    display: flex;
    align-items: center;
  }

  @media screen and (max-width: 768px) {
    .status-circle {
      font-size: 1rem;
    }

    .status-list {
      flex-direction: column;
    }
  }
</style>
