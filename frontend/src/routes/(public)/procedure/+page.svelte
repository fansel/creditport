<script>
  import { onMount } from 'svelte';
  import { enhance } from '$app/forms';
  import { page } from '$app/stores';
  import { tabProcedureForm } from '$lib/stores';

  export let data;

  //Components
  import General from './general.svelte';
  import Tabs from './tabs.svelte';
  import Module from './module.svelte';
  import Send from './send.svelte';

  $: modules = data.modules;

  $: console.log($tabProcedureForm);

  //Logik zum wechseln des Tabs
  const tabOrder = ['pills-general', 'pills-module', 'pills-send'];
  let activeTab = tabOrder[$tabProcedureForm];

  // $: if ($page.form?.success) {
  //   switchTab('pills-send');
  // }

  const switchTab = (tab) => {
    activeTab = tab;
    $tabProcedureForm = tabOrder.indexOf(tab);
  };

  const goToNextTab = () => {
    if ($tabProcedureForm < 2) {
      $tabProcedureForm++;
      activeTab = tabOrder[$tabProcedureForm];
    }
    // if (activeTab == 'pills-general') {
    //   activeTab = 'pills-module';
    // } else if (activeTab == 'pills-module') {
    //   activeTab = 'pills-send';
    // }
  };

  //Logik zum einlesen der Daten aus den allgemeinen Angaben
  let generalData = {
    university: '',
    externalCourseName: '',
    internalCouseName: '',
    annotation: ''
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
          selectedModul: 0,
          title: null
        }
      }
    ];
  }

  function removeRequest(index) {
    if (requests.length > 1) requests = requests.filter((_, i) => i !== index);
  }
</script>

<main>
  <Tabs {activeTab} {switchTab} />
  <hr />

  <div class={activeTab == 'pills-general' ? '' : 'visually-hidden'}>
    <General bind:generalData {goToNextTab} />
  </div>

  <div class={activeTab == 'pills-module' ? '' : 'visually-hidden'}>
    <Module bind:modulesForm {requests} {removeRequest} bind:generalData {modules} {activeTab} {goToNextTab} {addRequest} />
  </div>

  <div class={activeTab == 'pills-send' ? '' : 'visually-hidden'}>
    <Send bind:modulesForm />
  </div>
</main>
