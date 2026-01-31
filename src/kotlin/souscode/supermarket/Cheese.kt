package souscode.supermarket

import souscode.base.Solid
import souscode.i18n.named
import souscode.unit.Mass

abstract class Cheese(quantity: Mass? = null) : Solid(quantity), Dairy

class Parmesan(quantity: Mass) : Cheese(quantity) {
  override val name = named(enUS = "parmesan", zhCN = "帕玛森芝士")
}

class Pecorino(quantity: Mass) : Cheese(quantity) {
  override val name = named(enUS = "pecorino", zhCN = "羊奶酪")
}
