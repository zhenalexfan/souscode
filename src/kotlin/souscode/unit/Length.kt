package souscode.unit

import souscode.base.JsExport

@JsExport
data class Length(val value: Number) {
  override fun toString(): String = "${value}cm"

  operator fun plus(other: Length): Length =
    Length(value.toDouble() + other.value.toDouble())

  operator fun minus(other: Length): Length =
    Length(value.toDouble() - other.value.toDouble())

  operator fun times(factor: Number): Length =
    Length(value.toDouble() * factor.toDouble())

  operator fun div(factor: Number): Length =
    Length(value.toDouble() / factor.toDouble())
}

val Number.cm: Length
  get() = Length(this)

val Number.m: Length
  get() = Length(this.toDouble() * 100)

val Number.inch: Length
  get() = Length(this.toDouble() * 2.54)

val Number.inches: Length
  get() = inch
