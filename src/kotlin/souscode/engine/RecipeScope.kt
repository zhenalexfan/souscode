package souscode.engine

import souscode.base.Cookable
import souscode.base.Ingredient
import souscode.base.JsExport
import souscode.base.JsName
import souscode.base.Matter
import souscode.base.State
import souscode.container.Bowl
import souscode.container.Container
import souscode.i18n.*
import souscode.unit.Duration
import souscode.unit.Intensity
import souscode.unit.Temperature
import souscode.unit.Velocity
import souscode.unit.celsius

@JsExport
class RecipeScope(
  val context: RecipeContext = RecipeContext.current() ?: RecipeContext()
) {
  val ingredients = mutableSetOf<Ingredient>()
  val containers = mutableListOf<Container>()

  // Boil operation
  fun Container.boil(): Container {
    this.state = State.Boiling
    this.temperature = 100.celsius
    this.isHeated = true
    context.notifyBoil(this)
    return this
  }

  // Add matter (Ingredient or Container)
  fun Container.add(item: Matter): Container {
    this.contents.add(item)
    if (this !in containers) {
      containers.add(this)
    }
    if (item is Ingredient) {
      ingredients.add(item)
    }
    context.notifyAdd(this, item)
    return this
  }

  @JsName("addAll")
  fun Container.add(vararg items: Matter): Container {
    context.silent {
      items.forEach { item ->
        this.contents.add(item)
        if (item is Ingredient) ingredients.add(item)
      }
    }
    context.notifyAddAll(this, items.toList())
    return this
  }

  // Submerge matter
  fun Container.submerge(item: Matter): Container {
    this.contents.add(item)
    if (item is Ingredient) ingredients.add(item)
    context.notifySubmerge(this, item)
    return this
  }

  // Heat with intensity
  fun Container.heat(intensity: Intensity): Container {
    this.isHeated = true
    this.currentIntensity = intensity
    context.notifyHeat(this, intensity)
    return this
  }

  // Combine matter
  fun Container.combine(vararg items: Matter): Container {
    context.silent { items.forEach { item -> this.add(item) } }
    context.notifyCombine(this, items.toList())
    return this
  }

  // Whisk with velocity
  fun Container.whisk(velocity: Velocity): Container {
    this.state = State.Whisked
    this.contents.forEach { it.state = State.Whisked }
    context.notifyWhisk(this, velocity)
    return this
  }

  // Mix contents
  fun Container.mix(): Container {
    this.state = State.Combined
    this.contents.forEach { it.state = State.Combined }
    context.notifyMix(this)
    return this
  }

  fun Container.removeHeat(
    settleAtTemperature: Temperature? = null
  ): Container {
    this.isHeated = false
    this.currentIntensity = null
    this.temperature = settleAtTemperature ?: 20.celsius
    context.notifyRemoveHeat(this, settleAtTemperature)
    return this
  }

  // Fold in another container with temperature check
  fun Container.foldIn(
    other: Container,
    temperatureCheck: (Temperature) -> Unit,
  ): Container {
    // Check temperature before folding
    val currentTemp = this.temperature
    temperatureCheck(currentTemp)

    // Combine contents
    this.contents.addAll(other.contents)
    other.contents.clear()
    this.state = State.Combined
    context.notifyFoldIn(this, other)
    return this
  }

  // Combine containers with + operator
  operator fun Container.plus(other: Container): Container {
    val combined = Bowl()
    combined.contents.addAll(this.contents)
    combined.contents.addAll(other.contents)
    this.contents.clear()
    other.contents.clear()

    combined.state = State.Combined
    // Average temperature when combining
    val avgTemp =
      (this.temperature.value.toDouble() + other.temperature.value.toDouble()) /
        2.0
    combined.temperature = avgTemp.toInt().celsius

    context.notifyCombineContainers(combined, this, other)
    return combined
  }

  // Wait for duration
  fun Container.wait(duration: Duration): Container {
    context.notifyWait(this, duration)
    // Update states of contents based on cooking
    this.contents.forEach { item ->
      // Use the item's own cooking logic if it exists, otherwise default to
      // Cooked
      if (item is Cookable) {
        item.cook(duration, this)
      } else {
        item.state = State.Cooked
      }
    }
    return this
  }

  fun Container.filter(condition: (Matter) -> Boolean): Container {
    val filtered = this.contents.filter { condition(it) }
    this.contents.clear()
    this.contents.addAll(filtered)
    return this
  }

  fun Container.roast(): Container {
    // Assuming the container is an Oven or inside one
    context.notifyRoast(this)
    return this
  }

  // Transfer contents TO another container
  fun Container.transferContentTo(destination: Container): Container {
    context.silent {
      destination.contents.addAll(this.contents)
      this.contents.clear()
    }
    context.notifyTransfer(this, destination)
    return this
  }

  // Transfer contents FROM another container
  fun Container.transferContentFrom(source: Container): Container {
    context.silent {
      this.contents.addAll(source.contents)
      source.contents.clear()
    }
    context.notifyTransferFrom(this, source)
    return this
  }
}

fun serve(
  context: RecipeContext? = null,
  block: RecipeScope.() -> Unit,
): RecipeScope {
  val effectiveContext = context ?: RecipeContext.current() ?: RecipeContext()
  val scope = RecipeScope(effectiveContext)
  scope.block()
  return scope
}
