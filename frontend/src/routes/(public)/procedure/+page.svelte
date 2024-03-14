<script>
  import { onMount } from 'svelte';
  //import { enhance } from '$app/forms';
  import { page } from '$app/stores';
  import { tabProcedureForm } from '$lib/stores';
 // import { superForm } from 'sveltekit-superforms';


  //const { form } = superForm(data.form);
  export let data;
  export let form;


  //components
  import General from './general.svelte';
  import Tabs from './tabs.svelte';
  import Module from './module.svelte';
  import Send from './send.svelte';

  $: modules = data.modules;
  $: universities = data.universities;
  $: courses = data.courses;
  $: uniId = form?.uniId;
  $: uniName = form?.uniName;
  console.log(uniId+" page.svelte");
  

  // $: console.log($tabProcedureForm);

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
    universityId: '',
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
    //addModuleToRequest(0)
    //addTitleToRequest(0)
  });
 
  
  function addRequest() {
    requests = [
      ...requests,
      {
        moduleData: {
          selectedInternalModulIds:[0],
          selectedExternalModulIds: [null] // Ein Array für die Titel
        }
      }
    ];
  }

  function addTitleToRequest(requestIndex) {
    requests[requestIndex].moduleData.selectedExternalModulIds = [
      ...requests[requestIndex].moduleData.selectedExternalModulIds,
      null // Füge einen weiteren Titel hinzu (initial null, kann nach Bedarf angepasst werden)
    ];
  }

  function addModuleToRequest(requestIndex) {
    requests[requestIndex].moduleData.selectedModul = [
      ...requests[requestIndex].moduleData.selectedModul,
      0 // Füge einen weiteren selectedModul hinzu (initial 0, kann nach Bedarf angepasst werden)
    ];
  }

  function removeTitleFromRequest(requestIndex, titleIndex) {
    const request = requests[requestIndex];
    const titles = [...request.moduleData.selectedExternalModulIds];
    titles.splice(titleIndex, 1);
    requests[requestIndex].moduleData.selectedExternalModulIds = titles;
  }

  function removeModuleFromRequest(requestIndex, moduleIndex) {
    const request = requests[requestIndex];
    const selectedModuls = [...request.moduleData.selectedModul];
    selectedModuls.splice(moduleIndex, 1);
    requests[requestIndex].moduleData.selectedModul = selectedModuls;
  }
  function removeRequest(index) {
    if (requests.length > 1) requests = requests.filter((_, i) => i !== index);
  }
</script>

<main>
  <Tabs {activeTab} {switchTab} />
  <hr />

  <div class={activeTab == 'pills-general' ? '' : 'visually-hidden'}>
    <General bind:generalData {goToNextTab} {uniId} {uniName} {courses} {universities} />
  </div>

  <div class={activeTab == 'pills-module' ? '' : 'visually-hidden'}>
    <Module
      bind:modulesForm
      {removeModuleFromRequest}
      {removeTitleFromRequest}
      {addTitleToRequest}
      {addModuleToRequest}
      {requests}
      {removeRequest}
      bind:generalData
      {modules}
      
      {goToNextTab}
      {addRequest}
    />
  </div>

  <div class={activeTab == 'pills-send' ? '' : 'visually-hidden'}>
    <Send bind:modulesForm />
  </div>
</main>
