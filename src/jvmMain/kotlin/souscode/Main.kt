package souscode

import souscode.engine.translator.Translator
import souscode.i18n.Locale
import souscode.recipe.*
import souscode.unit.*

// Check function with custom error message
fun check(condition: Boolean, lazyMessage: () -> String) {
  if (!condition) {
    throw IllegalStateException(lazyMessage())
  }
}

fun main() {
  val translator = Translator(Locale.ZH_CN)
  println(translator.translate(Carbonara()))
  println(translator.translate(OvenRoastedPotatoes()))
  println(translator.translate(AglioEOlio()))
  println(translator.translate(BraisedBeefNoodleSoup()))
  println(translator.translate(Bruschetta()))
  println(translator.translate(CapreseSalad()))
  println(translator.translate(GarlicButterSalmon()))
  println(translator.translate(MacAndCheese()))
  println(translator.translate(MapoTofu()))
  println(translator.translate(ScrambledEggs()))
  println(translator.translate(SteamedRice()))
  println(translator.translate(TomatoSoup()))
}
