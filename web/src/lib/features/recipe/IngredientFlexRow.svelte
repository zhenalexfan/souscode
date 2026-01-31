<script lang="ts">
  import Popover from "../../shared/components/Popover.svelte";
  import { cn } from "../../shared/cn";
  import { fade } from "svelte/transition";
  import { t } from "../../shared/i18n/i18n";

  let {
    ingredients = [],
    recipeName,
    limit,
    query = "",
    size = "sm",
    class: className = "",
  }: {
    ingredients: string[];
    recipeName: string;
    limit?: number;
    query?: string;
    size?: "sm" | "lg";
    class?: string;
  } = $props();

  let isPopoverOpen = $state(false);
  let moreButton = $state<HTMLElement | null>(null);
  let closeTimeout: ReturnType<typeof setTimeout> | null = null;

  function handleMouseEnter() {
    if (closeTimeout) clearTimeout(closeTimeout);
    isPopoverOpen = true;
  }

  function handleMouseLeave() {
    closeTimeout = setTimeout(() => {
      isPopoverOpen = false;
    }, 150);
  }

  let processedIngredients = $derived.by(() => {
    let items = ingredients.map((name) => ({
      name,
      matches: query ? name.toLowerCase().includes(query.toLowerCase()) : false,
    }));

    if (query) {
      // Sort: matches first, then original order
      items = [...items].sort((a, b) => {
        if (a.matches && !b.matches) return -1;
        if (!a.matches && b.matches) return 1;
        return 0;
      });
    }

    return items;
  });

  let visibleIngredients = $derived(
    limit && processedIngredients.length > limit
      ? processedIngredients.slice(0, limit)
      : processedIngredients,
  );

  let moreCount = $derived(
    Math.max(0, processedIngredients.length - (limit ?? Number.MAX_VALUE)),
  );
  // If there's only 1 more ingredient, don't show the button because
  // we could use the button's space to show the ingredient name
  let hasMore = $derived(moreCount > 1);
</script>

{#snippet ingredientChip(
  name: string,
  matches: boolean,
  chipSize: "xs" | "sm" | "lg",
)}
  <span
    class={cn(
      "rounded-xl font-bold transition-all duration-300",
      chipSize === "lg" ? "px-6 py-4 text-xl rounded-2xl" : "px-2 py-1",
      chipSize === "sm" && "text-sm",
      chipSize === "xs" && "text-xs",
      matches
        ? "bg-highlighted text-on-highlighted scale-105"
        : "bg-primary-container text-on-primary-container",
    )}
  >
    {name}
  </span>
{/snippet}

<div class="flex flex-wrap gap-2 relative {className}">
  {#each visibleIngredients as { name, matches }}
    {@render ingredientChip(name, matches, size)}
  {/each}

  {#if hasMore}
    <div class="inline-block">
      <button
        bind:this={moreButton}
        onmouseenter={handleMouseEnter}
        onmouseleave={handleMouseLeave}
        onclick={(e) => {
          e.stopPropagation();
          e.preventDefault();
        }}
        class="px-2 py-1 bg-primary-container/40 text-on-primary-container/50 rounded-xl text-sm font-bold cursor-default hover:bg-primary-container hover:text-on-primary-container transition-all duration-200 border border-dashed border-on-primary-container/20 whitespace-nowrap {isPopoverOpen
          ? 'bg-primary-container text-on-primary-container'
          : ''}"
      >
        {$t("recipe.viewMoreIngredients", { count: moreCount })}
      </button>

      <Popover
        anchor={moreButton}
        open={isPopoverOpen}
        side="bottom"
        align="left"
        onmouseenter={handleMouseEnter}
        onmouseleave={handleMouseLeave}
        class="px-6 py-4 bg-surface-variant backdrop-blur-xl rounded-2xl min-w-[280px] max-w-sm"
        transition={fade}
        transitionParams={{ duration: 200 }}
      >
        <div class="flex flex-col gap-4">
          <div class={cn("flex justify-between items-center text-on-surface")}>
            <span class="font-black lowercase">{recipeName}</span>
            <span class="text-xs font-bold"
              >{$t("recipe.totalIngredients", {
                count: ingredients.length,
              })}</span
            >
          </div>
          <div class="flex flex-wrap gap-2">
            {#each processedIngredients as { name, matches }}
              {@render ingredientChip(name, matches, "xs")}
            {/each}
          </div>
        </div>
      </Popover>
    </div>
  {/if}
</div>
