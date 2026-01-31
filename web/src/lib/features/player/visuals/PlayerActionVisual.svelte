<script lang="ts">
  import { fly } from "svelte/transition";
  import { cubicOut } from "svelte/easing";
  import { cn } from "../../../shared/cn";
  import ActionIcon from "./ActionIcon.svelte";
  import ActionHeader from "./ActionHeader.svelte";
  import ActionSnapshots from "./ActionSnapshots.svelte";
  import FadedScrollable from "../../../shared/components/FadedScrollable.svelte";
  import type { PlayerAction } from "../types";

  let {
    action,
    stepIndex,
    progress = 0,
    class: className = "",
  }: {
    action: PlayerAction;
    stepIndex: number;
    progress?: number;
    class?: string;
  } = $props();

  // Track direction for smooth slide transitions
  let lastIndex = $state(stepIndex);
  let direction = $state(1);

  $effect.pre(() => {
    if (stepIndex !== lastIndex) {
      direction = stepIndex > lastIndex ? 1 : -1;
      lastIndex = stepIndex;
    }
  });
</script>

<div class={cn("h-full w-full flex flex-col overflow-hidden", className)}>
  <!-- Top Content: Animates on step change -->
  <div class="flex-1 min-h-0 grid grid-cols-1 grid-rows-1">
    {#key stepIndex}
      {@const currentAction = action}
      <div
        class="col-start-1 row-start-1 flex-1 flex flex-col min-h-0 w-full h-full"
        in:fly={{
          y: direction * 15,
          duration: 400,
          opacity: 0,
          easing: cubicOut,
          delay: 80,
        }}
        out:fly={{
          y: -direction * 15,
          duration: 300,
          opacity: 0,
          easing: cubicOut,
        }}
      >
        <FadedScrollable class="flex-1 flex flex-col items-center py-8 min-h-0">
          <div
            class="flex flex-col items-center gap-4 w-full max-w-5xl mx-auto"
          >
            <ActionIcon
              type={currentAction.instruction.category}
              {progress}
              durationSeconds={currentAction.instruction.durationSeconds ?? 0}
            />

            <div class="flex flex-col items-center gap-6 text-center">
              <ActionHeader
                container={currentAction.containerName}
                description={currentAction.instruction.description}
              />
            </div>
          </div>
        </FadedScrollable>
      </div>
    {/key}
  </div>

  <!-- Bottom Content: Snapshots (No transition, updates instantly) -->
  <div class="shrink-0 mt-1 mb-2">
    <ActionSnapshots
      snapshots={action.snapshots}
      targetContainer={action.containerName}
    />
  </div>
</div>
