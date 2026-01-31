package souscode.unit

import souscode.base.JsExport
import souscode.i18n.LocalizableString
import souscode.i18n.localized

@JsExport
enum class TemperatureUnit {
  Celsius,
  Fahrenheit,
}

@JsExport
data class Temperature(val value: Number, val unit: TemperatureUnit) {
  val inCelsius: Double
    get() =
      if (unit == TemperatureUnit.Celsius) value.toDouble()
      else (value.toDouble() - 32) * 5 / 9

  val inFahrenheit: Double
    get() =
      if (unit == TemperatureUnit.Fahrenheit) value.toDouble()
      else (value.toDouble() * 9 / 5) + 32

  override fun toString(): String =
    "${value}${if (unit == TemperatureUnit.Celsius) "°C" else "°F"}"

  operator fun compareTo(other: Temperature): Int =
    inCelsius.compareTo(other.inCelsius)

  fun localized(preferredUnit: TemperatureUnit? = null): LocalizableString {
    val targetUnit = preferredUnit ?: unit
    val displayValue =
      if (targetUnit == TemperatureUnit.Celsius) inCelsius else inFahrenheit
    val unitStr = if (targetUnit == TemperatureUnit.Celsius) "°C" else "°F"
    return localized { "${displayValue.toInt()} $unitStr" }
  }
}

val Number.celsius: Temperature
  get() = Temperature(this, TemperatureUnit.Celsius)

val Number.fahrenheit: Temperature
  get() = Temperature(this, TemperatureUnit.Fahrenheit)
