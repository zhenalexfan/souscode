<script lang="ts">
  import { fade } from "svelte/transition";
  import { cn } from "../../shared/cn";
  import { t } from "../../shared/i18n/i18n";

  let {
    id,
    error,
    class: className = "",
  }: {
    id: string;
    error: string | null;
    class?: string;
  } = $props();
</script>

<div
  class={cn(
    "h-screen flex items-center justify-center flex-col gap-10 text-center p-12 bg-background",
    className,
  )}
  in:fade
>
  <div class="space-y-4">
    <div
      class="inline-flex p-8 bg-primary/5 rounded-[3rem] border border-primary/10 shadow-inner"
    >
      <p class="headline text-6xl text-primary lowercase tracking-tighter">
        {$t("player.error.oops")}
      </p>
    </div>
    <p
      class="text-on-background/40 font-black uppercase tracking-[0.3em] text-xs"
    >
      {$t("player.error.message")}
    </p>
  </div>

  <div
    class="font-mono bg-on-background/5 p-8 rounded-[2.5rem] max-w-2xl border border-on-background/5 text-sm text-on-background/60 leading-relaxed shadow-xl"
  >
    {error}
  </div>

  <a
    href="#/recipe/{id}"
    class="px-10 py-4 bg-on-background/5 hover:bg-on-background/10 rounded-full text-sm font-black uppercase tracking-[0.2em] transition-all"
  >
    {$t("player.error.back")}
  </a>
</div>
