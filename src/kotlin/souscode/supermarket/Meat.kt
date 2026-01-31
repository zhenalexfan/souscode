package souscode.supermarket

import souscode.base.Solid
import souscode.base.State
import souscode.i18n.named
import souscode.unit.Mass

abstract class Protein(quantity: Mass? = null) : Solid(quantity)

class Guanciale(quantity: Mass) : Protein(quantity) {
  override val name = named(enUS = "guanciale", zhCN = "关契亚勒")

  fun dice(): Guanciale {
    this.state = State.Softened
    return this
  }
}

class Pork(quantity: Mass) : Protein(quantity) {
  override val name = named(enUS = "pork", zhCN = "猪肉")
}

class Beef(quantity: Mass) : Protein(quantity) {
  override val name = named(enUS = "beef", zhCN = "牛肉")
}

class BeefShank(quantity: Mass) : Protein(quantity) {
  override val name = named(enUS = "beef shank", zhCN = "牛腱子")
}

class Salmon(quantity: Mass) : Protein(quantity) {
  override val name = named(enUS = "salmon", zhCN = "三文鱼")
}
