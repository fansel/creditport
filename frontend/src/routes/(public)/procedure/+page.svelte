<script>
  import { onMount } from 'svelte';
  import { enhance } from '$app/forms';
  import { page } from '$app/stores';
  export let data;

  //Components
  import General from './general.svelte';
  import Tabs from './tabs.svelte';
  import Module from './module.svelte';
  import Send from './send.svelte';

  const modules = data.modules;

  //Logik zum wechseln des Tabs
  let activeTab = 'pills-general';

  const switchTab = (tab) => {
    activeTab = tab;
  };

  const tabOrder = ['pills-general', 'pills-module', 'pills-send'];

  const goToNextTab = () => {
    if (activeTab == 'pills-general') {
      activeTab = 'pills-module';
    } else if (activeTab == 'pills-module') {
      activeTab = 'pills-send';
    }
  };

  //Logik zum einlesen der Daten aus den allgemeinen Angaben
  let generalData = {
    university: '',
    externalCourseName: '',
    internalCouseName: '',
    annotation:'',
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

<main>
  <Tabs {activeTab} {switchTab} />

  <div class={activeTab == 'pills-general' ? '' : 'visually-hidden'}>
    <General bind:generalData {goToNextTab} />
  </div>

  <div class={activeTab == 'pills-module' ? '' : 'visually-hidden'}>
    <Module bind:modulesForm {requests} {removeRequest} bind:generalData {modules} {activeTab} {goToNextTab} {addRequest} />
  </div>

  <div class={activeTab == 'pills-send' ? '' : 'visually-hidden'}>
    <Send bind:modulesForm />
  </div>

  <!-- <hr />
<button type="button" class="btn btn-primary" on:click={goToNextTab} >Weiter</button> -->
</main>
