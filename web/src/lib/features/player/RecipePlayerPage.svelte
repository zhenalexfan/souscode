<script lang="ts">
  import { onMount, onDestroy } from "svelte";
  import { fade } from "svelte/transition";
  import { RecipePlayerStore } from "./recipePlayerStore.svelte";
  import { cn } from "../../shared/cn";

  import { locale } from "../../shared/i18n/i18n";
  import PlayerHeader from "./visuals/PlayerHeader.svelte";
  import PlayerStepList from "./steps/PlayerStepList.svelte";
  import PlayerActionVisual from "./visuals/PlayerActionVisual.svelte";
  import PlayerControls from "./playback/PlayerControls.svelte";
  import PlayerError from "./PlayerError.svelte";
  import PlayerLoading from "./PlayerLoading.svelte";

  let { id }: { id: string } = $props();
  const player = new RecipePlayerStore();

  onMount(() => {
    player.load(id);
  });

  $effect(() => {
    player.currentLocale = $locale;
  });

  onDestroy(() => {
    player.destroy();
  });
</script>

{#if player.flattenedActions.length > 0}
  <div
    class={cn(
      "bg-background text-on-background selection:bg-accent selection:text-on-accent",
      "fixed inset-0 flex flex-col overflow-hidden",
    )}
    in:fade={{ duration: 800 }}
  >
    <PlayerHeader
      {id}
      recipeName={player.recipeName}
      currentStep={player.currentStepIndex + 1}
      totalSteps={player.flattenedActions.length}
    />

    <main class="flex-1 flex flex-col lg:flex-row overflow-hidden">
      <section
        class="flex-[1.5] relative min-w-0 overflow-hidden lg:flex-[2.5]"
      >
        <PlayerActionVisual
          action={player.flattenedActions[player.currentStepIndex]}
          stepIndex={player.currentStepIndex}
          progress={player.progress}
        />
      </section>

      <!-- Right Content: Step List & Playback -->
      <aside
        class={cn(
          "flex-1 lg:min-w-[420px] min-w-0 flex flex-col overflow-hidden relative",
          "ml-4 lg:ml-0 mr-4 mb-4 rounded-4xl z-10",
          "bg-primary-container/20 lg:shadow-xl",
        )}
      >
        <PlayerStepList
          actions={player.flattenedActions}
          currentIndex={player.currentStepIndex}
          onSelect={(index) => player.selectStep(index)}
          class="flex-1 pb-32 lg:pb-6"
        />

        <PlayerControls
          isPlaying={player.isPlaying}
          canPrev={player.currentStepIndex > 0}
          canNext={player.currentStepIndex < player.flattenedActions.length - 1}
          onToggle={() => player.togglePlay()}
          onPrev={() => player.prevStep()}
          onNext={() => player.nextStep()}
          class={cn(
            "absolute bottom-6 right-6 shadow-2xl rounded-3xl border border-on-background/10",
            "lg:static lg:bottom-auto lg:right-auto lg:shadow-none lg:rounded-none lg:border-none",
            "lg:border-t lg:border-on-background/5",
          )}
        />
      </aside>
    </main>
  </div>
{:else if player.error}
  <PlayerError {id} error={player.error} />
{:else}
  <PlayerLoading />
{/if}

<style>
  :global(*::-webkit-scrollbar) {
    width: 0px;
  }
  :global(*) {
    scrollbar-width: none;
  }
</style>
