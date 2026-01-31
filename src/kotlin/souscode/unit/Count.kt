package souscode.unit

import souscode.base.JsExport
import souscode.i18n.LocalizableString
import souscode.i18n.localized

@JsExport
data class Count(val value: Number) : Quantity {
  override fun toString(): String = "${value.toInt()}"

  operator fun plus(other: Count): Count =
    Count(value.toDouble() + other.value.toDouble())

  operator fun minus(other: Count): Count =
    Count(value.toDouble() - other.value.toDouble())

  operator fun times(factor: Number): Count =
    Count(value.toDouble() * factor.toDouble())

  operator fun div(factor: Number): Count =
    Count(value.toDouble() / factor.toDouble())

  override fun localized(): LocalizableString {
    return localized { value.toInt().toString() }
  }
}

val Number.count: Count
  get() = Count(this)

val Number.piece: Count
  get() = Count(this)

val Number.item: Count
  get() = Count(this)
