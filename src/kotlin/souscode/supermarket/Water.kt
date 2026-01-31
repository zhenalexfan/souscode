package souscode.supermarket

import souscode.base.Liquid
import souscode.i18n.named
import souscode.unit.Volume

/** Water ingredient */
class Water(quantity: Volume) : Liquid(quantity) {
  override val name = named(enUS = "water", zhCN = "水")
}
