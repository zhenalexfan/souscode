<script lang="ts">
  import { fade } from "svelte/transition";
  import { cn } from "../../shared/cn";
  import { t } from "../../shared/i18n/i18n";

  let { class: className = "" }: { class?: string } = $props();
</script>

<div
  class={cn(
    "h-screen flex items-center justify-center flex-col gap-8 bg-background",
    className,
  )}
  in:fade
>
  <div class="relative w-24 h-24">
    <div class="absolute inset-0 border-4 border-primary/20 rounded-full"></div>
    <div
      class="absolute inset-0 border-4 border-primary border-t-transparent rounded-full animate-spin"
    ></div>
  </div>
  <div class="space-y-1 text-center">
    <p
      class="text-2xl font-black text-on-background/20 uppercase tracking-[0.3em]"
    >
      {$t("player.loading.initializing")}
    </p>
    <p
      class="text-[10px] font-mono text-on-background/10 uppercase tracking-widest"
    >
      {$t("player.loading.registry")}
    </p>
  </div>
</div>
