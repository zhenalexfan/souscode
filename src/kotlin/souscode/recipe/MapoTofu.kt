package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class MapoTofu : Recipe() {

  override fun execute() = serve {
    Wok().apply {
      heat(Intensity.High)

      // 1. Aromatics
      add(
        OliveOil(2.tbsp), // Cooking oil
        Doubanjiang(2.tbsp),
        Ginger(10.g),
        Garlic(10.g),
        SichuanPeppercorn(1.tsp),
      )
      wait(2.minutes)

      // 2. Meat
      add(Pork(150.g)) // Ground pork
      wait(3.minutes)

      // 3. Tofu & Sauce
      add(Tofu(400.g))
      add(SoySauce(1.tbsp), Sugar(1.tsp), Water(100.ml))

      heat(Intensity.Medium)
      wait(5.minutes)

      // 4. Finish
      add(GreenOnion(20.g))
    }
  }

  override val name = named(enUS = "mapo tofu", zhCN = "麻婆豆腐")
}
