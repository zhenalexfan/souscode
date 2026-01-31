package souscode.engine.snapshot

import souscode.base.JsExport
import souscode.base.Recipe
import souscode.container.Container
import souscode.engine.RecipeContext
import souscode.engine.RecipeScope
import souscode.engine.translator.Done
import souscode.engine.translator.InstructionLogger
import souscode.engine.translator.InstructionStep
import souscode.i18n.Locale

@JsExport
data class SnapshotRecord(
  val container: Container,
  val instruction: InstructionStep,
  val snapshots: Array<ContainerSnapshot>,
)

@JsExport
class SnapshotLogger(locale: Locale = Locale.EN_US, marked: Boolean = true) :
  InstructionLogger(locale, marked) {
  private val snapshotRecords = mutableListOf<SnapshotRecord>()

  override fun log(container: Container, step: InstructionStep) {
    super.log(container, step)
    val snapshots =
      seenContainers.map { it.snapshot(this.locale) }.toTypedArray()
    snapshotRecords.add(SnapshotRecord(container, step, snapshots))
  }

  fun record(recipe: Recipe): RecipeScope {
    val context = RecipeContext()
    context.addListener(this)
    return RecipeContext.withContext(context) {
      val scope = recipe.execute()
      scope
    }
  }

  fun getSteps(): Array<Step> {
    super.compile()

    val orderedContainers = this.records.map { it.first }
    assignLabels(orderedContainers)

    val containerById = orderedContainers.associateBy { it.id }
    val steps = mutableListOf<Step>()

    // 1. Process all recorded steps with shifted snapshots
    for (i in snapshotRecords.indices) {
      val record = snapshotRecords[i]
      val snapshotsBefore =
        if (i == 0) {
          orderedContainers
            .map { it.initialSnapshot(this.locale) }
            .toTypedArray()
        } else {
          toFullSnapshots(snapshotRecords[i - 1].snapshots, orderedContainers)
        }

      steps.add(
        Step(
          containerName = record.container.labeledName(this.locale),
          instruction =
            enhanceStep(record.instruction, containerById, this.locale),
          snapshots = snapshotsBefore,
        )
      )
    }

    // 2. Add terminating "Done" step
    snapshotRecords.lastOrNull()?.let { lastRecord ->
      steps.add(
        Step(
          containerName = lastRecord.container.labeledName(this.locale),
          instruction = InstructionStep(Done, messages.done()(this.locale)),
          snapshots = toFullSnapshots(lastRecord.snapshots, orderedContainers),
        )
      )
    }

    return steps.toTypedArray()
  }

  private fun assignLabels(containers: List<Container>) {
    containers
      .groupBy { it.name(this.locale) }
      .forEach { (_, instances) ->
        if (instances.size > 1) {
          instances.forEachIndexed { index, container ->
            container.label = ('A' + index).toString()
          }
        }
      }
  }

  private fun toFullSnapshots(
    currentSnapshots: Array<ContainerSnapshot>,
    orderedContainers: List<Container>,
  ): Array<ContainerSnapshot> {
    return orderedContainers
      .map { container ->
        currentSnapshots
          .find { it.id == container.id }
          ?.copy(name = container.labeledName(this.locale))
          ?: container.initialSnapshot(this.locale)
      }
      .toTypedArray()
  }
}
