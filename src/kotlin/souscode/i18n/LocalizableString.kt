package souscode.i18n

import souscode.base.JsExport

@JsExport
interface LocalizableString {
  operator fun invoke(locale: Locale): String
}

@JsExport
enum class Locale {
  EN_US,
  ZH_CN;

  internal val LocalizableString.translated: String
    get() = this(this@Locale)

  internal val Named.translated: String
    get() = this.name(this@Locale)
}

fun localized(block: Locale.() -> String): LocalizableString {
  return object : LocalizableString {
    override fun invoke(locale: Locale): String = locale.block()
  }
}

fun <A> localized(block: Locale.(A) -> String): (A) -> LocalizableString {
  return { a ->
    object : LocalizableString {
      override fun invoke(locale: Locale): String = locale.block(a)
    }
  }
}

fun <A, B> localized(
  block: Locale.(A, B) -> String
): (A, B) -> LocalizableString {
  return { a, b ->
    object : LocalizableString {
      override fun invoke(locale: Locale): String = locale.block(a, b)
    }
  }
}

fun <A, B, C> localized(
  block: Locale.(A, B, C) -> String
): (A, B, C) -> LocalizableString {
  return { a, b, c ->
    object : LocalizableString {
      override fun invoke(locale: Locale): String = locale.block(a, b, c)
    }
  }
}

fun <A, B, C, D> localized(
  block: Locale.(A, B, C, D) -> String
): (A, B, C, D) -> LocalizableString {
  return { a, b, c, d ->
    object : LocalizableString {
      override fun invoke(locale: Locale): String = locale.block(a, b, c, d)
    }
  }
}
