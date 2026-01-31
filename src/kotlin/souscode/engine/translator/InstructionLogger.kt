package souscode.engine.translator

import souscode.base.JsExport
import souscode.base.Matter
import souscode.container.Container
import souscode.engine.RecipeListener
import souscode.i18n.Locale
import souscode.i18n.LocalizableString
import souscode.i18n.Messages
import souscode.unit.Duration
import souscode.unit.Intensity
import souscode.unit.Temperature
import souscode.unit.Velocity
import souscode.util.SousMarkUtil

@JsExport
open class InstructionLogger(
  protected val locale: Locale = Locale.EN_US,
  protected val marked: Boolean = true,
) : RecipeListener {
  protected val messages = Messages(true)

  private var currentContainer: Container? = null
  private var currentSteps = mutableListOf<InstructionStep>()

  internal val records =
    mutableListOf<Pair<Container, MutableList<InstructionStep>>>()

  protected val seenContainers = mutableSetOf<Container>()

  protected open fun log(container: Container, step: InstructionStep) {
    seenContainers.add(container)
    if (container == currentContainer) {
      currentSteps.add(step)
      return
    }

    saveCurrentContainer()
    currentContainer = container
    currentSteps = mutableListOf(step)
  }

  protected fun step(
    category: InstructionStepCategory,
    instruction: (Messages) -> LocalizableString,
  ) = InstructionStep(category, instruction(messages)(locale))

  override fun onAdd(container: Container, item: Matter) {
    if (item is Container) seenContainers.add(item)
    log(container, step(Add) { it.addToContainer(item) })
  }

  override fun onAddAll(container: Container, items: List<Matter>) {
    items.forEach { if (it is Container) seenContainers.add(it) }
    log(container, step(Add) { it.addAllToContainer(items) })
  }

  override fun onBoil(container: Container) {
    log(container, step(Heat()) { it.bringToBoil() })
  }

  override fun onHeat(container: Container, intensity: Intensity) {
    log(container, step(Heat(intensity)) { it.heatContainer(intensity) })
  }

  override fun onWhisk(container: Container, velocity: Velocity) {
    log(container, step(Mix) { it.whiskContainer(velocity) })
  }

  override fun onMix(container: Container) {
    log(container, step(Mix) { it.mixContainer() })
  }

  override fun onRemoveHeat(container: Container, temperature: Temperature?) {
    log(container, step(Heat()) { it.removeHeatForContainer(temperature) })
  }

  override fun onFoldIn(container: Container, other: Container) {
    seenContainers.add(other)
    log(container, step(Mix) { it.foldInContainer(container, other) })
  }

  override fun onWait(container: Container, duration: Duration) {
    log(container, step(Wait(duration)) { it.waitForDuration(duration) })
  }

  override fun onRoast(container: Container) {
    log(container, step(Heat()) { it.roastContents(container.temperature) })
  }

  override fun onTransfer(source: Container, destination: Container) {
    seenContainers.add(destination)
    log(source, step(Transfer) { it.transferTo(source, destination) })
  }

  override fun onTransferFrom(container: Container, source: Container) {
    seenContainers.add(source)
    log(container, step(Transfer) { it.transferFrom(source) })
  }

  override fun onCombine(container: Container, items: List<Matter>) {
    items.forEach { if (it is Container) seenContainers.add(it) }
    log(container, step(Add) { it.addAllToContainer(items) })
    log(container, step(Mix) { it.mixContainer() })
  }

  override fun onCombineContainers(
    combinedContainer: Container,
    originalContainer1: Container,
    originalContainer2: Container,
  ) {
    seenContainers.addAll(
      listOf(combinedContainer, originalContainer1, originalContainer2)
    )
    log(
      combinedContainer,
      step(Mix) { it.combineContainers(originalContainer1, originalContainer2) },
    )
  }

  override fun onSubmerge(container: Container, item: Matter) {
    if (item is Container) seenContainers.add(item)
    log(container, step(Add) { it.submergeInContainer(item) })
  }

  fun compile(): List<Pair<Container, List<InstructionStep>>> {
    saveCurrentContainer()

    // 1. Assign labels to duplicate containers
    seenContainers
      .groupBy { it.name(Locale.EN_US) }
      .forEach { (_, instances) ->
        if (instances.size > 1) {
          instances.forEachIndexed { index, container ->
            container.label = ('A' + index).toString()
          }
        }
      }

    val containerById = seenContainers.associateBy { it.id }

    // 2. Update descriptions with labels and optionally strip tags
    return records.map { (container, steps) ->
      container to steps.map { enhanceStep(it, containerById) }
    }
  }

  protected fun enhanceStep(
    step: InstructionStep,
    containerById: Map<String, Container>,
    locale: Locale = this.locale,
  ): InstructionStep {
    var updatedDescription =
      SousMarkUtil.replaceContainerTags(step.description) { id, original ->
        val ref = containerById[id]
        if (ref != null) {
          messages.renderMatter(ref)(locale)
        } else {
          original
        }
      }

    if (!marked) {
      updatedDescription = SousMarkUtil.stripTags(updatedDescription)
    }

    return step.copy(description = updatedDescription)
  }

  private fun saveCurrentContainer() {
    val prevContainer = currentContainer
    val prevSteps = currentSteps.toMutableList()
    if (prevContainer != null && prevSteps.isNotEmpty()) {
      records.add(prevContainer to prevSteps)
    }
  }
}
