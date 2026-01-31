package souscode.engine.translator

import souscode.base.JsExport
import souscode.unit.Duration
import souscode.unit.Intensity

@JsExport sealed interface InstructionStepCategory

@JsExport data object Add : InstructionStepCategory

@JsExport
data class Heat(val intensity: Intensity? = null) : InstructionStepCategory

@JsExport data object Mix : InstructionStepCategory

@JsExport
data class Wait(val duration: Duration? = null) : InstructionStepCategory

@JsExport data object Transfer : InstructionStepCategory

@JsExport data object Done : InstructionStepCategory

@JsExport
data class InstructionStep(
  /** The type of the current step. */
  val category: InstructionStepCategory,

  /** The description of the current step. This is localized already. */
  val description: String,
)
