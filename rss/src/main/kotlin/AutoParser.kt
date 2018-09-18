package org.openrndr.rss

import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import java.io.IOException
import java.io.InputStream


class AutoParser {

    private val AtomNS = "http://www.w3.org/2005/Atom"

    companion object {

        @Throws(RSSException::class)
        fun parse(document: InputStream): List<FeedArticle> {
            try {
                val jdoc = Jsoup.parse(document, "UTF-8", "", Parser.xmlParser())
                if (jdoc.children().size > 0) {
                    val rootTag = jdoc.child(0).tagName().toLowerCase()
                    return when (rootTag) {
                        "feed" -> AtomParser.parse(jdoc)
                        "rss" -> RSSParser.parse(jdoc)
                        "rdf:rdf" -> RDFParser.parse(jdoc)
                        "html", "body" -> throw NotAFeedException("not a feed but html")
                        else -> throw UnknownFormatException("unknown feed type")
                    }
                } else {
                    throw UnknownFormatException("root has no children")
                }
            } catch (e: IOException) {
                throw RSSException("IO Error", e)
            } catch (e: Exception) {
                throw RSSException("Exception while parsing", e)
            }
        }
    }


}
