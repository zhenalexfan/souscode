package souscode.unit

import souscode.base.JsExport
import souscode.i18n.LocalizableString
import souscode.i18n.Named

@JsExport
interface Quantity : Named {
  fun localized(): LocalizableString

  override val name: LocalizableString
    get() = localized()
}
