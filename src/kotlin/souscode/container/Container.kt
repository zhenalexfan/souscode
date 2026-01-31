package souscode.container

import souscode.base.Cookable
import souscode.base.JsExport
import souscode.base.Matter
import souscode.unit.Duration
import souscode.unit.Intensity

@JsExport
abstract class Container : Matter(), Cookable {
  val contents: MutableList<Matter> = mutableListOf()
  var isHeated: Boolean = false
    internal set

  var currentIntensity: Intensity? = null
    internal set

  // Delegate cooking to contents
  override fun cook(duration: Duration, container: Container) {
    contents.forEach { item ->
      if (item is Cookable) {
        item.cook(duration, this)
      } else {
        item.state = souscode.base.State.Cooked
      }
    }
  }
}
