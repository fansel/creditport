<script>
  export let switchTab;
  export let activeTab;
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
