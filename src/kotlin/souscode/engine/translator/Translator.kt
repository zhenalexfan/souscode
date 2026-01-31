package souscode.engine.translator

import souscode.base.Recipe
import souscode.engine.RecipeContext
import souscode.i18n.Locale

class Translator(val locale: Locale, val marked: Boolean = true) {
  fun translate(recipe: Recipe): String =
    with(locale) {
      val context = RecipeContext()
      val logger = InstructionLogger(locale, marked)
      context.addListener(logger)

      RecipeContext.withContext(context) { recipe.execute() }
      val instructions = logger.compile()

      // TODO: make it more readable
      val textInstructions =
        instructions.joinToString("\n") { (container, steps) ->
          "${container.name.translated}:\n${steps.joinToString("\n") { step -> "  ${step.description}" }}"
        }
      return "=== ${recipe.name.translated} ===\n$textInstructions\n====================\n"
    }
}
