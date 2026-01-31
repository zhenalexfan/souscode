<script lang="ts">
  import { cn } from "../../../shared/cn";
  import SousMarkText from "../../common/SousMarkText.svelte";
  import ContainerTextRow from "../../common/ContainerTextRow.svelte";

  let {
    container,
    description,
  }: { container?: string | null; description: string } = $props();
</script>

<div class="text-center space-y-6 max-w-2xl px-4">
  {#if container}
    <div
      class={cn(
        "inline-flex items-center px-4 py-1 rounded-full border border-on-surface/5 shadow-sm",
        "bg-surface-variant text-on-surface",
        "text-sm",
      )}
    >
      <ContainerTextRow text={container} />
    </div>
  {/if}

  <h3 class={cn("headline text-4xl md:text-6xl leading-none tracking-tighter")}>
    <SousMarkText text={description} />
  </h3>
</div>
