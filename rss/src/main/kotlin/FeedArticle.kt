package org.openrndr.rss

import java.io.Serializable
import java.util.*

class FeedArticle(val guid: String?,
                  val title: String?,
                  val content: String?,
                  val link: String?,
                  val author: String? = null,
                  var publicationDate: Date? = null,
                  val thumbnail: List<String>? = null

) : Serializable {
    companion object {
        @JvmStatic
        private val serialVersionUID: Long = 0L
    }

}