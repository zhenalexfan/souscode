package souscode.tools

import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue
import souscode.base.Recipe
import souscode.engine.RecipeScope
import souscode.engine.serve
import souscode.i18n.localized

class RecipeWebExporterTest {

  // A dummy recipe for testing
  class TestRecipe : Recipe() {
    override val name = localized { "TestRecipe" }

    override fun execute(): RecipeScope {
      return serve {
        // No-op
      }
    }
  }

  @Test
  fun `generateJson produces valid json structure`() {
    val tempDir =
      createTempDir("souscode_test") // Note: Using deprecated for simplicity as in original
    try {
      // Setup a fake source file
      val recipeDir = File(tempDir, "src/kotlin/souscode/recipe")
      recipeDir.mkdirs()
      File(recipeDir, "TestRecipe.kt").writeText("val x = 1")

      val exporter = RecipeWebExporter(rootDir = tempDir)
      val json = exporter.generateJson(listOf(TestRecipe()))

      // Basic checks
      assertTrue(json.startsWith("["))
      assertTrue(json.endsWith("]"))
      assertTrue(json.contains("\"id\": \"TestRecipe\""))
      assertTrue(json.contains("\"name\": \"TestRecipe\""))
      // Instructions should now be a list of objects, not a plain string
      // We expect "instructions": [ ... ]
      assertTrue(json.contains("\"instructions\": ["))

      // Check if code was read
      assertTrue(json.contains("\"code\": \"val x = 1\""))
    } finally {
      tempDir.deleteRecursively()
    }
  }

  @Test
  fun `generateJson handles missing code file`() {
    val tempDir = createTempDir("souscode_test")
    try {
      // No file created

      val exporter = RecipeWebExporter(rootDir = tempDir)
      val json = exporter.generateJson(listOf(TestRecipe()))

      assertTrue(json.contains("\"code\": \"Code not found\""))
    } finally {
      tempDir.deleteRecursively()
    }
  }
}
