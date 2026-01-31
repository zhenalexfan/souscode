package souscode.base

import souscode.i18n.LocalizableString
import souscode.i18n.Named
import souscode.i18n.localized
import souscode.unit.Temperature
import souscode.unit.celsius

/** Physical states that matter can be in during cooking */
@JsExport
enum class State {
  Raw,
  Cooked,
  Crispy,
  Boiling,
  Emulsified,
  Melted,
  Softened,
  AlDente,
  Rendered,
  Whisked,
  Combined,
}

/** Base class for all physical matter in the culinary domain */
@JsExport
abstract class Matter : Named {

  val id: String = "m${idCounter++}"
  var label: String? = null

  var state: State = State.Raw
    internal set

  var temperature: Temperature = 20.celsius
    internal set

  val labeledName: LocalizableString
    get() = label?.let { l -> localized { "${name(this)} $l" } } ?: name

  companion object {
    private var idCounter = 0
  }
}
