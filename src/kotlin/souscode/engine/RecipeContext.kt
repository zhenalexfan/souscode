package souscode.engine

import souscode.base.JsExport
import souscode.base.Matter
import souscode.container.Container
import souscode.unit.Duration
import souscode.unit.Intensity
import souscode.unit.Temperature
import souscode.unit.Velocity
import souscode.util.ThreadLocal

@JsExport
class RecipeContext {
  companion object {
    private val threadContext = ThreadLocal<RecipeContext>()

    fun current(): RecipeContext? = threadContext.get()

    fun <T> withContext(context: RecipeContext, block: () -> T): T {
      val old = threadContext.get()
      threadContext.set(context)
      try {
        return block()
      } finally {
        threadContext.set(old)
      }
    }
  }

  private val listeners = mutableListOf<RecipeListener>()
  private var silentDepth = 0

  fun addListener(listener: RecipeListener) {
    listeners.add(listener)
  }

  fun <T> silent(block: () -> T): T {
    silentDepth++
    try {
      return block()
    } finally {
      silentDepth--
    }
  }

  private inline fun notify(block: RecipeListener.() -> Unit) {
    if (silentDepth == 0) {
      listeners.forEach { it.block() }
    }
  }

  fun notifyAdd(container: Container, item: Matter) = notify {
    onAdd(container, item)
  }

  fun notifyAddAll(container: Container, items: List<Matter>) = notify {
    onAddAll(container, items)
  }

  fun notifyBoil(container: Container) = notify { onBoil(container) }

  fun notifyHeat(container: Container, intensity: Intensity) = notify {
    onHeat(container, intensity)
  }

  fun notifyWhisk(container: Container, velocity: Velocity) = notify {
    onWhisk(container, velocity)
  }

  fun notifyMix(container: Container) = notify { onMix(container) }

  fun notifyRemoveHeat(container: Container, temperature: Temperature?) =
    notify {
      onRemoveHeat(container, temperature)
    }

  fun notifyFoldIn(container: Container, other: Container) = notify {
    onFoldIn(container, other)
  }

  fun notifyWait(container: Container, duration: Duration) = notify {
    onWait(container, duration)
  }

  fun notifyRoast(container: Container) = notify { onRoast(container) }

  fun notifyTransfer(source: Container, destination: Container) = notify {
    onTransfer(source, destination)
  }

  fun notifyTransferFrom(container: Container, source: Container) = notify {
    onTransferFrom(container, source)
  }

  fun notifyCombine(container: Container, items: List<Matter>) = notify {
    onCombine(container, items)
  }

  fun notifyCombineContainers(
    combinedContainer: Container,
    originalContainer1: Container,
    originalContainer2: Container,
  ) = notify {
    onCombineContainers(
      combinedContainer,
      originalContainer1,
      originalContainer2,
    )
  }

  fun notifySubmerge(container: Container, item: Matter) = notify {
    onSubmerge(container, item)
  }
}
