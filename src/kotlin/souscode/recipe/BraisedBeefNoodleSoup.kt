package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class BraisedBeefNoodleSoup : Recipe() {

  override fun execute() = serve {
    Pot().apply {
      // 1. Sear & Braise Beef
      heat(Intensity.High)
      add(
        BeefShank(500.g),
        OliveOil(15.ml),
        Ginger(20.g),
        GreenOnion(20.g),
        StarAnise(2),
        Cinnamon(1), // 1 stick
      )
      wait(5.minutes) // Sear

      // 2. Add liquids and simmer
      add(SoySauce(50.ml), Doubanjiang(1.tbsp), Sugar(1.tbsp), Water(2.l))

      boil()
      wait(90.minutes) // Braise

      // 3. Add Noodles and Veg
      add(Noodles(300.g))
      add(BokChoy(200.g))
      wait(5.minutes)
    }
  }

  override val name = named(enUS = "braised beef noodle soup", zhCN = "红烧牛肉面")
}
