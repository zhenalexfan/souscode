<script lang="ts">
  import { getRecipe, recipes } from "./recipe.data";
  import RecipeCodeViewer from "./RecipeCodeViewer.svelte";
  import RecipeDetailHeader from "./RecipeDetailHeader.svelte";
  import InstructionList from "./InstructionList.svelte";
  import { t } from "../../shared/i18n/i18n";

  let { id } = $props();
  let recipe = $derived(getRecipe(id, $recipes));
</script>

{#if recipe}
  <div
    class="h-full w-full flex flex-col lg:flex-row p-2 md:p-4 gap-2 md:gap-4 bg-background overflow-hidden fixed inset-0"
  >
    <!-- Left Pane: Code -->
    <RecipeCodeViewer code={recipe.code} />

    <!-- Right Pane: Info -->
    <div
      class="flex-1 bg-surface rounded-3xl md:rounded-[2.5rem] p-6 md:p-8 lg:p-16 overflow-auto shadow-2xl flex flex-col gap-8 md:gap-12 text-on-surface custom-scrollbar relative pb-32 md:pb-16"
    >
      <RecipeDetailHeader
        id={recipe.id}
        name={recipe.name}
        ingredients={recipe.ingredients}
      />

      <InstructionList instructions={recipe.instructions} />
    </div>
  </div>
{:else}
  <div
    class="headline p-8 h-[100dvh] w-full flex items-center justify-center text-2xl md:text-4xl text-on-background"
  >
    {$t("recipe.notFound")}
  </div>
{/if}

<style>
  .custom-scrollbar::-webkit-scrollbar {
    width: 0px;
    background: transparent;
  }
  .custom-scrollbar {
    -ms-overflow-style: none; /* IE and Edge */
    scrollbar-width: none; /* Firefox */
  }
</style>
