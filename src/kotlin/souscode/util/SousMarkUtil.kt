package souscode.util

import souscode.base.JsExport

@JsExport
object SousMarkUtil {
  private val CONTAINER_TAG_REGEX =
    Regex("<container[^>]*id=\"([^\"]+)\"[^>]*>.*?</container>")
  private val ANY_TAG_REGEX = Regex("<[^>]*>")

  fun replaceContainerTags(
    text: String,
    transform: (id: String, original: String) -> String,
  ): String {
    return CONTAINER_TAG_REGEX.replace(text) { match ->
      val id = match.groupValues[1]
      val original = match.value
      transform(id, original)
    }
  }

  fun stripTags(text: String): String {
    return text.replace(ANY_TAG_REGEX, "")
  }
}
