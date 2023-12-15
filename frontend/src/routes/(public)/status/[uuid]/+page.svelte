<script>
  export let data;
</script>

<p>Status mit der UUID: {data.uuid}</p>


<pre>
    {JSON.stringify(data.procedure, null, 2)}
</pre>