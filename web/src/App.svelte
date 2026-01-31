<script lang="ts">
  import { onMount } from "svelte";
  import Home from "./lib/features/home/HomePage.svelte";
  import RecipeDetail from "./lib/features/recipe/RecipePage.svelte";
  import RecipePlayer from "./lib/features/player/RecipePlayerPage.svelte";
  import About from "./lib/features/about/AboutPage.svelte";

  let currentRoute = $state("home");
  let params = $state<{ id?: string }>({});

  function handleHashChange() {
    const hash = window.location.hash.slice(1);
    // Format: #/recipe/ID or #/about or #/
    if (hash.startsWith("/recipe/")) {
      currentRoute = "recipe";
      params.id = hash.split("/")[2];
    } else if (hash.startsWith("/player/")) {
      currentRoute = "player";
      params.id = hash.split("/")[2];
    } else if (hash === "/about") {
      currentRoute = "about";
    } else {
      currentRoute = "home";
    }
  }

  $effect(() => {
    handleHashChange();
    window.addEventListener("hashchange", handleHashChange);
    return () => window.removeEventListener("hashchange", handleHashChange);
  });
</script>

<div
  class="min-h-screen bg-background text-on-background font-sans selection:bg-accent selection:text-on-accent"
>
  {#if currentRoute === "home"}
    <Home />
  {:else if currentRoute === "recipe"}
    <RecipeDetail id={params.id!} />
  {:else if currentRoute === "player"}
    <RecipePlayer id={params.id!} />
  {:else if currentRoute === "about"}
    <About />
  {/if}
</div>
