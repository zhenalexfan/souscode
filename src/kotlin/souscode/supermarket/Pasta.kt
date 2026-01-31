package souscode.supermarket

import souscode.base.Cookable
import souscode.base.Solid
import souscode.base.State
import souscode.container.Container
import souscode.i18n.named
import souscode.unit.Duration
import souscode.unit.Mass

abstract class Pasta(quantity: Mass? = null) : Solid(quantity), Cookable {
  override fun cook(duration: Duration, container: Container) {
    this.state = State.AlDente
  }
}

class Spaghetti(quantity: Mass) : Pasta(quantity) {
  override val name = named(enUS = "spaghetti", zhCN = "意大利面")
}

class Macaroni(quantity: Mass) : Pasta(quantity) {
  override val name = named(enUS = "macaroni", zhCN = "通心粉")
}
