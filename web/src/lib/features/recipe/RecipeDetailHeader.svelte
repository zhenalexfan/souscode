<script lang="ts">
  import { ChefHat } from "lucide-svelte";
  import IngredientFlexRow from "./IngredientFlexRow.svelte";
  import { t } from "../../shared/i18n/i18n";
  import { cn } from "../../shared/cn";
  import { toKebabCase } from "../../shared/utils";

  let { id, name, ingredients } = $props();
</script>

<div>
  <h1
    class="headline text-5xl md:text-6xl lg:text-8xl mb-4 md:mb-8 text-on-surface"
  >
    {name}
  </h1>
  <IngredientFlexRow {ingredients} recipeName={name} size="sm" class="mb-8" />
  <div class="flex gap-4">
    <a
      href="/player/{toKebabCase(id)}"
      class={cn(
        "bg-background text-on-background",
        "font-bold text-xs lowercase tracking-widest",
        "gap-2 px-6 py-3 rounded-full",
        "inline-flex items-center transform hover:scale-105 transition-transform duration-200 cursor-pointer hover:shadow-xl no-underline",
      )}
    >
      <ChefHat size={16} />
      {$t("recipe.startCooking")}
    </a>
  </div>
</div>
