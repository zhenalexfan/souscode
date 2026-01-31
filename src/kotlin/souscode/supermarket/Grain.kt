package souscode.supermarket

import souscode.base.Solid
import souscode.i18n.named
import souscode.unit.Mass
import souscode.unit.Quantity

abstract class Grain(quantity: Quantity? = null) : Solid(quantity)

class Flour(quantity: Mass) : Grain(quantity) {
  override val name = named(enUS = "flour", zhCN = "面粉")
}

class Rice(quantity: Mass) : Grain(quantity) {
  override val name = named(enUS = "rice", zhCN = "米")
}

class Bread(val slices: Int? = null) : Grain(null) {
  override val name = named(enUS = "bread", zhCN = "面包")
}

class Noodles(quantity: Mass) : Grain(quantity) {
  override val name = named(enUS = "noodles", zhCN = "面条")
}

class Tofu(quantity: Mass) : Solid(quantity) {
  override val name = named(enUS = "tofu", zhCN = "豆腐")
}
