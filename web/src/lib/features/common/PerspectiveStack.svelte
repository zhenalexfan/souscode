<script lang="ts" generics="T">
  import { cn } from "../../shared/cn";
  import type { Snippet } from "svelte";

  let {
    items,
    renderItem,
    expanded = $bindable(false),
    class: className = "",
  }: {
    items: T[];
    renderItem: Snippet<
      [T, { index: number; total: number; expanded: boolean }]
    >;
    expanded?: boolean;
    class?: string;
  } = $props();

  function toggle(e: MouseEvent) {
    if (!expanded) {
      e.stopPropagation();
      expanded = true;
    }
  }

  let isHovered = $state(false);
</script>

<!-- svelte-ignore a11y_click_events_have_key_events -->
<!-- svelte-ignore a11y_no_static_element_interactions -->
<div
  class={cn(
    "perspective-stack relative transition-all duration-700 ease-[cubic-bezier(0.34,1.56,0.64,1)]",
    expanded && "is-expanded",
    !expanded
      ? "h-40 lg:h-48 cursor-pointer active:scale-95 z-0 hover:z-50"
      : "flex flex-row gap-3",
    className,
  )}
  onclick={toggle}
  onmouseenter={() => (isHovered = true)}
  onmouseleave={() => (isHovered = false)}
  style="perspective: 2000px; transform-style: preserve-3d; --cos-angle: {isHovered
    ? 0.766
    : 0.422};"
>
  {#each items as item, i}
    {@const depth = items.length - 1 - i}
    <div
      class={cn(
        "transition-all duration-700 ease-[cubic-bezier(0.34,1.56,0.64,1)]",
        !expanded ? "absolute top-0 left-1/2" : "relative",
      )}
      style={!expanded
        ? `
        transform: translateX(-50%) translate3d(${-depth * (isHovered ? 12 : 5)}px, ${depth * (isHovered ? -4 : -2)}px, ${-depth * 80}px) rotateY(${isHovered ? 40 : 65}deg) rotateZ(${isHovered ? depth * 1 : 0}deg);
        z-index: ${items.length - depth};
        opacity: ${depth === 0 ? 1 : Math.max(0.4, 1 - depth * 0.15)};
        filter: ${depth === 0 ? "none" : `blur(${depth * 0.25}px) brightness(${1 - depth * 0.08})`};
      `
        : ""}
    >
      {@render renderItem(item, { index: i, total: items.length, expanded })}
    </div>
  {/each}

  {#if !expanded && items.length > 1}
    <div
      class={cn(
        "absolute bottom-4 -right-2 z-[999] px-3 py-1 rounded-full",
        "bg-surface/80 backdrop-blur-xl border border-on-surface/10",
        "shadow-[0_8px_32px_rgba(0,0,0,0.12)]",
        "flex items-center gap-1.5",
        "transition-all duration-500 pointer-events-none",
        isHovered ? "translate-x-2 -translate-y-1 scale-110" : "",
      )}
    >
      <div class="w-1.5 h-1.5 rounded-full bg-primary animate-pulse"></div>
      <span
        class="text-[11px] font-bold tracking-tight text-on-surface/80 lowercase"
      >
        {items.length} stacked
      </span>
    </div>
  {/if}
</div>

<style>
  .perspective-stack:not(.is-expanded) {
    --nominal-width: 10rem;
    width: calc(var(--nominal-width) * var(--cos-angle));
  }

  .perspective-stack.is-expanded {
    width: auto;
  }

  @media (min-width: 1024px) {
    .perspective-stack:not(.is-expanded) {
      --nominal-width: 12rem;
    }
  }
</style>
