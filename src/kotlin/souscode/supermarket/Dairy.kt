package souscode.supermarket

import souscode.base.Ingredient
import souscode.base.Liquid
import souscode.base.Solid
import souscode.i18n.named
import souscode.unit.Count
import souscode.unit.Mass
import souscode.unit.Quantity
import souscode.unit.Volume

interface Dairy

class Milk(quantity: Volume) : Liquid(quantity), Dairy {
  override val name = named(enUS = "milk", zhCN = "牛奶")
}

class Cream(quantity: Volume) : Liquid(quantity), Dairy {
  override val name = named(enUS = "cream", zhCN = "奶油")
}

class Butter(quantity: Mass) : Solid(quantity), Dairy {
  override val name = named(enUS = "butter", zhCN = "黄油")
}

class EggYolk(val count: Count) : Ingredient(count), Dairy {
  override val name = named(enUS = "egg yolk", zhCN = "蛋黄")
  override val quantity: Quantity = count
}

class Egg(val count: Count) : Ingredient(count), Dairy {
  override val name = named(enUS = "egg", zhCN = "鸡蛋")
  override val quantity: Quantity = count
}

class Mozzarella(quantity: Mass) : Solid(quantity), Dairy {
  override val name = named(enUS = "mozzarella", zhCN = "马苏里拉")
}

class Cheddar(quantity: Mass) : Solid(quantity), Dairy {
  override val name = named(enUS = "cheddar", zhCN = "切达芝士")
}
