<script lang="ts">
  import { tick } from "svelte";
  import { portal } from "../portal";
  import { cn } from "../cn";

  interface Props {
    anchor: HTMLElement | null;
    open?: boolean;
    align?: "left" | "right" | "center";
    side?: "top" | "bottom";
    class?: string;
    children?: any;
    style?: string;
    id?: string;
    role?: string;
    tabindex?: string | number;
    "aria-labelledby"?: string;
    onkeydown?: (event: KeyboardEvent) => void;
    onmouseenter?: (event: MouseEvent) => void;
    onmouseleave?: (event: MouseEvent) => void;
    element?: HTMLDivElement | null;
    transition?: any;
    transitionParams?: any;
  }

  let {
    anchor,
    open = false,
    align = "center",
    side = "bottom",
    class: className = "",
    children,
    style = "",
    id,
    role,
    tabindex,
    "aria-labelledby": ariaLabelledBy,
    onkeydown,
    onmouseenter,
    onmouseleave,
    element = $bindable(null),
    transition = null,
    transitionParams = {},
  }: Props = $props();

  let contentRef: HTMLDivElement | null = $state(null);

  function dynamicTransition(node: Element, params: any) {
    if (transition) {
      return transition(node, params);
    }
    return { duration: 0 };
  }

  $effect(() => {
    if (contentRef) {
      element = contentRef;
    }
  });
  let position = $state({ top: 0, left: 0 });

  export async function updatePosition() {
    if (!anchor || !contentRef) return;

    const rect = anchor.getBoundingClientRect();
    const spacing = 8;
    const contentWidth = contentRef.offsetWidth;
    const contentHeight = contentRef.offsetHeight;

    let top =
      side === "bottom"
        ? rect.bottom + spacing
        : rect.top - contentHeight - spacing;

    let left =
      align === "right"
        ? rect.right - contentWidth
        : align === "left"
          ? rect.left
          : rect.left + rect.width / 2 - contentWidth / 2;

    const vw = window.innerWidth;
    const vh = window.innerHeight;

    // Boundary checks
    if (left < 8) left = 8;
    if (left + contentWidth > vw) left = vw - contentWidth - 8;

    // Vertical flip logic
    if (top < 8) top = 8;
    if (top + contentHeight > vh) {
      // Try flipping
      const topSpace = rect.top - spacing;
      const bottomSpace = vh - rect.bottom - spacing;

      if (side === "bottom" && topSpace > contentHeight) {
        top = rect.top - contentHeight - spacing;
      } else if (side === "top" && bottomSpace > contentHeight) {
        top = rect.bottom + spacing;
      }

      // Final safety check
      if (top < 8) top = 8;
      if (top + contentHeight > vh) top = vh - contentHeight - 8;
    }

    position = { top, left };
  }

  $effect(() => {
    if (open && anchor) {
      // Initial position
      updatePosition();
      // Check again after render in case size changed
      tick().then(updatePosition);
    }
  });
</script>

<svelte:window
  onresize={() => open && updatePosition()}
  onscroll={() => open && updatePosition()}
/>

{#if open}
  <div
    bind:this={contentRef}
    use:portal
    {id}
    {role}
    tabindex={typeof tabindex === "string" ? parseInt(tabindex) : tabindex}
    aria-labelledby={ariaLabelledBy}
    class={cn("fixed z-[9999]", className)}
    style="top: {position.top}px; left: {position.left}px; {style}"
    {onkeydown}
    {onmouseenter}
    {onmouseleave}
    transition:dynamicTransition={transitionParams}
  >
    {@render children?.()}
  </div>
{/if}
