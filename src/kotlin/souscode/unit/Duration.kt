package souscode.unit

import souscode.base.JsExport
import souscode.i18n.Locale
import souscode.i18n.LocalizableString
import souscode.i18n.Named
import souscode.i18n.localized

@JsExport
data class Duration(val value: Number) : Named {
  override val name: LocalizableString = localized {
    when (this) {
      Locale.EN_US -> if (value == 1) "$value minute" else "$value minutes"
      Locale.ZH_CN -> "${value}分钟"
    }
  }

  override fun toString(): String = "${value} minutes"

  operator fun plus(other: Duration): Duration =
    Duration(value.toDouble() + other.value.toDouble())

  operator fun minus(other: Duration): Duration =
    Duration(value.toDouble() - other.value.toDouble())

  operator fun times(factor: Number): Duration =
    Duration(value.toDouble() * factor.toDouble())

  operator fun div(factor: Number): Duration =
    Duration(value.toDouble() / factor.toDouble())

  val inSeconds: Double
    get() = value.toDouble() * 60

  val inHours: Double
    get() = value.toDouble() / 60
}

val Number.seconds: Duration
  get() = Duration(this.toDouble() / 60)

val Number.minutes: Duration
  get() = Duration(this)

val Number.hours: Duration
  get() = Duration(this.toDouble() * 60)
