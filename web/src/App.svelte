<script lang="ts">
  import { onMount } from "svelte";
  import Home from "./lib/features/home/HomePage.svelte";
  import RecipeDetail from "./lib/features/recipe/RecipePage.svelte";
  import RecipePlayer from "./lib/features/player/RecipePlayerPage.svelte";
  import About from "./lib/features/about/AboutPage.svelte";
  import { toPascalCase } from "./lib/shared/utils";

  let currentRoute = $state("home");
  let params = $state<{ id?: string }>({});

  function handlePopState() {
    const path = window.location.pathname;

    if (path.startsWith("/recipe/")) {
      currentRoute = "recipe";
      const slug = path.split("/")[2];
      params.id = toPascalCase(slug);
    } else if (path.startsWith("/player/")) {
      currentRoute = "player";
      const slug = path.split("/")[2];
      params.id = toPascalCase(slug);
    } else if (path === "/about") {
      currentRoute = "about";
    } else {
      currentRoute = "home";
    }
  }

  function navigateTo(url: string) {
    history.pushState(null, "", url);
    handlePopState();
  }

  onMount(() => {
    handlePopState();
    window.addEventListener("popstate", handlePopState);

    // Global click listener for typed links
    const handleClick = (e: MouseEvent) => {
      const target = (e.target as HTMLElement).closest("a");
      if (target && target.href.startsWith(window.location.origin)) {
        const path = target.href.replace(window.location.origin, "");
        if (!path.startsWith("#")) {
          e.preventDefault();
          navigateTo(path);
        }
      }
    };

    document.addEventListener("click", handleClick);

    return () => {
      window.removeEventListener("popstate", handlePopState);
      document.removeEventListener("click", handleClick);
    };
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
