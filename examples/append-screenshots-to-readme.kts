import java.io.File

val mdContent = File("./README.md").readText()


val screenShotsHeader = "## Screenshots"

val captions = mapOf(
    "rss-001" to """
        Downloads text from an rss feed and makes a composition out of it.
    """.trimIndent(),

    "textfile-001" to """
        Loads text from a textfile and makes applies and effect to it.
    """.trimIndent(),

    "video-001" to """
        Demonstrates how to play video with OpenRNDR
    """.trimIndent(),

    "website-001" to """
        Downloads images and text from a website and makes a composition out of it.
    """.trimIndent()
)


val text = try {
    mdContent.split(screenShotsHeader)[0]
} catch (e: IndexOutOfBoundsException) {
    ""
}

val screenshotsMd = File("./images").listFiles().filter {
    listOf("png", "jpg").contains(it.extension)
}.map {
    val exampleName = it.name.split(".")[0]
    """
        #### ${exampleName}
        ${captions[exampleName]?.let { "$it<br>" } ?: ""}
        ![$exampleName](${it.path})
    """.trimIndent()
}.joinToString("\n\n")


val output = listOf(text, screenShotsHeader, screenshotsMd).joinToString("\n\n")

File("./README.md").writeText(output)
