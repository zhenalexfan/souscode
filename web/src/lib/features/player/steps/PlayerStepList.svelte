<script lang="ts">
  import StepItem from "./StepItem.svelte";
  import { cn } from "../../../shared/cn";
  import type { PlayerAction } from "../types";

  let {
    actions,
    currentIndex,
    onSelect,
    class: className = "",
  }: {
    actions: PlayerAction[];
    currentIndex: number;
    onSelect: (index: number) => void;
    class?: string;
  } = $props();
</script>

<div class={cn("overflow-y-auto p-6 space-y-2 custom-scrollbar", className)}>
  {#each actions as action, i}
    <StepItem
      {action}
      index={i}
      isActive={i === currentIndex}
      isCompleted={i < currentIndex}
      onclick={() => onSelect(i)}
    />
  {/each}
</div>

<style>
  .custom-scrollbar::-webkit-scrollbar {
    width: 0px;
  }
  .custom-scrollbar {
    scrollbar-width: none;
  }
</style>
