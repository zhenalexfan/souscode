package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class SteamedRice : Recipe() {

  override fun execute() = serve {
    // Ratio 1:1.5 usually, or 1:2.
    Pot()
      .add(Rice(300.g), Water(450.ml))
      .boil()
      .heat(Intensity.Low) // Simmer
      .wait(15.minutes)
      .removeHeat()
      .wait(5.minutes) // Rest
  }

  override val name = named(enUS = "steamed rice", zhCN = "米饭")
}
