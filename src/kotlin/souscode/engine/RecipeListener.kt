package souscode.engine

import souscode.base.JsExport
import souscode.base.Matter
import souscode.container.Container
import souscode.unit.Duration
import souscode.unit.Intensity
import souscode.unit.Temperature
import souscode.unit.Velocity

@JsExport
interface RecipeListener {
  fun onAdd(container: Container, item: Matter) {}

  fun onAddAll(container: Container, items: List<Matter>) {}

  fun onBoil(container: Container) {}

  fun onHeat(container: Container, intensity: Intensity) {}

  fun onWhisk(container: Container, velocity: Velocity) {}

  fun onMix(container: Container) {}

  fun onRemoveHeat(container: Container, temperature: Temperature?) {}

  fun onFoldIn(container: Container, other: Container) {}

  fun onWait(container: Container, duration: Duration) {}

  fun onRoast(container: Container) {}

  fun onTransfer(source: Container, destination: Container) {}

  fun onTransferFrom(container: Container, source: Container) {}

  fun onCombine(container: Container, items: List<Matter>) {}

  fun onCombineContainers(
    combinedContainer: Container,
    originalContainer1: Container,
    originalContainer2: Container,
  ) {}

  fun onSubmerge(container: Container, item: Matter) {}
}
