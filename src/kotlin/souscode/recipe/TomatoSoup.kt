package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class TomatoSoup : Recipe() {

  override fun execute() = serve {
    Pot().apply {
      // Sauté aromatics
      heat(Intensity.Medium)
      add(OliveOil(1.tbsp), Onion(1.count), Garlic(2.tsp))
      wait(5.minutes) // Sauté

      // Add tomatoes and simmer
      add(Tomato(500.g), Water(500.ml))
      boil()
      wait(20.minutes)

      // Finish
      add(Cream(100.ml))
      whisk(Velocity.High) // Blend
    }
  }

  override val name = named(enUS = "tomato soup", zhCN = "番茄汤")
}
