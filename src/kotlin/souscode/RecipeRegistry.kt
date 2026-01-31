package souscode

import souscode.base.JsExport
import souscode.base.Recipe
import souscode.recipe.*

@JsExport
object RecipeRegistry {
  fun getRecipes(): Array<Recipe> =
    arrayOf(
      AglioEOlio(),
      BraisedBeefNoodleSoup(),
      Bruschetta(),
      CapreseSalad(),
      Carbonara(),
      GarlicButterSalmon(),
      MacAndCheese(),
      MapoTofu(),
      OvenRoastedPotatoes(),
      ScrambledEggs(),
      SteamedRice(),
      TomatoSoup(),
    )

  fun getRecipeById(id: String): Recipe? {
    return getRecipes().find { it::class.simpleName == id }
  }
}
