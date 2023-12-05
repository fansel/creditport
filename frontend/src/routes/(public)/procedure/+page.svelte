<script>
  export let data;

  const modules = data.modules;



//Logik zum wechseln des Tabs
 let activeTab= "pills-general";

    const switchTab = (tab) => {
        activeTab = tab;
    };

    const tabOrder = ['pills-general', 'pills-module', 'pills-send'];
    let currentTabIndex = 0;

    const goToNextTab = () => {
        currentTabIndex = (currentTabIndex + 1) % tabOrder.length;
        activeTab = tabOrder[currentTabIndex];
    };

//Logik zum einlesen der Daten aus Formularen
  let generalData = {
      uni: '',
      formerStudies: '',
      futureStudies:'',
    };
//Logik für Anträge
  let requests = [];

  function addRequest(){
    requests = [...requests, {
        moduleData: {
          selectedModul: 0,
        },
    }];
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
      aria-selected="{activeTab === 'pills-general'}"
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
      aria-selected="{activeTab === 'pills-module'}"
      >
      <div class="rounded-circle border-3 border border-primary status-circle {activeTab === 'pills-module' ? 'active' : ''}">
        <span>2</span>
      </div>

      Modulanträge
    </button>
  </li>

  <li class="status-item" role="presentation">
    <button class="status-button fw-bold text-primary" 
            on:click={() => switchTab('pills-send')}
            id="pills-send-tab" 
            data-bs-toggle="pill" 
            data-bs-target="#pills-send" 
            type="button" 
            role="tab" 
            aria-controls="pills-send" 
            aria-selected="false">
      <div class="rounded-circle border-3 border border-primary status-circle {activeTab === 'pills-send' ? 'active' : ''}">
        <span>3</span>
      </div>

      Zusammenfassung
    </button>
  </li>
</ul>

{#if activeTab === 'pills-general'}
<form action="/?add" method="POST">
  <div class="row">
    <div class="col-md">
      <div class="mb-3">
        <label for="uni" class="mb-2">Universität der anzurechnenden Module </label>
        <input type="text" id="uni" bind:value={generalData.uni} class="form-control" placeholder="Universität Bremen" />
      </div>
      <div class="mb-3">
        <label for="" class="mb-2">Studiengang der anzurechnenden Module</label>
        <input type="text" id="formerStudies" bind:value={generalData.formerStudies} class="form-control" placeholder="B.Sc. Informatik"/>
      </div>
      <div class="mb-3">
        <label for="" class="mb-2">Studiengang der Universität Leipzig an dem die Anrechnung erfolgen soll</label>
        <input type="text" id="futureStudies" bind:value={generalData.futureStudies} class="form-control" placeholder="B.Sc. Wirtschaftsinformatik" />
      </div>
      <div class="form-floating">
        <textarea class="form-control" placeholder="Leave a comment here" id="floatingTextarea" style="height: 100px"></textarea>
        <label for="floatingTextarea">Kommentare</label>
      </div>
    </div>
   
  </div>
 
</form>
{/if}

{#if activeTab === 'pills-module'}
<h1>{generalData.uni}</h1>
<form action="POST" id="requests">
    {#each requests as { moduleData }, index (index) }
     <div class="card w-100 mb-3" key={index}>
        <div class="card-body">

          <div class="row">
            <div class="col-md">
              <h2 class="h4">Fremdmodul</h2>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="" class="mb-2" >Universität</label>
                  <input type="text" class="form-control" value={generalData.uni}>
                </div>
                <div class="col-md-6">
                  <label for="" class="mb-2" >Studiengang</label>
                  <input type="text" class="form-control" value={generalData.formerStudies}>
                </div>
              </div>

              <div class="mb-3">
                <label for="" class="mb-2">Name</label>
                <input type="text" class="form-control" placeholder="Modellierung und Programmierung" />
              </div>

              <div class="row">
                <div class="col-md-8">
                  <div class="mb-3">
                    <label for="" class="mb-2">Website zum Modul</label>
                    <input type="text" class="form-control" placeholder="http://uni-leipzig.de/module_xyz" />
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="mb-3">
                    <label for="" class="mb-2">LP</label>
                    <input type="text" class="form-control" placeholder="5" />
                  </div>
                </div>
              </div>
              <div class="mb-3">
                <label for="formFile" class="form-label">Modulbeschreibung<i class="bi bi-question-circle ms-2" /> </label>
                <input class="form-control" type="file" id="formFile" />
              </div>
            </div>
            <div class="col-md">
              <h2 class="h4">Modul Vorschlag</h2>

              <div class="mb-2">

              </div>
              <div class="mb-3">
                <label for="" class="mb-2">Name</label>
                <select class="form-select" aria-label="Default select example" bind:value={moduleData.selectedModul}>
                  {#each modules as modul, index}
                    <option value={index}>{modul.moduleName}</option>
                  {/each}
                </select>
              </div>

              <p>{modules[moduleData.selectedModul].moduleDescription ?? ''}</p>
            </div>
          </div>
        </div>{activeTab === 'pills-general' ? 'active' : ''}
        <button class="btn btn-primary" on:click={() => removeRequest(index)}>Remove Request</button>
      </div> 
      
  {/each}
      
  
</form>

<div class="w-100 d-inline-flex justify-content-center">
  <button class="btn btn-primary" on:click={addRequest}>
    <i class="bi bi-plus-circle" style="font-size: 2rem;" />
  </button>
</div>
{/if}
<hr />
<button type="button" class="btn btn-primary" on:click={goToNextTab} >Weiter</button>

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
