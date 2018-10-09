import java.io.File

val mdContent = File("./README.md").readText()


val screenShotsHeader = "## Screenshots"

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
        ![$exampleName](${it.path})
    """.trimIndent()
}.joinToString("\n\n")


val output  = listOf(text, screenShotsHeader, screenshotsMd).joinToString("\n\n")

File("./README.md").writeText(output)
