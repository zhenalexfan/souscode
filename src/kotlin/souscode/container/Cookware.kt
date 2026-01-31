package souscode.container

import souscode.i18n.named
import souscode.unit.Temperature
import souscode.unit.celsius

abstract class Cookware : Container()

class Pot : Cookware() {
  override val name = named(enUS = "pot", zhCN = "煮锅")
}

class Pan : Cookware() {
  override val name = named(enUS = "pan", zhCN = "煎锅")
}

class Wok : Cookware() {
  override val name = named(enUS = "wok", zhCN = "炒锅")
}

class BakingTray : Cookware() {
  override val name = named(enUS = "baking tray", zhCN = "烤盘")
}

class Oven(val preset: Temperature = 180.celsius) : Cookware() {
  override val name = named(enUS = "oven", zhCN = "烤箱")

  init {
    this.temperature = preset
    this.isHeated = true // Ovens are usually preheated in this context
  }
}
