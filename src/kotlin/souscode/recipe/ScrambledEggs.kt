package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class ScrambledEggs : Recipe() {

  override fun execute() = serve {
    val bowl =
      Bowl()
        .add(Egg(3.count), Salt(1.tsp), Pepper(0.5.tsp), Milk(30.ml))
        .whisk(Velocity.High)

    Pan()
      .heat(Intensity.Medium)
      .add(Butter(15.g))
      .transferContentFrom(bowl)
      .wait(5.minutes)
      .filter { it.state == souscode.base.State.Cooked }
  }

  override val name = named(enUS = "scrambled eggs", zhCN = "炒蛋")
}
