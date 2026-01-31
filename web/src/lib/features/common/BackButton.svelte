<script lang="ts">
  import { ArrowLeft, X } from "lucide-svelte";
  import { cn } from "../../shared/cn";

  let {
    href,
    class: className = "",
    variant = "back",
  }: {
    href: string;
    class?: string;
    variant?: "back" | "close";
  } = $props();
</script>

<a
  {href}
  class={cn(
    "p-3 bg-white/10 rounded-full hover:bg-white text-white hover:text-black transition-all duration-300 backdrop-blur-md flex items-center justify-center group",
    className,
  )}
>
  {#if variant === "close"}
    <X
      size={22}
      strokeWidth={3}
      class="transition-transform group-hover:rotate-90"
    />
  {:else}
    <ArrowLeft size={22} strokeWidth={3} class="transition-transform" />
  {/if}
</a>
