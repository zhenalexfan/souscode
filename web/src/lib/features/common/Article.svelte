<script lang="ts">
  import BackButton from "../common/BackButton.svelte";
  import { cn } from "../../shared/cn";

  let {
    title,
    copyright,
    children,
  }: {
    title: string;
    copyright: string;
    children: import("svelte").Snippet;
  } = $props();

  const brand = "Souscode";
  const year = new Date().getFullYear();
</script>

<div class={cn("p-8 max-w-4xl mx-auto min-h-screen flex flex-col")}>
  <header class={cn("mb-16 mt-8 flex items-center justify-between")}>
    <h1 class={cn("headline text-8xl text-on-background lowercase")}>
      {title}
    </h1>
    <BackButton
      href="/"
      variant="close"
      class="text-on-background hover:text-background hover:bg-on-background"
    />
  </header>

  <main
    class={cn(
      "flex-grow",
      "text-on-background",
      "prose prose-xl max-w-none",
      "prose-headings:font-bold prose-headings:text-on-background",
      "prose-h2:text-4xl prose-h2:mt-12 prose-h2:mb-6",
      "prose-p:text-xl prose-p:leading-relaxed",
      "prose-strong:font-black prose-strong:text-on-background",
      "prose-code:text-on-background",
      "prose-li:text-on-background",
      "[&_li::marker]:text-on-background",
      "prose-span:text-accent prose-span:lowercase prose-span:font-black",
    )}
  >
    {@render children()}
  </main>

  <footer class={cn("py-16 text-center opacity-60 text-sm")}>
    © {year}
    {brand} · {copyright}
  </footer>
</div>
