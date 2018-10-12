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

val links = mapOf(
        "images-001" to """
            ./src/main/kotlin/images-001
        """.trimIndent(),

        "video-001" to """
            ./src/main/kotlin/video-001
        """.trimIndent(),

        "shapes-001" to """
            ./src/main/kotlin/shapes-001
        """.trimIndent(),

        "shapes-003" to """
            ./src/main/kotlin/shapes-003
        """.trimIndent(),

        "shapes-002" to """
             ./src/main/kotlin/shapes-002
        """.trimIndent(),

        "textfile-001" to """
            ./src/main/kotlin/textfile-001
        """.trimIndent(),

        "website-001" to """
             ./src/main/kotlin/website-001
        """.trimIndent(),

        "typography-002" to """
            ./src/main/kotlin/typography-002
        """.trimIndent(),

        "typography-001" to """
             ./src/main/kotlin/typography-001
        """.trimIndent(),

        "effects-001" to """
            ./src/main/kotlin/effects-001
        """.trimIndent(),

        "effects-002" to """
            ./src/main/kotlin/effects-002
        """.trimIndent(),

        "rss-001" to """
            ./src/main/kotlin/rss-001
        """.trimIndent(),

        "effects-003" to """
            ./src/main/kotlin/effects-003
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
        [![$exampleName](${it.path})](${links[exampleName]?.let { "$it" }})
    """.trimIndent()
}.joinToString("\n\n")


val output = listOf(text, screenShotsHeader, screenshotsMd).joinToString("\n\n")

File("./README.md").writeText(output)
