<script lang="ts">
  import { cn } from "../../../shared/cn";
  import { t } from "../../../shared/i18n/i18n";
  import BackButton from "../../common/BackButton.svelte";

  let {
    id,
    recipeName,
    currentStep,
    totalSteps,
    class: className = "",
  }: {
    id: string;
    recipeName: string;
    currentStep: number;
    totalSteps: number;
    class?: string;
  } = $props();

  let progress = $derived((currentStep / totalSteps) * 100);
</script>

<header
  class={cn(
    "h-14 px-6 flex items-center justify-between z-20 sticky top-0",
    className,
  )}
>
  <!-- Left Side: Back and Info -->
  <div class="flex items-center gap-4">
    <BackButton
      href="#/recipe/{id}"
      class="bg-on-background/5 text-on-background hover:bg-on-background hover:text-background"
    />

    <div class="flex flex-col cursor-default">
      <span class="text-xs lowercase font-medium"
        >{$t("player.currentlyCooking")}</span
      >
      <h1 class="headline text-xl lowercase leading-none text-on-background">
        {recipeName}
      </h1>
    </div>
  </div>

  <!-- Center: Progress Bar -->
  <div
    class="absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2 hidden lg:block"
  >
    <div class="w-64 h-1.5 bg-on-background/40 rounded-full overflow-hidden">
      <div
        class="h-full bg-on-background transition-all duration-700 ease-[cubic-bezier(0.23,1,0.32,1)]"
        style="width: {progress}%"
      ></div>
    </div>
  </div>

  <!-- Right Side: Step Counter -->
  <div class="flex items-center">
    <p class="text-base font-mono font-bold leading-none cursor-default">
      {currentStep.toString().padStart(2, "0")}<span
        class="text-on-background/50 font-normal"
        >/{totalSteps.toString().padStart(2, "0")}</span
      >
    </p>
  </div>
</header>
