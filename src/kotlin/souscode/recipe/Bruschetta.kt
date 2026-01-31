package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class Bruschetta : Recipe() {

  override fun execute() = serve {
    // 1. Topping
    val topping =
      Bowl()
        .add(
          Tomato(300.g),
          Basil(10.g), // Basil expects Quantity
          Garlic(1.tsp),
          OliveOil(2.tbsp),
          Salt(1.tsp),
        )

    // 2. Toast
    val slices =
      BakingTray()
        .add(Bread(slices = 4)) // Bread takes separate val in constructor?

    Oven(200.celsius).add(slices).roast().wait(5.minutes)

    // 3. Assemble
    // Place topping on slices.
    // We can transfer toast to a Plate and add topping.
    Bowl() // Using Bowl as generic container/Plate
      .transferContentFrom(slices) // Retrieve toast
      .transferContentFrom(topping) // Add tomatoes on top
  }

  override val name = named(enUS = "bruschetta", zhCN = "意式烤面包")
}
