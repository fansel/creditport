<script>
  import Modal from './Modal.svelte';

  let showModal = false;

  let university = 'Universität Halle';
  let course_of_study = 'Angewandte Informatik';
  const data = [
    {
      module_old: 'Mathe I',
      module_new: 'Analysis',
      cp: '10',
      link: 'www.uni-halle.de/informatik/xyz',
      desc_old: 'Modulbeschreibung des Fachs Mathe I...',
      desc_new: 'Modulbeschreibung des Fachs Analysis...'
    },
    {
      module_old: 'Mathe II',
      module_new: 'Lineare Algebra',
      cp: '10',
      link: 'www.uni-halle.de/informatik/xyz',
      desc_old: 'Modulbeschreibung des Fachs Mathe I...',
      desc_new: `Modulbeschreibung des Fachs Analysis...
      Angenommen wir haetten ganz viel Text: Wie wuerde das ausschauen?
      und noch mehr viel Text und noch mehr viel Text und noch mehr viel Text
      und noch mehr viel Text und noch mehr viel Text und noch mehr viel Text`
    },
    {
      module_old: 'Diskrete Strukturen',
      module_new: 'Diskrete Strukturen',
      cp: '5',
      link: 'www.uni-halle.de/informatik/xyz',
      desc_old: 'Modulbeschreibung des Fachs...',
      desc_new: 'Modulbeschreibung des Fachs...'
    }
  ];
</script>

<button on:click={() => (showModal = true)} class="btn btn-sm btn-primary btn-group-right"><i class="bi bi-pencil-square" /></button>

<Modal bind:showModal>
  <div class="card w-100 mb-3">
    <div class="card-header fw-bold" id="main-header">
      <div class="row">
        <div class="col">{university} <i class="bi bi-pencil-square" /></div>
        <div class="col">{course_of_study} <i class="bi bi-pencil-square" /></div>
      </div>
    </div>
    <!-- BODY -->
    <div class="card-body">
      {#each data as data}
        <div class="row">
          <div class="col-6">
            <!-- old -->
            <div class="row">
              <p><strong>Fremdmodul:</strong> {data.module_old} <i class="bi bi-pencil-square" /></p>
              <p>{data.desc_old}</p>
            </div>
          </div>
          <div class="col-6">
            <div class="row">
              <p><strong>Modul:</strong> {data.module_new} <i class="bi bi-pencil-square" /></p>
              <p>{data.desc_new}</p>
            </div>
          </div>
        </div>
        <hr />
      {/each}
    </div>
  </div>
</Modal>

<style>
  .bi {
    color: blue;
  }
</style>
