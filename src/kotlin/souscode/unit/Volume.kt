package souscode.unit

import souscode.base.JsExport
import souscode.base.JsName
import souscode.i18n.LocalizableString
import souscode.i18n.localized
import souscode.util.formatDecimals

@JsExport
enum class VolumeUnit(val symbol: String, val mlRatio: Double) {
  Ml("mL", 1.0),
  L("L", 1000.0),
  Cup("cup", 236.588),
  Tbsp("tbsp", 14.7868),
  Tsp("tsp", 4.92892),
}

@JsExport
data class Volume(val value: Number, val unit: VolumeUnit) : Quantity {
  val inMl: Double
    get() = value.toDouble() * unit.mlRatio

  override fun toString(): String = "${value}${unit.symbol}"

  operator fun plus(other: Volume): Volume =
    Volume(inMl + other.inMl, VolumeUnit.Ml)

  operator fun minus(other: Volume): Volume =
    Volume(inMl - other.inMl, VolumeUnit.Ml)

  operator fun times(factor: Number): Volume =
    Volume(value.toDouble() * factor.toDouble(), unit)

  operator fun div(factor: Number): Volume =
    Volume(value.toDouble() / factor.toDouble(), unit)

  override fun localized(): LocalizableString = localized(null)

  @JsName("localizedWithUnit")
  fun localized(preferredUnit: VolumeUnit?): LocalizableString {
    val targetUnit = preferredUnit ?: unit
    val displayValue =
      if (targetUnit == unit) value.toDouble() else inMl / targetUnit.mlRatio
    // Format to 2 decimal places if not an integer
    val formattedValue =
      if (displayValue == displayValue.toInt().toDouble()) {
        displayValue.toInt().toString()
      } else {
        displayValue.formatDecimals(2)
      }
    return localized { "$formattedValue ${targetUnit.symbol}" }
  }
}

val Number.ml: Volume
  get() = Volume(this, VolumeUnit.Ml)

val Number.l: Volume
  get() = Volume(this, VolumeUnit.L)

val Number.liter: Volume
  get() = l

val Number.cup: Volume
  get() = Volume(this, VolumeUnit.Cup)

val Number.tbsp: Volume
  get() = Volume(this, VolumeUnit.Tbsp)

val Number.tsp: Volume
  get() = Volume(this, VolumeUnit.Tsp)
