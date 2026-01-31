package souscode.supermarket

import souscode.base.Liquid
import souscode.i18n.named
import souscode.unit.Volume

abstract class Sauce(quantity: Volume? = null) : Liquid(quantity)

class SoySauce(quantity: Volume) : Sauce(quantity) {
  override val name = named(enUS = "soy sauce", zhCN = "酱油")
}
