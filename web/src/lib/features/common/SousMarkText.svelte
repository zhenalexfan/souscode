<script lang="ts">
  import { parseSousMark, type Token } from "./sousMark";

  let { text }: { text: string } = $props();
  let tokens = $derived(parseSousMark(text));
</script>

{#each tokens as token}
  {#if token.type === "text"}
    {token.content}
  {:else if token.type === "ingredient"}
    <span
      class="inline-block font-bold border-b border-on-surface/0 hover:border-on-surface/50 cursor-help"
      title={`ID: ${token.id}`}
    >
      {token.content}
    </span>
  {:else if token.type === "container"}
    <span
      class="inline-block font-bold border-b border-on-surface/0 hover:border-on-surface/50 cursor-help"
      title={`ID: ${token.id}${token.kind ? ` (${token.kind})` : ""}`}
    >
      {token.content}
    </span>
  {/if}
{/each}
