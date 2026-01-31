import { derived } from "svelte/store";
import recipesEn from "../../../data/recipes_en-US.json";
import recipesZh from "../../../data/recipes_zh-CN.json";
import { locale } from "../../shared/i18n/i18n";
import type { Recipe } from "../../shared/types";

const dataMap = {
  "en-US": recipesEn,
  "zh-CN": recipesZh,
};

const recipeCodes = import.meta.glob(
  "../../../../../src/kotlin/souscode/recipe/*.kt",
  {
    query: "?raw",
    eager: true,
    import: "default",
  },
);

export const recipes = derived(locale, ($locale) => {
  const baseRecipes = dataMap[$locale] as Recipe[];
  return baseRecipes.map((recipe) => {
    const codePath = `../../../../../src/kotlin/souscode/recipe/${recipe.id}.kt`;
    return {
      ...recipe,
      code: (recipeCodes[codePath] as string) || "Code not found",
    };
  });
});

export function getRecipe(
  id: string,
  recipeList: Recipe[],
): Recipe | undefined {
  return recipeList.find((r) => r.id === id);
}
