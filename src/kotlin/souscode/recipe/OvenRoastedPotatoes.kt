package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class OvenRoastedPotatoes : Recipe() {

  override fun execute() = serve {
    // 1. Prepare mixture
    val mixture =
      Bowl()
        .add(
          Potato(500.g),
          OliveOil(2.tbsp),
          Salt(1.tsp),
          BlackPepper((0.5).tsp).crush(),
          Rosemary(1.tbsp),
          Garlic(1.tbsp),
        )
        .whisk(Velocity.Low)

    // 2. Transfer to Tray
    val tray = BakingTray().transferContentFrom(mixture)

    // 3. Roast in Oven
    Oven(400.fahrenheit).apply {
      add(tray)
      roast()
      wait(25.minutes)
    }
  }

  override val name = named(enUS = "oven roasted potatoes", zhCN = "烤土豆")
}
