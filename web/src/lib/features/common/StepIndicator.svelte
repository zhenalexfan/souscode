<script lang="ts">
  import { cn } from "../../shared/cn";

  let {
    number,
    isActive = false,
    class: className = "",
  }: {
    number: number;
    isActive?: boolean;
    class?: string;
  } = $props();
</script>

<div class={cn("flex flex-col items-center gap-0", className)}>
  <span class="text-xs font-mono font-black">
    {number.toString().padStart(2, "0")}
  </span>
  {#if isActive}
    <div class="w-1.5 h-1.5 rounded-full bg-current animate-pulse"></div>
  {/if}
</div>
