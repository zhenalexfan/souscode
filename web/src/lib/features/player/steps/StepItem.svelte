<script lang="ts">
  import { cn } from "../../../shared/cn";
  import ContainerTextRow from "../../common/ContainerTextRow.svelte";
  import SousMarkText from "../../common/SousMarkText.svelte";
  import StepIndicator from "../../common/StepIndicator.svelte";
  import type { PlayerAction } from "../types";

  let {
    action,
    index,
    isActive,
    isCompleted,
    onclick,
  }: {
    action: PlayerAction;
    index: number;
    isActive: boolean;
    isCompleted: boolean;
    onclick: () => void;
  } = $props();
</script>

<button
  {onclick}
  class={cn(
    "w-full text-left p-3 rounded-[2.5rem] transition-all duration-500 group relative border",
    isActive
      ? "bg-surface-variant text-on-surface shadow-xl shadow-primary/20 scale-102"
      : isCompleted
        ? "bg-primary-container/10 text-on-background/20 border-transparent hover:bg-primary-container/20 hover:text-on-background/40"
        : "bg-primary-container/10 text-on-background border-transparent hover:bg-primary-container/30",
  )}
>
  <div class="flex gap-4 items-start">
    <StepIndicator number={index + 1} {isActive} class="pt-0.5" />

    <div class="flex-1 space-y-1">
      <p class="text-xl font-bold tracking-tight">
        <SousMarkText text={action.instruction.description} />
      </p>
      {#if !isActive && action.containerName}
        <ContainerTextRow
          text={action.containerName}
          class="text-xs font-medium"
        />
      {/if}
    </div>
  </div>

  <!-- Selection Indicator -->
  {#if isActive}
    <div
      class="absolute -right-1 top-1/2 -translate-y-1/2 w-2 h-12 bg-on-primary/20 rounded-l-full blur-sm"
    ></div>
  {/if}
</button>
