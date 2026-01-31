package souscode.unit

import souscode.base.JsExport
import souscode.i18n.Locale
import souscode.i18n.LocalizableString
import souscode.i18n.named

@JsExport
enum class Intensity : LocalizableString {
  Low,
  Medium,
  High;

  override fun invoke(locale: Locale): String {
    return when (this) {
      Low -> named("low", "低")(locale)
      Medium -> named("medium", "中")(locale)
      High -> named("high", "高")(locale)
    }
  }
}
