package souscode.util

expect class ThreadLocal<T>() {
  fun get(): T?

  fun set(value: T?)
}

expect fun Double.formatDecimals(decimals: Int): String
