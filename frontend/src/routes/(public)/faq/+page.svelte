<script>
  const accordionData = [
    {
      question: 'Was ist der allgemeine Ablauf des Antragsverfahrens?',
      answer: `Die Anerkennung von Studienleistungen von einer anderen Universität oder Fachhochschule an der Universität Leipzig erfolgt nur für Module, die an unserer Universität angeboten werden. Um
        diesen Prozess in Gang zu setzen, benötigen wir von Ihnen sämtliche relevante Unterlagen und Nachweise über die bereits absolvierten Studienleistungen, wie beispielsweise ein Transcript of
        Records und Modulbeschreibungen. Diese Dokumente müssen im PDF-Format vorliegen und werden im späteren Antragsverfahren eingereicht. Eine Kommission wird dann über die Anerkennung Ihrer
        Studienleistungen für eins oder mehrere Module entscheiden.`
    },
    {
      question: 'Warum weichen die Anzahl der Leistungspunkte (LP) für ein Modul an meiner Universität von denen an der Universität Leipzig ab?',
      answer: `Die Unterschiede in der Anzahl der Leistungspunkte (LP) zwischen Ihrer Universität und der Universität Leipzig können verschiedene Gründe haben. An unserer Universität Leipzig sind Module in
        der Regel auf 5 oder 10 Leistungspunkte standardisiert. Es kann jedoch sein, dass ein Modul an Ihrer aktuellen Universität, das viele Inhalte umfasst, an unserer Universität in mehrere
        kleinere Module aufgeteilt wurde, die jeweils 5 oder 10 LP vergeben. Gleichzeitig kann es auch umgekehrt der Fall sein, dass ein Modul an Ihrer Universität, das bei uns als mehrere separate
        Module zählt, in Summe mehr Leistungspunkte aufweist. Unsere Sachbearbeiter und der Ausschuss werden diese Unterschiede bei der Anrechnung berücksichtigen und entsprechend entscheiden.`
    },
    {
      question: 'Woher weiß ich ob ich mein Modul mir anrechnen lassen kann?',
      answer: `Am besten schauen Sie für Ihren neuen Studiengang an der Universität Leipzig, ob es ein Modul gibt mit ähnlicher Modulbeschreibung. Dieses Modul können Sie als Vorschlag im Antrags Ihres Vorgangs vorschlagen.`
    },
    {
      question: 'Was ist die Deadline?',
      answer: `Die Fristen für eine Bewerbung variieren je nach Studiengang und der Zulassungesbeschränkung. Einige Studiengänge haben selbst in höheren Semestern noch eine Zulassungsbeschränkung. Welche
        Studiengänge dies betrifft erfahren sie auf den Seiten unserer Studiengänge. Dementsprechend ist es ratsam frühzeitig Ihren Antrag zu stellen, sodass alles problemlos funktionieren kann.`
    }
  ];
</script>

<div class="px-4 pt-5 mt-5 mb-2 text-center">
  <h1 class="display-4 fw-bold">FAQ's</h1>
</div>

<div class="accordion" id="accordionExample">
  {#each accordionData as item, index}
    <div class="accordion-item">
      <h2 class="accordion-header" id="headingOne">
        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse{index}" aria-expanded="false" aria-controls="collapseOne">
          {item.question}
        </button>
      </h2>
      <div id="collapse{index}" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
        <div class="accordion-body">
          {item.answer}
        </div>
      </div>
    </div>
  {/each}
</div>
