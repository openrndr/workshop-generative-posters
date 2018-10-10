package org.openrndr.rss

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

import java.io.IOException
import java.io.InputStream
import java.net.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.logging.Logger

object AtomParser {
    private val logger = Logger.getLogger(AtomParser::class.java.name)

    @Throws(RSSException::class)
    fun parse(document: Document): List<FeedArticle> {
        val articles = ArrayList<FeedArticle>()

        val docEle = document.getElementsByTag("feed").get(0)
        val items = docEle.getElementsByTag("entry")

        for (item in items) {

            val e = item
            try {

                val date = getArticlePublicationDate(e)

                val title = getArticleTitle(e)
                val content = getArticleContent(e)

                val author = getArticleAuthor(e)

                val link = getArticleLink(e)

                val guid = getArticleGUID(e)

                if (title != null && link != null) {
                    val article = FeedArticle(guid = guid, title = title, author = author, content = content, link = link, publicationDate = date)
                    articles.add(article)
//                    val thumbnails = e.getElementsByTag("media:thumbnail")
//                    if (thumbnails != null) {
//                        for (t in thumbnails!!.indices) {
//                            val thumbnail = thumbnails!!.get(t)
//                            val thumbnailURL = thumbnail.attr("url")
//                            article.getThumbnails().add(thumbnailURL)
//                        }
//                    }

                }


            } catch (ex: NullPointerException) {
                //ex.printStackTrace();
            }


        }
        return articles
    }

    private fun getArticleTitle(e: Element): String? {
        val titles = e.getElementsByTag("title")
        if (titles.size > 0) {
            return e.getElementsByTag("title").get(0).text().trim({ it <= ' ' })
        } else {
            return null
        }
    }

    private fun getArticleContent(e: Element): String? {

        val contents = e.getElementsByTag("content")
        if (contents.size > 0) {
            var content = e.getElementsByTag("content").get(0).text().trim({ it <= ' ' })
            if ("html" == contents.attr("type")) {
                content = Parser.unescapeEntities(content, true)
            }
            return content

        } else {
            return null
        }
    }

    private fun getArticlePublicationDate(e: Element): Date? {
        var date: Date? = null
        try {
            val pubDateElements = e.getElementsByTag("published")

            if (pubDateElements.size > 0) {

                val pubDateString = pubDateElements.get(0).text()
                val sdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z")
                date = sdf.parse(pubDateString)
            }
        } catch (e1: ParseException) {
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
            val firstAuthor = authors.get(0) as Element
            val names = firstAuthor.getElementsByTag("name")
            if (names.size == 1) {
                author = names.get(0).text().trim({ it <= ' ' })
            }
        }
        return author
    }

    private fun getArticleGUID(e: Element): String? {
        var guid: String? = null
        val guids = e.getElementsByTag("id")

        if (guids.size > 0) {
            guid = e.getElementsByTag("id").get(0).text()
        }
        return guid
    }

    private fun getArticleLink(e: Element): String? {
        var link: String? = null

        val links = e.getElementsByTag("link")
        for (elem in links) {
            var rel: String? = elem.attr("rel")
            val href = elem.attr("href")

            if (rel!!.length == 0) {
                rel = null
            }

            if (rel != null && href != null) {
                if (rel == "alternate") {
                    link = href
                }
            } else {
                if (href != null && rel == null) {
                    if (link == null) {
                        link = href
                    }
                }
            }
        }

        if (link != null) {

            try {
                if (!link.startsWith("https://") && !link.startsWith("http://")) {
                    link = "http://" + link
                }

                var url = URL(link)
                val uri: URI

                uri = URI(url.protocol, url.userInfo, url.host, url.port, url.path, url.query, url.ref)
                url = uri.toURL()
                link = url.toExternalForm()

            } catch (e0: URISyntaxException) {
                link = null
            } catch (e0: MalformedURLException) {
                link = null
            }

        }
        return link
    }

    @Throws(RSSException::class)
    fun parse(document: InputStream): List<FeedArticle> {

        try {
            val jdoc = Jsoup.parse(document, "UTF-8", "", Parser.xmlParser())
            return parse(jdoc)
        } catch (e: RSSException) {
            throw RSSException("rss exception", e)
        } catch (e: IOException) {
            throw RSSException("rss IO exception", e)
        }

    }
}
