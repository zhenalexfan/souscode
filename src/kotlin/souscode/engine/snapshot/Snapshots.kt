package souscode.engine.snapshot

import souscode.base.JsExport
import souscode.base.Matter
import souscode.base.State
import souscode.container.Container
import souscode.container.Oven
import souscode.engine.translator.InstructionStep
import souscode.i18n.Locale

@JsExport
interface MatterSnapshot {
  val id: String
  val name: String
  val state: String
  val temperature: Int
}

@JsExport
data class BasicMatterSnapshot(
  override val id: String,
  override val name: String,
  override val state: String,
  override val temperature: Int,
) : MatterSnapshot

@JsExport
data class ContainerSnapshot(
  override val id: String,
  override val name: String,
  override val state: String,
  override val temperature: Int,
  val contents: Array<MatterSnapshot>,
  val isHeated: Boolean,
  val intensity: String?,
) : MatterSnapshot

@JsExport
data class Step(
  val containerName: String,
  val instruction: InstructionStep,

  // The snapshots before the current instruction is executed
  val snapshots: Array<ContainerSnapshot>,
)

fun Matter.snapshot(locale: Locale): MatterSnapshot {
  return when (this) {
    is Container -> this.snapshot(locale)
    else ->
      BasicMatterSnapshot(
        id = id,
        name = labeledName(locale),
        state = state.name,
        temperature = temperature.value.toInt(),
      )
  }
}

fun Container.snapshot(locale: Locale): ContainerSnapshot {
  return ContainerSnapshot(
    id = id,
    name = labeledName(locale),
    state = state.name,
    temperature = temperature.value.toInt(),
    contents = contents.map { it.snapshot(locale) }.toTypedArray(),
    isHeated = isHeated,
    intensity = currentIntensity?.name,
  )
}

fun Container.initialSnapshot(locale: Locale): ContainerSnapshot {
  val (initTemp, initHeated) =
    when (this) {
      is Oven -> (this.preset.value.toInt() to true)
      else -> (20 to false)
    }

  return ContainerSnapshot(
    id = id,
    name = labeledName(locale),
    state = State.Raw.name,
    temperature = initTemp,
    contents = emptyArray(),
    isHeated = initHeated,
    intensity = null,
  )
}
