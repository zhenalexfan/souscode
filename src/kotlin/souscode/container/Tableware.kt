package souscode.container

import souscode.i18n.named

abstract class Tableware : Container()

class Bowl : Tableware() {
  override val name = named(enUS = "bowl", zhCN = "碗")
}

class Plate : Tableware() {
  override val name = named(enUS = "plate", zhCN = "盘子")
}

class Cup : Tableware() {
  override val name = named(enUS = "cup", zhCN = "杯子")
}
