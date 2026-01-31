package souscode.tools

import java.io.File
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import souscode.base.Recipe
import souscode.engine.RecipeContext
import souscode.engine.translator.InstructionLogger
import souscode.i18n.Locale
import souscode.i18n.Messages
import souscode.recipe.*

@Serializable
data class ContainerSteps(val container: String, val steps: List<String>)

@Serializable
data class RecipeExport(
  val id: String,
  val name: String,
  val ingredients: List<String>,
  val instructions: List<ContainerSteps>,
)

class RecipeWebExporter(
  private val locale: Locale = Locale.EN_US,
  private val rootDir: File = File("."),
) {

  private val json = Json { prettyPrint = true }

  fun generateJson(recipes: List<Recipe>): String {
    val messages = Messages(isMarked = true)
    val exportList =
      recipes.map { recipe ->
        val id = requireNotNull(recipe::class.simpleName)
        val displayName = recipe.name(locale)

        val context = RecipeContext()
        val logger = InstructionLogger(locale, marked = true)
        context.addListener(logger)

        val scope = RecipeContext.withContext(context) { recipe.execute() }

        // Ingredients
        val ingredients =
          scope.ingredients.map { ingredient -> ingredient.name(locale) }

        // Instructions
        val instructions =
          logger.compile().map { (container, steps) ->
            ContainerSteps(
              container = messages.renderMatter(container)(locale),
              steps = steps.map { it.description },
            )
          }

        RecipeExport(
          id = id,
          name = displayName,
          ingredients = ingredients,
          instructions = instructions,
        )
      }

    return json.encodeToString(exportList)
  }
}

fun main() {
  val recipeDir = File("src/kotlin/souscode/recipe")
  val recipes =
    recipeDir
      .listFiles { _, name -> name.endsWith(".kt") }
      ?.mapNotNull { file ->
        val className = file.nameWithoutExtension
        try {
          // Assume the class is in the souscode.recipe package and matches the
          // filename
          val clazz = Class.forName("souscode.recipe.$className")
          // Check if it's a subclass of Recipe (and not abstract)
          if (
            Recipe::class.java.isAssignableFrom(clazz) &&
              !java.lang.reflect.Modifier.isAbstract(clazz.modifiers)
          ) {
            clazz.getDeclaredConstructor().newInstance() as Recipe
          } else {
            null
          }
        } catch (e: Exception) {
          System.err.println(
            "Could not load recipe from ${file.name}: ${e.message}"
          )
          null
        }
      } ?: emptyList()

  println(
    "Found ${recipes.size} recipes: ${recipes.map { it::class.simpleName }}"
  )

  val locales = mapOf(Locale.EN_US to "en-US", Locale.ZH_CN to "zh-CN")

  val outputDir = File("web/src/data")
  if (!outputDir.exists()) {
    outputDir.mkdirs()
  }

  locales.forEach { (locale, suffix) ->
    val exporter = RecipeWebExporter(locale = locale, rootDir = File("."))
    val jsonOutput = exporter.generateJson(recipes)
    val outputFile = File(outputDir, "recipes_$suffix.json")
    outputFile.writeText(jsonOutput)
    println("Generated ${outputFile.absolutePath}")
  }
}
