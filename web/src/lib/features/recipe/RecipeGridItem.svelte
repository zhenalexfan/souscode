<script lang="ts">
  import type { Recipe } from "../../shared/types";
  import IngredientFlexRow from "./IngredientFlexRow.svelte";

  let {
    recipe,
    query = "",
  }: {
    recipe: Recipe;
    query?: string;
  } = $props();

  let nameParts = $derived.by(() => {
    if (!query) return [{ text: recipe.name, matches: false }];

    const escapedQuery = query.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
    const regex = new RegExp(`(${escapedQuery})`, "gi");

    return recipe.name
      .split(regex)
      .map((part) => ({
        text: part,
        matches: part.toLowerCase() === query.toLowerCase(),
      }))
      .filter((part) => part.text !== "");
  });
</script>

<a
  href="#/recipe/{recipe.id}"
  class="block group [perspective:1000px] active:scale-95 transition-transform duration-200"
>
  <div
    class="bg-surface rounded-[2rem] p-8 h-[18rem] flex flex-col justify-between
           transition-all duration-500 ease-[cubic-bezier(0.23,1,0.32,1)]
           group-hover:[transform:rotateX(5deg)_translateY(-8px)]
           group-hover:bg-highlighted-container
           relative overflow-hidden"
  >
    <div class="relative z-10 group-hover:opacity-100">
      <h2 class="headline text-5xl mb-6 text-on-surface">
        {#each nameParts as part}
          {#if part.matches}
            <span class="bg-highlighted text-on-highlighted">{part.text}</span>
          {:else}
            {part.text}
          {/if}
        {/each}
      </h2>

      <IngredientFlexRow
        ingredients={recipe.ingredients}
        recipeName={recipe.name}
        limit={4}
        {query}
      />
    </div>

    <!-- dynamic background blob -->
    <div
      class="absolute -bottom-24 -right-24 w-64 h-64 rounded-full blur-3xl opacity-10 group-hover:opacity-30 transition-opacity duration-500 bg-[#ffe0b4]"
    ></div>
  </div>
</a>
