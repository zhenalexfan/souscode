package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class AglioEOlio : Recipe() {

  override fun execute() = serve {
    // 1. Boil Pasta
    val pot =
      Pot()
        .add(Water(1.l))
        .boil()
        .add(Salt(1.tbsp))
        .add(Spaghetti(200.g))
        .wait(10.minutes) // Cook pasta

    // 2. Prepare Sauce
    val pan =
      Pan()
        .heat(Intensity.Low)
        .add(OliveOil(60.ml), Garlic(4.tsp), ChiliFlakes(1.tsp))
        .wait(3.minutes) // Infuse oil

    // 3. Combine
    // Transfer pasta only? We don't have a strainer tool.
    // Let's assume transferContentFrom moves everything including water, which
    // isn't ideal for
    // Aglio e Olio but technically correct for the framework.
    // Or we can manually remove water? "filter" method?
    // "Drain" isn't an action.
    // Let's just transfer all for now, simulating adding pasta with some pasta
    // water.
    pan.transferContentFrom(pot).whisk(Velocity.Medium) // Emulsify
  }

  override val name = named(enUS = "aglio e olio", zhCN = "蒜油意面")
}
