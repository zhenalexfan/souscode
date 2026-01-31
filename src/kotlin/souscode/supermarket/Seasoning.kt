package souscode.supermarket

import souscode.base.Solid
import souscode.i18n.named
import souscode.unit.Quantity

abstract class Seasoning(quantity: Quantity? = null) : Solid(quantity)

abstract class CrushableSeasoning(quantity: Quantity? = null) :
  Seasoning(quantity) {
  fun crush(): Seasoning {
    // Return self effectively, or a wrapped state if semantics required.
    // For now, simple chaining.
    return this
  }
}

class BlackPepper(quantity: Quantity) : CrushableSeasoning(quantity) {
  override val name = named(enUS = "black pepper", zhCN = "黑胡椒")
}

class Salt(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "salt", zhCN = "盐")
}

class Pepper(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "pepper", zhCN = "胡椒")
}

class Garlic(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "garlic", zhCN = "大蒜")
}

class Onion(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "onion", zhCN = "洋葱")
}

class Tomato(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "tomato", zhCN = "西红柿")
}

class Basil(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "basil", zhCN = "罗勒")
}

class Oregano(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "oregano", zhCN = "牛至")
}

class Rosemary(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "rosemary", zhCN = "迷迭香")
}

class Sugar(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "sugar", zhCN = "糖")
}

class ChiliFlakes(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "chili flakes", zhCN = "辣椒片")
}

class Doubanjiang(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "doubanjiang", zhCN = "豆瓣酱")
}

class SichuanPeppercorn(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "sichuan peppercorn", zhCN = "花椒")
}

class StarAnise(val count: Int) : Seasoning(null) {
  override val name = named(enUS = "star anise", zhCN = "八角")
}

class Cinnamon(val stickCount: Int) : Seasoning(null) {
  override val name = named(enUS = "cinnamon", zhCN = "肉桂")
}

class Parsley(quantity: Quantity) : Seasoning(quantity) {
  override val name = named(enUS = "parsley", zhCN = "欧芹")
}
