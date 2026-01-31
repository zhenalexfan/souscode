<script lang="ts">
  import { recipes } from "../recipe/recipe.data";
  import RecipeGrid from "../recipe/RecipeGrid.svelte";
  import RecipeGridItem from "../recipe/RecipeGridItem.svelte";
  import RecipeSearch from "../recipe/RecipeSearch.svelte";
  import LanguageSwitcher from "../../shared/components/LanguageSwitcher.svelte";
  import HomeMenu from "./HomeMenu.svelte";

  import { cn } from "../../shared/cn";
  import { Info } from "lucide-svelte";
  import { t } from "../../shared/i18n/i18n";

  let searchQuery = $state("");

  let filteredRecipes = $derived(
    $recipes.filter(
      (r) =>
        r.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        r.ingredients.some((i) =>
          i.toLowerCase().includes(searchQuery.toLowerCase()),
        ),
    ),
  );

  let scrollY = $state(0);
  let isShrink = $derived(scrollY > 0);
</script>

<svelte:window bind:scrollY />

<div class="p-4 md:p-8 max-w-7xl mx-auto">
  <header class="sticky mb-8 md:mb-16 pt-4 md:pt-8 -mx-4 md:-mx-8 px-4 md:px-8">
    <div class="flex justify-between items-end w-full">
      <div class="flex flex-col items-start transition-all duration-500">
        <a
          href="#/about"
          class={cn(
            "flex items-center gap-2",
            "text-xs font-medium tracking-wide lowercase",
            "text-on-background/80 hover:text-on-background hover:border-on-background cursor-pointer",
            "border border-on-background/20 px-3 py-1 rounded-full",
            "transition-all duration-500 origin-left",
            "-mt-6",
            isShrink
              ? "mb-1 scale-90 translate-y-2 md:translate-y-8"
              : "mb-2 md:mb-4 scale-100 translate-y-3 md:translate-y-12",
          )}
        >
          <Info size={12} />
          {$t("home.personalExperiment")}
        </a>
        <h1
          class={cn(
            "headline text-on-background lowercase origin-left leading-none",
            isShrink ? "text-4xl md:text-8xl" : "text-5xl md:text-9xl",
            "transition-all duration-500",
          )}
        >
          souscode.
        </h1>
      </div>
      <div class="flex items-center gap-2 mb-1 md:mb-3">
        <LanguageSwitcher />
        <HomeMenu />
      </div>
    </div>
  </header>

  <RecipeSearch bind:searchQuery class="mb-4 md:mb-8" />

  <RecipeGrid>
    {#each filteredRecipes as recipe}
      <RecipeGridItem {recipe} query={searchQuery} />
    {/each}
  </RecipeGrid>
</div>
