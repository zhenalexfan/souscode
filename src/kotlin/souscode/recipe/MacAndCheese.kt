package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class MacAndCheese : Recipe() {

  override fun execute() = serve {
    // 1. Cook Pasta
    val pot =
      Pot()
        .add(Water(2.l))
        .boil()
        .add(Salt(1.tbsp))
        .add(Macaroni(300.g))
        .wait(8.minutes)
    // Assume drained.

    // 2. Make Sauce in separate pan
    val pan =
      Pan()
        .heat(Intensity.Medium)
        .add(Butter(30.g))
        .add(Flour(30.g))
        .whisk(Velocity.Medium) // Roux
        .add(Milk(300.ml))
        .wait(5.minutes) // Thicken
        .add(Cheddar(200.g))
        .add(Salt(0.5.tsp))
        .whisk(Velocity.Low) // Melt cheese

    // 3. Combine
    pan.transferContentFrom(pot) // Add pasta to sauce
  }

  override val name = named(enUS = "mac and cheese", zhCN = "芝士通心粉")
}
