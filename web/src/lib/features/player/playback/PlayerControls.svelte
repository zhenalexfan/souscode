<script lang="ts">
  import { Play, Pause, SkipBack, SkipForward } from "lucide-svelte";
  import { cn } from "../../../shared/cn";
  import type { Component } from "svelte";

  let {
    isPlaying,
    canPrev,
    canNext,
    onToggle,
    onPrev,
    onNext,
    class: className = "",
  }: {
    isPlaying: boolean;
    canPrev: boolean;
    canNext: boolean;
    onToggle: () => void;
    onPrev: () => void;
    onNext: () => void;
    class?: string;
  } = $props();
</script>

{#snippet NavButton({
  onclick,
  disabled,
  icon: Icon,
}: {
  onclick: () => void;
  disabled: boolean;
  icon: Component;
})}
  <button
    {onclick}
    {disabled}
    class={cn(
      "flex items-center justify-center transition-all duration-300 rounded-full",
      "hover:bg-background text-on-background hover:text-white",
      "disabled:opacity-40 disabled:cursor-not-allowed group",
      "w-10 h-10 lg:w-14 lg:h-14",
    )}
  >
    <Icon
      strokeWidth={1.5}
      class="w-5 h-5 lg:w-7 lg:h-7 transition-transform group-active:scale-90"
    />
  </button>
{/snippet}

<div
  class={cn(
    "backdrop-blur-2xl transition-all duration-300",
    "bg-background/80 shadow-2xl p-4 lg:bg-background/40 lg:p-10 lg:shadow-none",
    className,
  )}
>
  <div class="flex items-center justify-center gap-6 lg:gap-12">
    {@render NavButton({ onclick: onPrev, disabled: !canPrev, icon: SkipBack })}

    <button
      onclick={onToggle}
      class={cn(
        "rounded-full flex items-center justify-center transition-all duration-500 hover:scale-105 active:scale-95 shadow-2xl relative group",
        "w-14 h-14 lg:w-24 lg:h-24",
        isPlaying
          ? "bg-on-background text-background shadow-background/20"
          : "bg-surface-variant text-on-surface-variant",
      )}
    >
      <!-- Glow -->
      <div
        class={cn(
          "absolute inset-0 rounded-full blur-2xl opacity-20 transition-opacity group-hover:opacity-40",
          isPlaying ? "bg-on-background" : "bg-primary",
        )}
      ></div>

      {#if isPlaying}
        <Pause
          strokeWidth={1}
          fill="currentColor"
          class="w-6 h-6 lg:w-11 lg:h-11 relative z-10"
        />
      {:else}
        <Play
          strokeWidth={1}
          fill="currentColor"
          class="w-6 h-6 lg:w-11 lg:h-11 relative z-10 ml-0.5 lg:ml-1.5"
        />
      {/if}
    </button>

    {@render NavButton({
      onclick: onNext,
      disabled: !canNext,
      icon: SkipForward,
    })}
  </div>
</div>
