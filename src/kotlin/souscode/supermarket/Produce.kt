package souscode.supermarket

import souscode.base.Solid
import souscode.i18n.named
import souscode.unit.Mass

abstract class Produce(quantity: Mass? = null) : Solid(quantity)

class Potato(quantity: Mass) : Produce(quantity) {
  override val name = named(enUS = "potato", zhCN = "土豆")
}

class Lettuce(quantity: Mass) : Produce(quantity) {
  override val name = named(enUS = "lettuce", zhCN = "生菜")
}

class Cucumber(quantity: Mass) : Produce(quantity) {
  override val name = named(enUS = "cucumber", zhCN = "黄瓜")
}

class BokChoy(quantity: Mass) : Produce(quantity) {
  override val name = named(enUS = "bok choy", zhCN = "小白菜")
}

class Ginger(quantity: Mass) : Produce(quantity) {
  override val name = named(enUS = "ginger", zhCN = "姜")
}

class GreenOnion(quantity: Mass) : Produce(quantity) {
  override val name = named(enUS = "green onion", zhCN = "葱")
}

class Lemon(val count: Int) : Produce(null) {
  override val name = named(enUS = "lemon", zhCN = "柠檬")
}
