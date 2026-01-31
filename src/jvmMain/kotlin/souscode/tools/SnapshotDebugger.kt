package souscode.tools

import souscode.engine.snapshot.SnapshotLogger
import souscode.i18n.Locale
import souscode.recipe.Carbonara

fun main() {
  val recipe = Carbonara()
  println("Running recipe: ${recipe::class.simpleName}")

  val steps =
    SnapshotLogger(Locale.EN_US, true).run {
      record(recipe)
      getSteps()
    }
  println("Total steps recorded: ${steps.size}")

  steps.forEachIndexed { index, step ->
    println(
      "\nStep ${index + 1}: ${step.containerName} -> ${step.instruction.description}"
    )
    println("Snapshots involved (${step.snapshots.size}):")
    step.snapshots.forEach { snapshot ->
      println("  - Container: ${snapshot.name} (ID: ${snapshot.id})")
      println("    Contents: ${snapshot.contents.map { it.name }}")
    }
  }
}
