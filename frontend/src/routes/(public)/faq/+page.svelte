<script>
  import Accordion from '$root/lib/components/Accordion.svelte';
  import { faqData } from '$root/lib/faq';
  import { login_schema } from '$root/lib/schema';

  const accordionData = faqData;
  console.log('question', faqData[0].question);
</script>

<div class="px-4 mt-5 mb-2 text-center">
  <h1 class="display-4 fw-bold">FAQ's</h1>
</div>

{#each faqData as _, i}
  <Accordion open={false}>
    <div slot="head">
      <h1 class="fs-6 m-0 px-2">{faqData[i].question}</h1>
    </div>
    <div slot="details"><p>{faqData[i].answer}</p></div>
  </Accordion>
{/each}
