<script lang="ts">
  import SnapshotCard from "./SnapshotCard.svelte";
  import type { ContainerSnapshot } from "../types";
  import FadedScrollable from "../../../shared/components/FadedScrollable.svelte";
  import PerspectiveStack from "../../common/PerspectiveStack.svelte";

  let {
    snapshots = [],
    targetContainer = undefined,
  }: {
    snapshots: ContainerSnapshot[];
    targetContainer?: string;
  } = $props();

  type DisplayItem =
    | { type: "single"; snapshot: ContainerSnapshot }
    | { type: "stack"; snapshots: ContainerSnapshot[] };

  let displayItems = $derived.by(() => {
    const items: DisplayItem[] = [];
    let currentStack: ContainerSnapshot[] = [];
    let foundTarget = false;

    for (const s of snapshots) {
      const isTarget = s.name === targetContainer;

      if (isTarget) {
        if (currentStack.length > 0) {
          if (currentStack.length === 1) {
            items.push({ type: "single", snapshot: currentStack[0] });
          } else {
            items.push({ type: "stack", snapshots: [...currentStack] });
          }
          currentStack = [];
        }
        foundTarget = true;
        items.push({ type: "single", snapshot: s });
        continue;
      }

      if (!foundTarget && s.contents.length === 0) {
        currentStack.push(s);
      } else {
        if (currentStack.length > 0) {
          if (currentStack.length === 1) {
            items.push({ type: "single", snapshot: currentStack[0] });
          } else {
            items.push({ type: "stack", snapshots: [...currentStack] });
          }
          currentStack = [];
        }
        items.push({ type: "single", snapshot: s });
      }
    }

    if (currentStack.length > 0) {
      if (currentStack.length === 1) {
        items.push({ type: "single", snapshot: currentStack[0] });
      } else {
        items.push({ type: "stack", snapshots: [...currentStack] });
      }
    }

    return items;
  });
</script>

<div class="w-full h-full flex flex-col min-h-0">
  {#if snapshots.length === 0}
    <div class="flex-1 flex flex-col items-center justify-center text-center">
      <p class="text-sm font-bold text-on-surface/20">
        No simulation data for this step
      </p>
    </div>
  {:else}
    <FadedScrollable orientation="horizontal" class="flex-1">
      <div class="flex flex-row gap-3 py-4 px-1 items-start">
        {#each displayItems as item}
          <div class="shrink-0">
            {#if item.type === "single"}
              <SnapshotCard
                snapshot={item.snapshot}
                isTarget={item.snapshot.name === targetContainer}
              />
            {:else}
              <PerspectiveStack items={item.snapshots}>
                {#snippet renderItem(
                  snapshot: ContainerSnapshot,
                  { index, expanded },
                )}
                  <SnapshotCard
                    {snapshot}
                    isTarget={snapshot.name === targetContainer}
                    minimal={!expanded && index > 0}
                  />
                {/snippet}
              </PerspectiveStack>
            {/if}
          </div>
        {/each}
      </div>
    </FadedScrollable>
  {/if}
</div>
