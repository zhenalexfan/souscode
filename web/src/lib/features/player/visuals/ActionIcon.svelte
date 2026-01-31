<script lang="ts">
  import {
    Flame,
    Plus,
    Combine,
    Timer,
    UtensilsCrossed,
    Check,
    ArrowRightLeft,
  } from "lucide-svelte";
  import { cn } from "../../../shared/cn";
  import { InstructionStepCategory } from "../types";
  import { onMount } from "svelte";
  let {
    type,
    progress = 0,
    durationSeconds = 0,
  }: {
    type: InstructionStepCategory;
    progress?: number;
    durationSeconds?: number;
  } = $props();

  let isLargeScreen = $state(false);

  onMount(() => {
    const mql = window.matchMedia("(min-width: 1024px)");
    isLargeScreen = mql.matches;
    const listener = (e: MediaQueryListEvent) => (isLargeScreen = e.matches);
    mql.addEventListener("change", listener);
    return () => mql.removeEventListener("change", listener);
  });

  const getActionIcon = (type: InstructionStepCategory) => {
    switch (type) {
      case InstructionStepCategory.Heat:
        return Flame;
      case InstructionStepCategory.Add:
        return Plus;
      case InstructionStepCategory.Mix:
        return Combine;
      case InstructionStepCategory.Wait:
        return Timer;
      case InstructionStepCategory.Transfer:
        return ArrowRightLeft;
      case InstructionStepCategory.Done:
        return Check;
      default:
        return UtensilsCrossed;
    }
  };

  const getActionColor = (type: InstructionStepCategory) => {
    switch (type) {
      case InstructionStepCategory.Heat:
        return "text-background";
      case InstructionStepCategory.Add:
        return "text-green-600";
      case InstructionStepCategory.Mix:
        return "text-purple-600";
      case InstructionStepCategory.Wait:
        return "text-yellow-600";
      case InstructionStepCategory.Transfer:
        return "text-blue-600";
      case InstructionStepCategory.Done:
        return "text-emerald-600";
      default:
        return "text-on-background/40";
    }
  };

  const formatCountdown = (p: number, total: number) => {
    const remaining = Math.max(0, Math.ceil(total * (1 - p)));
    if (remaining < 60) return remaining.toString();
    const mm = Math.floor(remaining / 60);
    const ss = remaining % 60;
    return `${mm}:${ss.toString().padStart(2, "0")}`;
  };

  const ActionIcon = getActionIcon(type);
  let actionColor = $derived(getActionColor(type));
  let countdownText = $derived(formatCountdown(progress, durationSeconds));
</script>

<div class="relative group">
  <!-- Glow Effect -->
  <div
    class={cn(
      "absolute inset-0 transition-all duration-700 opacity-20 group-hover:opacity-30",
      "rounded-[2.5rem] blur-[32px] lg:rounded-[4rem] lg:blur-[64px]",
      actionColor.replace("text-", "bg-"),
    )}
  ></div>

  <!-- Icon Container -->
  <div
    class={cn(
      "relative bg-surface border border-on-surface/5 flex items-center justify-center overflow-hidden shadow-[0_32px_64px_-16px_rgba(0,0,0,0.1)] transition-transform duration-500 group-hover:scale-[1.02]",
      "w-24 h-24 rounded-[2.5rem]",
      "lg:w-36 lg:h-36 lg:rounded-[4rem]",
    )}
  >
    {#if type === InstructionStepCategory.Wait}
      <span
        class={cn(
          actionColor,
          "text-lg lg:text-2xl font-mono tracking-tighter tabular-nums",
          "animate-pulse-slow transition-transform duration-500 group-hover:scale-110",
        )}
      >
        {countdownText}
      </span>
    {:else}
      <ActionIcon
        strokeWidth={1}
        class={cn(
          actionColor,
          "w-12 h-12 lg:w-[72px] lg:h-[72px]",
          "animate-pulse-slow transition-transform duration-500 group-hover:scale-110",
        )}
      />
    {/if}

    <!-- Progress Border (Solid Running Dot) -->
    {#if progress > 0}
      <svg
        class="absolute inset-0 pointer-events-none overflow-visible p-[2px] -rotate-90"
        viewBox="0 0 100 100"
      >
        <!-- Start/End Marker (Shared Geometry) -->
        <rect
          x="2"
          y="2"
          width="96"
          height="96"
          rx={isLargeScreen ? "42" : "40"}
          fill="none"
          stroke="currentColor"
          stroke-width="5"
          pathLength="1"
          stroke-dasharray="0.001 0.999"
          stroke-dashoffset="0"
          stroke-linecap="round"
          class={cn(actionColor, "opacity-10")}
        />

        <!-- The Running Dot -->
        <rect
          x="2"
          y="2"
          width="96"
          height="96"
          rx={isLargeScreen ? "42" : "40"}
          fill="none"
          stroke="currentColor"
          stroke-width="5"
          pathLength="1"
          stroke-dasharray="0.001 0.999"
          stroke-dashoffset={-progress}
          stroke-linecap="round"
          class={cn(actionColor, "transition-all duration-150 ease-linear")}
        />
      </svg>
    {/if}

    {#if type === InstructionStepCategory.Heat}
      <div
        class="absolute bottom-0 left-0 right-0 h-1/3 bg-gradient-to-t from-primary/5 to-transparent"
      ></div>
    {/if}
  </div>
</div>

<style>
  :global(.animate-pulse-slow) {
    animation: pulse 4s cubic-bezier(0.4, 0, 0.6, 1) infinite;
  }

  @keyframes pulse {
    0%,
    100% {
      opacity: 1;
      transform: scale(1);
    }
    50% {
      opacity: 0.8;
      transform: scale(1.03);
    }
  }
</style>
