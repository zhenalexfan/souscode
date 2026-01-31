package souscode.supermarket

import souscode.base.Liquid
import souscode.i18n.named
import souscode.unit.Volume

abstract class Oil(quantity: Volume? = null) : Liquid(quantity)

class OliveOil(quantity: Volume) : Oil(quantity) {
  override val name = named(enUS = "olive oil", zhCN = "橄榄油")
}
