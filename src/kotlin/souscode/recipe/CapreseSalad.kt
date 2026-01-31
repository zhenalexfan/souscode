package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class CapreseSalad : Recipe() {

  override fun execute() = serve {
    Bowl()
      .add(Tomato(200.g), Mozzarella(150.g), Basil(10.g))
      .add(OliveOil(2.tbsp), Salt(0.5.tsp), BlackPepper(0.2.tsp))
      .mix()
  }

  override val name = named(enUS = "caprese salad", zhCN = "卡普雷塞沙拉")
}
