package org.openrndr.rss


/**
 * Created with IntelliJ IDEA.
 * User: edwinjakobs
 * Date: 6/25/13
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
open class RSSException : Exception {

    internal constructor(message: String) : super(message) {
    }


    internal constructor(message: String, e: Exception) : super(message, e) {
    }

}

class NotAFeedException(s: String) : RSSException(s)

class UnknownFormatException(s: String) : RSSException(s)

class UnsupportedFormatException(s: String) : RSSException(s)
