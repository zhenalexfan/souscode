package souscode.base

import souscode.unit.Quantity
import souscode.unit.Volume

interface Cookable {
  fun cook(
    duration: souscode.unit.Duration,
    container: souscode.container.Container,
  )
}

@JsExport
abstract class Ingredient(open val quantity: Quantity? = null) : Matter()

@JsExport
abstract class Solid(override val quantity: Quantity? = null) :
  Ingredient(quantity)

@JsExport
abstract class Liquid(override val quantity: Volume? = null) :
  Ingredient(quantity)
