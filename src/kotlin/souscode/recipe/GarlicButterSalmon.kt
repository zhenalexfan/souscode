package souscode.recipe

import souscode.base.Recipe
import souscode.container.*
import souscode.engine.*
import souscode.i18n.named
import souscode.supermarket.*
import souscode.unit.*

class GarlicButterSalmon : Recipe() {

  override fun execute() = serve {
    // 1. Prepare Sauce
    val sauce =
      Bowl()
        .add(Butter(50.g), Garlic(1.tbsp), Lemon(1), Parsley(1.tbsp))
        .whisk(Velocity.Medium) // Mix butter

    // 2. Prepare Salmon
    val salmon = Salmon(200.g)

    // 3. Cook
    Pan()
      .heat(Intensity.Medium)
      .add(salmon)
      .transferContentFrom(sauce) // Pour sauce over
      .wait(10.minutes)
  }

  override val name = named(enUS = "garlic butter salmon", zhCN = "蒜香黄油三文鱼")
}
