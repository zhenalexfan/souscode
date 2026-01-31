<script lang="ts">
  import { onMount } from "svelte";
  import { createHighlighterCore } from "shiki/core";
  import { createJavaScriptRegexEngine } from "shiki/engine/javascript";
  import { ListChevronsDownUp, ListChevronsUpDown } from "lucide-svelte";
  import { cn } from "../cn";

  interface Props {
    code: string;
    language?: string;
    collapsibleRanges?: { start: number; end: number; label?: string }[];
    class?: string;
  }

  let {
    code = "",
    language = "kotlin",
    collapsibleRanges = [],
    class: className = "",
  }: Props = $props();

  let highlighter = $state<any>(null);
  let highlightedLines = $state<any[][]>([]);
  let collapsedState = $state<Record<number, boolean>>({});

  // Initialize collapsed state from props
  $effect(() => {
    const newState: Record<number, boolean> = {};
    collapsibleRanges.forEach((range, index) => {
      if (collapsedState[index] === undefined) {
        newState[index] = true;
      }
    });
    Object.assign(collapsedState, newState);
  });

  onMount(async () => {
    // Dynamically import shiki components to keep bundle size small or avoid SSR issues if any
    const { default: kotlin } = await import("shiki/langs/kotlin.mjs");
    const { default: theme } = await import("shiki/themes/github-dark.mjs");

    highlighter = await createHighlighterCore({
      themes: [theme],
      langs: [kotlin],
      engine: createJavaScriptRegexEngine(),
    });
  });

  $effect(() => {
    if (highlighter && code) {
      const tokens = highlighter.codeToTokens(code, {
        lang: language,
        theme: "github-dark",
      });
      highlightedLines = tokens.tokens;
    } else {
      highlightedLines = code
        .split("\n")
        .map((line) => [{ content: line, color: "inherit" }]);
    }
  });

  function isLineInCollapsedRange(lineIdx: number) {
    for (let i = 0; i < collapsibleRanges.length; i++) {
      const range = collapsibleRanges[i];
      if (lineIdx + 1 >= range.start && lineIdx + 1 <= range.end) {
        return {
          inRange: true,
          rangeIdx: i,
          isFirst: lineIdx + 1 === range.start,
        };
      }
    }
    return { inRange: false };
  }

  function toggleCollapse(rangeIdx: number) {
    collapsedState[rangeIdx] = !collapsedState[rangeIdx];
  }
</script>

<div
  class="code-viewer {className} font-mono selection:bg-white/20 selection:text-white"
>
  {#each highlightedLines as line, i}
    {@const status = isLineInCollapsedRange(i)}
    {#if status.inRange && status.rangeIdx !== undefined && collapsedState[status.rangeIdx]}
      {#if status.isFirst}
        <div
          class={cn(
            "line-container group/line flex items-center min-h-[1.5em] my-1",
            "opacity-20 hover:opacity-100",
          )}
        >
          <div class="w-8 flex-shrink-0 flex justify-center">
            <button
              onclick={() => toggleCollapse(status.rangeIdx!)}
              class="text-white/20 hover:text-white/60 transition-colors cursor-pointer"
              title="Expand"
            >
              <ListChevronsUpDown size={14} />
            </button>
          </div>
          <button
            onclick={() => toggleCollapse(status.rangeIdx!)}
            class={cn(
              "inline-flex items-center px-1.5 py-0.5 rounded",
              "text-white/50 hover:text-white",
              "transition-colors text-xs font-sans cursor-pointer whitespace-nowrap",
            )}
            title="Expand {collapsibleRanges[status.rangeIdx].label || 'lines'}"
          >
            {collapsibleRanges[status.rangeIdx].label || "..."}
          </button>
        </div>
      {/if}
    {:else}
      <div
        class={cn(
          "line-container group/line flex items-start leading-relaxed min-h-[1.5em]",
        )}
      >
        <div class="w-8 flex-shrink-0 flex justify-center pt-1">
          {#if status.inRange && status.rangeIdx !== undefined && status.isFirst}
            <button
              onclick={() => toggleCollapse(status.rangeIdx!)}
              class={cn(
                "text-white/20 hover:text-white/60 transition-colors cursor-pointer",
                "opacity-20 hover:opacity-100",
              )}
              title="Collapse"
            >
              <ListChevronsDownUp size={14} />
            </button>
          {/if}
        </div>
        <div class="line flex-1 whitespace-pre-wrap">
          {#each line as token}<span style="color: {token.color}"
              >{token.content}</span
            >{/each}{#if line.length === 0 || (line.length === 1 && line[0].content === "")}<span
              >&nbsp;</span
            >{/if}
        </div>
      </div>
    {/if}
  {/each}
</div>

<style>
  .line-container {
    min-height: 1.5em;
  }
  .line {
    min-height: 1.5em;
  }
  button {
    background: none;
    border: none;
    padding: 0;
    margin: 0;
    font: inherit;
    color: inherit;
    cursor: pointer;
  }
</style>
