<script lang="ts">
  import { Package, Thermometer, Hash } from "lucide-svelte";
  import { cn } from "../../../shared/cn";
  import SousMarkText from "../../common/SousMarkText.svelte";
  import ContainerTextRow from "../../common/ContainerTextRow.svelte";
  import Hexagon from "../../common/Hexagon.svelte";
  import PulsingDot from "../../common/PulsingDot.svelte";
  import { type ContainerSnapshot, Intensity } from "../types";
  import { t } from "../../../shared/i18n/i18n";

  let {
    snapshot,
    isTarget = false,
    minimal = false,
    class: className = "",
  }: {
    snapshot: ContainerSnapshot;
    isTarget?: boolean;
    minimal?: boolean;
    class?: string;
  } = $props();

  const intensityMap = {
    [Intensity.Low]: "player.intensity.low",
    [Intensity.Medium]: "player.intensity.medium",
    [Intensity.High]: "player.intensity.high",
  };
</script>

<div
  class={cn(
    isTarget
      ? "bg-highlighted-container text-on-highlighted-container shadow-xl ring-2 ring-primary/30"
      : "bg-surface/90 backdrop-blur-md text-on-surface border border-on-surface/5",
    "h-40 w-40 lg:h-48 lg:w-48 rounded-[2rem] pt-2 flex flex-col",
    "transition-all duration-500 group/card relative overflow-hidden",
    !isTarget &&
      !minimal &&
      "hover:shadow-[0_24px_48px_-12px_rgba(0,0,0,0.15)] hover:-translate-y-1.5",
    minimal && "shadow-sm pointer-events-none",
    className,
  )}
>
  <!-- Background subtle texture/glow -->
  {#if isTarget}
    <div
      class="absolute -top-12 -right-12 w-24 h-24 bg-primary/10 blur-3xl rounded-full"
    ></div>
  {/if}

  <div class="z-10 flex flex-col h-full overflow-hidden">
    <!-- Header: Name and Temp (Fixed at top) -->
    <div class="flex items-start justify-between mb-2 flex-none px-3">
      <div class="flex items-center gap-2">
        <Hexagon
          class={cn(
            "w-8 h-8 transition-colors",
            isTarget ? "text-primary/20" : "text-on-surface/5",
          )}
        >
          <Package
            size={14}
            strokeWidth={2}
            class={isTarget ? "text-primary" : "text-on-surface/60"}
          />
        </Hexagon>
        <div class="flex flex-col min-w-0">
          <ContainerTextRow
            text={snapshot.name}
            class="text-sm font-bold tracking-tight"
          />
          {#if !minimal}
            <div
              class="text-[10px] uppercase tracking-wider font-black opacity-40 leading-none"
            >
              {snapshot.state}
            </div>
          {/if}
        </div>
      </div>

      {#if !minimal && snapshot.temperature !== undefined}
        <div
          class="flex items-center gap-0.5 bg-on-surface/5 px-1.5 py-0.5 rounded-full"
        >
          <Thermometer size={10} class="opacity-50" />
          <span class="font-mono text-[10px] font-bold"
            >{snapshot.temperature}°</span
          >
        </div>
      {/if}
    </div>

    <!-- Scrollable Body -->
    <div
      class="flex-1 overflow-y-auto min-h-0 custom-scrollbar space-y-3 pr-1 -mr-1"
    >
      <!-- Contents -->
      <div class="flex flex-col px-3 h-full">
        {#if snapshot.contents.length > 0}
          <div class="flex flex-wrap gap-0.5">
            {#each snapshot.contents as content}
              <div
                class={cn(
                  "text-xs font-medium",
                  "pl-1 pr-1.5 py-0 flex items-center gap-1 rounded-full",
                  "bg-on-surface/5 transition-colors hover:bg-on-surface/10",
                  "cursor-default border border-on-surface/5",
                )}
              >
                <span
                  class={cn(
                    "w-1.5 h-1.5 rounded-full shrink-0",
                    content.state === "Raw"
                      ? "bg-emerald-400"
                      : "bg-orange-400",
                  )}
                ></span>
                <span class="lowercase text-on-surface/90">
                  <SousMarkText text={content.name} />
                </span>
              </div>
            {/each}
          </div>
        {:else if !minimal}
          <div
            class="flex flex-col items-center justify-center py-4 rounded-[1.5rem] border border-dashed border-on-surface/10 bg-on-surface/[0.02] group-hover/card:bg-on-surface/[0.04] transition-colors"
          >
            <div class="relative w-8 h-8 mb-1 opacity-20">
              <div
                class="absolute inset-0 rounded-full border border-current animate-ping opacity-20"
              ></div>
              <div
                class="absolute inset-2 rounded-full border border-current"
              ></div>
            </div>
            <span
              class="text-[10px] font-black uppercase tracking-[0.2em] opacity-30"
              >{$t("player.snapshot.vacuum")}</span
            >
          </div>
        {/if}
      </div>
    </div>

    <!-- Heating Indicator (Sticky at bottom) -->
    {#if !minimal && snapshot.isHeated}
      <div
        class="flex-none py-1.5 border-t border-on-surface/10 border-dashed flex items-center justify-between px-4 backdrop-blur-sm shadow-[0_-8px_16px_rgba(0,0,0,0.03)]"
      >
        <div class="flex items-center gap-2">
          <PulsingDot class="w-2 h-2" />
          <span
            class="text-[10px] text-primary lowercase font-medium tracking-tight"
          >
            {$t("player.snapshot.heat")}
            <span class="font-bold py-0.5 rounded-full"
              >{snapshot.intensity !== undefined && snapshot.intensity !== null
                ? $t(intensityMap[snapshot.intensity])
                : $t("player.snapshot.active")}</span
            >
          </span>
        </div>
      </div>
    {/if}
  </div>
</div>

<style>
  .custom-scrollbar::-webkit-scrollbar {
    width: 0px;
    height: 0px;
  }
  .custom-scrollbar {
    scrollbar-width: none;
  }
</style>
