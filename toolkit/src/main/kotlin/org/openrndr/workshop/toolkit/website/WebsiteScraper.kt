package org.openrndr.workshop.toolkit.website

import com.chimbori.crux.articles.ArticleExtractor
import org.jsoup.Jsoup
import org.openrndr.draw.ColorBuffer
import java.net.URL

fun scrapeWebsite(url:String) : WebsiteContents {
    val rawHTML = URLFetcher.fetch(URL(url), 1000, 1000)

    val article = ArticleExtractor.with(url, rawHTML)
            .extractMetadata()
            .extractContent()
            .article()

    println(article.title)
    val imageElements = Jsoup.parse(rawHTML).getElementsByTag("img")
    //val imageElements = article.document.getElementsByTag("img")
    val images = mutableListOf<ColorBuffer>()

    // -- download all images
    imageElements.forEach {
        val imageUrl = it.attr("src")
        if (imageUrl.endsWith(".png") || imageUrl.endsWith(".jpg") || imageUrl.endsWith(".gif")) {

            var fixedUrl = imageUrl
            if (imageUrl.startsWith("//")) {
                fixedUrl = "https:$imageUrl"
            } else if (imageUrl.startsWith("/")) {
                fixedUrl ="$url$imageUrl"
            }

            println("downloading image from $fixedUrl")
            images.add(ColorBuffer.fromUrl(fixedUrl))
        }
    }
    return WebsiteContents(article.title, article.document.text(), images)
}
class WebsiteContents(val title:String, val body:String, val images: List<ColorBuffer>)