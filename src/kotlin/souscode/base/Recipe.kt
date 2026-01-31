package souscode.base

import souscode.engine.RecipeScope

@JsExport
abstract class Recipe : Ingredient() {
  abstract fun execute(): RecipeScope
}
