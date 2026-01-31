package souscode.util

actual class ThreadLocal<T> actual constructor() {
  private val jvmThreadLocal = java.lang.ThreadLocal<T>()

  actual fun get(): T? = jvmThreadLocal.get()

  actual fun set(value: T?) = jvmThreadLocal.set(value)
}

actual fun Double.formatDecimals(decimals: Int): String =
  java.lang.String.format("%.${decimals}f", this)
