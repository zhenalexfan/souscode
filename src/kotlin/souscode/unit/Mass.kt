package souscode.unit

import souscode.base.JsExport
import souscode.base.JsName
import souscode.i18n.LocalizableString
import souscode.i18n.localized
import souscode.util.formatDecimals

@JsExport
enum class MassUnit(val symbol: String, val gRatio: Double) {
  G("g", 1.0),
  Kg("kg", 1000.0),
  Oz("oz", 28.35),
  Lb("lb", 453.592),
}

@JsExport
data class Mass(val value: Number, val unit: MassUnit) : Quantity {
  val inG: Double
    get() = value.toDouble() * unit.gRatio

  override fun toString(): String = "${value}${unit.symbol}"

  operator fun plus(other: Mass): Mass = Mass(inG + other.inG, MassUnit.G)

  operator fun minus(other: Mass): Mass = Mass(inG - other.inG, MassUnit.G)

  operator fun times(factor: Number): Mass =
    Mass(value.toDouble() * factor.toDouble(), unit)

  operator fun div(factor: Number): Mass =
    Mass(value.toDouble() / factor.toDouble(), unit)

  override fun localized(): LocalizableString = localized(null)

  @JsName("localizedWithUnit")
  fun localized(preferredUnit: MassUnit?): LocalizableString {
    val targetUnit = preferredUnit ?: unit
    val displayValue =
      if (targetUnit == unit) value.toDouble() else inG / targetUnit.gRatio
    val formattedValue =
      if (displayValue == displayValue.toInt().toDouble()) {
        displayValue.toInt().toString()
      } else {
        displayValue.formatDecimals(2)
      }
    return localized { "$formattedValue ${targetUnit.symbol}" }
  }
}

val Number.g: Mass
  get() = Mass(this, MassUnit.G)

val Number.kg: Mass
  get() = Mass(this, MassUnit.Kg)

val Number.oz: Mass
  get() = Mass(this, MassUnit.Oz)

val Number.lb: Mass
  get() = Mass(this, MassUnit.Lb)
