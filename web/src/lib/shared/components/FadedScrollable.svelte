<script lang="ts">
  import type { Snippet } from "svelte";
  import { cn } from "../cn";

  let {
    class: className = "",
    orientation = "vertical",
    children,
  }: {
    class?: string;
    orientation?: "vertical" | "horizontal";
    children: Snippet;
  } = $props();
</script>

<div
  class={cn(
    "custom-scrollbar scroll-fade",
    orientation === "vertical"
      ? "overflow-y-auto flex flex-col justify-start"
      : "overflow-x-auto flex flex-row items-stretch",
    className,
  )}
>
  <div
    class={cn(
      orientation === "vertical"
        ? "w-full my-auto"
        : "h-full flex flex-row items-center px-6",
    )}
  >
    {@render children()}
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

  .scroll-fade {
    mask-image: linear-gradient(
      to bottom,
      transparent,
      black 2rem,
      black calc(100% - 2rem),
      transparent
    );
    -webkit-mask-image: linear-gradient(
      to bottom,
      transparent,
      black 2rem,
      black calc(100% - 2rem),
      transparent
    );
  }

  .overflow-x-auto.scroll-fade {
    mask-image: linear-gradient(
      to right,
      transparent,
      black 2rem,
      black calc(100% - 2rem),
      transparent
    );
    -webkit-mask-image: linear-gradient(
      to right,
      transparent,
      black 2rem,
      black calc(100% - 2rem),
      transparent
    );
  }
</style>
