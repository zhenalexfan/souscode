package souscode.i18n

import souscode.base.JsExport

@JsExport
interface Named {
  val name: LocalizableString
}

@JsExport
fun named(enUS: String, zhCN: String? = null): LocalizableString {
  return localized {
    when (this) {
      Locale.EN_US -> enUS
      Locale.ZH_CN -> zhCN ?: enUS
    }
  }
}
