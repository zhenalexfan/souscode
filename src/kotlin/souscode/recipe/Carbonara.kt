package souscode.recipe

import souscode.base.Recipe
import souscode.base.State
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class Carbonara : Recipe() {

  override fun execute() = serve {
    val pasta =
      Pot()
        .apply {
          add(Water(100.ml))
          boil()
          add(Salt(10.g))
          submerge(Spaghetti(200.g))
        }
        .wait(10.minutes)
        .filter { it.state == State.AlDente }

    val base =
      Pan().apply {
        heat(Intensity.Medium)
        add(Guanciale(5.g).dice())
      }

    val binder =
      Bowl()
        .add(EggYolk(3.count), Pecorino(50.g), BlackPepper(1.g).crush())
        .whisk(Velocity.High)

    // The Critical Path (The Merge)
    (base + pasta).removeHeat().foldIn(binder) { temp ->
      check(temp < 65.celsius) {
        "EMULSIFICATION_FAILURE: Scrambled eggs detected"
      }
    }
  }

  override val name = named(enUS = "carbonara", zhCN = "奶油培根意面")
}
