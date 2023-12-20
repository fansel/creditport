<script>
  let uuid = '';
  let inputSuccess = false;

  let fields = [];


  function clear($event) {
    $event.target.value = '';
  }

  // Handler für das Paste Event
  function onPaste($event) {
    const data = $event.clipboardData.getData('text');
    const value = data.replace(/ /g, '').split('');

    //Prüft ob alle Werte des Zwischenablage Nummern sind
    if (!value.some((number) => !checkNumber(number))) {
      //Der einzufügende Wert muss genau 6 Stellen haben
      if (value.length === fields.length) {
        fields.forEach((field, index) => {
          field.value = value[index];
          submit();
        });
      }
    } else {
      return;
    }
  }

  //Handler für das Event wenn eine Taste im Input Feld gedrückt wird
  function onKeyUp($event) {
    const field = $event.target;
    const value = field.value;
    const index = findIndexInArray(fields, field); //Bestimmt an welcher Stelle der Input steht

    //Wenn die löschen Taste gedrückt wird
    if ($event.key === 'Backspace' && index > 0) {
      field.previousElementSibling.focus();
    }

    if (checkNumber(value)) {
      if (value.length > 0 && index < fields.length - 1) {
        field.nextElementSibling.focus();
      }

      if (field.value !== '' && index === fields.length - 1) {
        submit();
      }
    } else {
      clear($event);
    }
  }

  function submit() {
    //Baue noch einen Check ein ob auch wirklich alle Felder eingetragen sind
    uuid = '';
    fields.forEach((field, index) => {
      uuid += field.value;
      field.disabled = true;
    });

    //Zusätlich sollte serverseitig angefragt werden ob diese uuid überhaupt exisitert

    inputSuccess = true;
  }

  function checkNumber(number) {
    return /[0-9]/g.test(number);
  }

  const findIndexInArray = (array, targetObject) => array.findIndex((item) => item === targetObject);
</script>

<div class="mt-5 d-flex flex-column align-items-center">
  <h1 class="display-4 fw-bold text-body-emphasis">Statusabfrage</h1>
  <p class="lead">Gib deine Vorgangsnummer an und erhalte alle Infos zum aktuellen Bearbeitungsstand deines Vorgangs.</p>

  <div class="mt p-4">
    <div class="digit-field mb-4">
      {#each [1, 2, 3, 4, 5, 6] as nb, index}
        <input type="text" class="digit" maxlength="1" bind:this={fields[index]} on:focus={clear} on:keydown={clear} on:paste={onPaste} on:keyup={onKeyUp} />
      {/each}
    </div>
  </div>
  <a class="btn btn-primary {inputSuccess ? '' : 'disabled'}" href="/status/{uuid}">Status abfragen</a>
</div>

<style>
  .digit-field {
    display: flex;
    flex-direction: row;
  }

  .digit {
    width: 3.9rem;
    padding: 1rem;
    font-size: 1.5rem;
    border: none;
    outline: 1px solid #6c757d;
    margin: 0 1rem 0 0;
    background-color: white;
    text-align: center;
    border-radius: 0.5rem;
    transition: all 150ms ease-in-out;
  }

  .digit:focus {
    color: blue;
    outline: 0.3rem solid #0d6efd;
  }

  .digit:last-child {
    margin: 0 0 0 0;
  }

  .digit:nth-child(3) {
    margin: 0 3rem 0 0;
  }

  .digit:disabled {
    opacity: 0.3;
  }

  @media screen and (max-width: 768px) {
    .digit {
      width: 2.7rem;
      padding: 0.75rem;
      font-size: 1rem;
      margin: 0 0.5rem 0 0;
    }
    .digit:nth-child(3) {
      margin: 0 1.25rem 0 0;
    }
  }
</style>
