package org.openrndr.rss

import java.io.IOException
import java.io.InputStream
import java.net.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.logging.Logger

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

object RSSParser {

    private val logger = Logger.getLogger(RSSParser::class.java.name)

    @Throws(RSSException::class)
    fun parse(url: URL): List<FeedArticle> {
        try {
            val conn = url.openConnection() as HttpURLConnection
            conn.readTimeout = 4500
            conn.connectTimeout = 4500
            conn.connect()
            val articles = parse(conn.inputStream)
            conn.disconnect()
            return articles
        } catch (e: IOException) {
            throw RSSException("rss IO exception", e)
        }

    }

    @Throws(RSSException::class)
    fun parse(document: Document): List<FeedArticle> {
        val articles = ArrayList<FeedArticle>()

        val docEle = document.getElementsByTag("rss")[0]
        var items = docEle.getElementsByTag("item")

        if (items.size == 0) {
            items = docEle.getElementsByTag("entry")
        }

        for (e in items) {

            val date = getArticlePublicationDate(e)

            val title = getArticleTitle(e)
            val content = getArticleContent(e)
            val author = getArticleAuthor(e)

            val link = getArticleLink(e)
            val guid = getArticleGuid(e)

            if (link != null && title != null) {
                val article = FeedArticle(guid = guid, title = title, author = author, content = content, link = link, publicationDate = date)
                val thumbnails = e.getElementsByTag("media:thumbnail")
//                if (thumbnails != null) {
//                    for (thumbnail in thumbnails) {
//                        val thumbnailURL = thumbnail.attr("url")
//                        article.getThumbnails().add(thumbnailURL)
//                    }
//                }
                articles.add(article)
            }
        }

        return articles
    }

    private fun getArticleTitle(e: Element): String? {

        val titles = e.getElementsByTag("title")
        if (titles.size > 0) {
            return e.getElementsByTag("title")[0].text()
        } else {
            return null
        }
    }

    private fun getArticleContent(e: Element): String? {
        val descriptions = e.getElementsByTag("description")

        if (descriptions.size > 0) {
            return e.getElementsByTag("description")[0].text()
        } else {
            return null
        }
    }

    private fun getArticleLink(e: Element): String? {

        val links = e.getElementsByTag("link")

        if (links.size > 0) {
            var link: String? = e.getElementsByTag("link")[0].text()

            try {
                if (!link!!.startsWith("https://") && !link.startsWith("http://")) {
                    link = "http://" + link
                }


                var url = URL(link)
                val uri: URI

                uri = URI(url.protocol, url.userInfo, url.host, url.port, url.path, url.query, url.ref)
                url = uri.toURL()
                link = url.toExternalForm()

            } catch (e1: URISyntaxException) {
                logger.warning("Malformed url: " + link!!)
                link = null
            } catch (e1: MalformedURLException) {
                logger.warning("Malformed url: " + link!!)
                link = null
            }

            return link
        } else {
            return null
        }

    }

    private fun getArticlePublicationDate(e: Element): Date? {
        var date: Date? = null
        try {
            val pubDates = e.getElementsByTag("pubDate")

            if (pubDates.size > 0) {
                val pubDateString = pubDates[0].text()
                val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
                date = sdf.parse(pubDateString)
            }
        } catch (e1: ParseException) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
        } catch (e1: NullPointerException) {
            //
        }

        return date
    }

    private fun getArticleAuthor(e: Element): String? {
        var author: String? = null

        val authors = e.getElementsByTag("author")
        if (authors.size == 1) {
            val names = authors[0].getElementsByTag("name")
            if (names.size == 1) {
                author = names[0].text().trim { it <= ' ' }
            }
        }
        return author
    }

    private fun getArticleGuid(e: Element): String? {
        var guid: String? = null
        val guids = e.getElementsByTag("guid")
        if (guids.size > 0) {
            guid = guids[0].text()
        }
        return guid
    }

    @Throws(RSSException::class)
    fun parse(document: InputStream): List<FeedArticle> {
        try {
            return parse(Jsoup.parse(document, "UTF-8", "", Parser.xmlParser()))
        } catch (e: RSSException) {
            throw RSSException("rss exception", e)
        } catch (e: IOException) {
            throw RSSException("rss IO exception", e)
        }

    }


}
