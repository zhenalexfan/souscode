package souscode.i18n

import souscode.base.Ingredient
import souscode.base.JsExport
import souscode.base.Matter
import souscode.container.Container
import souscode.container.Cookware
import souscode.container.Tableware
import souscode.unit.Duration
import souscode.unit.Intensity
import souscode.unit.Temperature
import souscode.unit.Velocity

object Common {
  val ingredientWithQuantity = localized { ingredient: Ingredient ->
    val qty = ingredient.quantity?.localized()?.translated
    val name = ingredient.name.translated
    when (this) {
      Locale.EN_US -> "${qty ?: "some"} $name"
      Locale.ZH_CN -> "${qty ?: "一些"}$name"
    }
  }

  val itemized = localized { items: Iterable<String> ->
    when (this) {
      Locale.EN_US -> items.joinToString(", ")
      Locale.ZH_CN -> items.joinToString("、")
    }
  }
}

val Matter.quantityName: LocalizableString
  get() = let { matter ->
    localized {
      when (matter) {
        is Ingredient -> Common.ingredientWithQuantity(matter).translated
        else -> matter.name.translated
      }
    }
  }

@JsExport
class Messages(val isMarked: Boolean) {

  private fun Matter.toSousMark(locale: Locale): String {
    val original =
      when (this) {
        is Ingredient -> Common.ingredientWithQuantity(this)(locale)
        else -> {
          val baseName = this.name(locale)
          if (this.label != null) "$baseName ${this.label}" else baseName
        }
      }

    if (!isMarked) return original

    return when (this) {
      is Ingredient -> "<ingredient id=\"$id\">$original</ingredient>"
      is Cookware -> "<container cookware id=\"$id\">$original</container>"
      is Tableware -> "<container tableware id=\"$id\">$original</container>"
      else -> "<container id=\"$id\">$original</container>"
    }
  }

  fun renderMatter(item: Matter) = localized { item.toSousMark(this) }

  fun bringToBoil() = localized {
    when (this) {
      Locale.EN_US -> "Bring to a boil"
      Locale.ZH_CN -> "煮沸"
    }
  }

  fun addToContainer(item: Matter) = localized {
    val itemName = item.toSousMark(this)
    when (this) {
      Locale.EN_US -> "Add $itemName"
      Locale.ZH_CN -> "往里放$itemName"
    }
  }

  fun addAllToContainer(items: Iterable<Matter>) = localized {
    val names = items.map { item -> item.toSousMark(this) }
    val namesItemized = Common.itemized(names).translated
    when (this) {
      Locale.EN_US -> "Add $namesItemized"
      Locale.ZH_CN -> "往里放$namesItemized"
    }
  }

  fun submergeInContainer(item: Matter) = localized {
    val itemName = item.toSousMark(this)
    when (this) {
      Locale.EN_US -> "Submerge $itemName"
      Locale.ZH_CN -> "把${itemName}浸入"
    }
  }

  fun heatContainer(intensity: Intensity) = localized {
    val intensityName = intensity.translated
    when (this) {
      Locale.EN_US -> "Heat at $intensityName intensity"
      Locale.ZH_CN -> "开${intensityName}火"
    }
  }

  fun whiskContainer(velocity: Velocity) = localized {
    val velocityName = velocity.translated
    when (this) {
      Locale.EN_US -> "Whisk contents at $velocityName velocity"
      Locale.ZH_CN -> "${velocityName}速搅拌"
    }
  }

  fun mixContainer() = localized {
    when (this) {
      Locale.EN_US -> "Mix contents"
      Locale.ZH_CN -> "搅拌"
    }
  }

  fun removeHeatForContainer(temp: Temperature?) = localized {
    val tempStr = temp?.localized()?.translated
    when (this) {
      Locale.EN_US ->
        "Remove heat" + if (temp == null) "" else " and cool to $tempStr"
      Locale.ZH_CN -> "停止加热" + if (temp == null) "" else "并降至$tempStr"
    }
  }

  fun foldInContainer(container: Container, other: Container) = localized {
    val name = container.toSousMark(this)
    val otherName = other.toSousMark(this)
    when (this) {
      Locale.EN_US -> "Fold $otherName into $name"
      Locale.ZH_CN -> "把${otherName}里的东西拌入${name}里"
    }
  }

  fun combineContainers(c1: Container, c2: Container) = localized {
    val name1 = c1.toSousMark(this)
    val name2 = c2.toSousMark(this)
    when (this) {
      Locale.EN_US -> "Combine $name1 and $name2"
      Locale.ZH_CN -> "混合${name1}和${name2}里的东西"
    }
  }

  fun waitForDuration(duration: Duration) = localized {
    when (this) {
      Locale.EN_US -> "Wait ${duration.translated}"
      Locale.ZH_CN -> "等${duration.translated}"
    }
  }

  fun roastContents(temp: Temperature) = localized {
    val tempStr = temp.localized().translated
    when (this) {
      Locale.EN_US -> "Roast contents at $tempStr"
      Locale.ZH_CN -> "在${tempStr}下烘烤"
    }
  }

  fun transferTo(source: Container, dest: Container) = localized {
    val sourceName = source.toSousMark(this)
    val destName = dest.toSousMark(this)
    when (this) {
      Locale.EN_US -> "Transfer contents of $sourceName to $destName"
      Locale.ZH_CN -> "把${sourceName}里的东西移到${destName}里"
    }
  }

  fun transferFrom(source: Container) = localized {
    val sourceName = source.toSousMark(this)
    when (this) {
      Locale.EN_US -> "Transfer contents from $sourceName"
      Locale.ZH_CN -> "把${sourceName}里的东西移进来"
    }
  }

  fun done() = localized {
    when (this) {
      Locale.EN_US -> "Done"
      Locale.ZH_CN -> "大功告成"
    }
  }
}
