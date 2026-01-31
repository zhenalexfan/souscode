package souscode.util

actual class ThreadLocal<T> actual constructor() {
  private var value: T? = null

  actual fun get(): T? = value

  actual fun set(value: T?) {
    this.value = value
  }
}

actual fun Double.formatDecimals(decimals: Int): String =
  this.asDynamic().toFixed(decimals).toString()
