<script lang="ts">
  import type { Snippet } from "svelte";
  import { marked } from "marked";
  import { cn } from "../../shared/cn";
  import Hexagon from "../common/Hexagon.svelte";

  let {
    title,
    description,
    icon,
    class: className,
  }: {
    title: string;
    description: string;
    icon: Snippet;
    class?: string;
  } = $props();

  let htmlContent = $derived(marked.parse(description, { async: false }));
</script>

<div
  class={cn(
    "border border-on-background/20",
    "p-6 rounded-4xl bg-surface/5 backdrop-blur-sm",
    className,
  )}
>
  <Hexagon class={cn("p-4 text-surface w-fit")}>
    <div class={cn("text-on-surface")}>
      {@render icon()}
    </div>
  </Hexagon>
  <div class={cn("text-2xl font-bold my-6")}>
    {title}
  </div>
  <div
    class={cn(
      "text-lg opacity-80 leading-relaxed",
      "prose prose-p:text-lg prose-p:leading-relaxed prose-p:opacity-100",
      "prose-a:text-accent prose-a:underline prose-a:font-medium",
      "prose-strong:text-on-background prose-strong:font-black",
      "prose-code:text-on-background",
      "text-on-background",
    )}
  >
    {@html htmlContent}
  </div>
</div>
