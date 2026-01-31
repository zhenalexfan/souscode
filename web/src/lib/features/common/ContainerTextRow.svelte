<script lang="ts">
  import SousMarkText from "./SousMarkText.svelte";
  import { cn } from "../../shared/cn";

  let { text, class: className = "" }: { text: string; class?: string } =
    $props();
</script>

<h4 class={cn("font-black uppercase tracking-[0.15em]", className)}>
  <SousMarkText {text} />
</h4>
