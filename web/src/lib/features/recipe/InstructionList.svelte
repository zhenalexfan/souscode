<script lang="ts">
  import type { ContainerSteps } from "../../shared/types";
  import SousMarkText from "../common/SousMarkText.svelte";
  import ContainerTextRow from "../common/ContainerTextRow.svelte";
  import Hexagon from "../common/Hexagon.svelte";
  import StepIndicator from "../common/StepIndicator.svelte";
  import { cn } from "../../shared/cn";

  let { instructions }: { instructions: ContainerSteps[] } = $props();

  function toRoman(num: number): string {
    const romanMap = [
      { value: 10, symbol: "X" },
      { value: 9, symbol: "IX" },
      { value: 5, symbol: "V" },
      { value: 4, symbol: "IV" },
      { value: 1, symbol: "I" },
    ];
    let result = "";
    let n = num;
    for (const { value, symbol } of romanMap) {
      while (n >= value) {
        result += symbol;
        n -= value;
      }
    }
    return result;
  }

  let sectionStartIndices = $derived.by(() => {
    let indices: number[] = [];
    let current = 1;
    for (const section of instructions) {
      indices.push(current);
      current += section.steps.length;
    }
    return indices;
  });
</script>

<div class="pb-12">
  <div class="space-y-12">
    {#each instructions as section, sectionIndex}
      <div class="space-y-6">
        <div class="relative flex items-center h-16 group/section">
          <!-- Decorative Rounded Hexagon Asset -->
          <Hexagon
            class="absolute -left-10 w-22 h-22 text-primary-container/60"
          >
            <span
              class="text-on-primary-container text-3xl font-black tracking-tighter select-none pointer-events-none"
            >
              {toRoman(sectionIndex + 1)}
            </span>
          </Hexagon>

          <ContainerTextRow
            text={section.container}
            class={cn(
              "relative z-10 pl-9 text-xl ",
              "border-b border-on-surface/5 pb-1 flex-1 transition-all duration-300 group-hover/section:text-on-surface group-hover/section:pl-11",
            )}
          />
        </div>

        <ul class="space-y-1">
          {#each section.steps as step, stepIndex}
            <li class="flex gap-4 group">
              <StepIndicator
                number={sectionStartIndices[sectionIndex] + stepIndex}
                class="pt-2 opacity-80"
              />
              <div
                class="flex-1 text-xl font-medium leading-relaxed text-on-surface"
              >
                <SousMarkText text={step} />
              </div>
            </li>
          {/each}
        </ul>
      </div>
    {/each}
  </div>
</div>
