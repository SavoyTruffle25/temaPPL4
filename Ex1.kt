package org.example

import khttp.get
import org.jsoup.Jsoup


interface Parser {
    fun parse(text: String): Map<String, Any>
}

class JsonParser : Parser {
    override fun parse(text: String): Map<String, Any> {
        return mapOf("json" to text)
    }
}

class XmlParser : Parser {
    override fun parse(text: String): Map<String, Any> {
        return mapOf("xml" to text)
    }
}

class YamlParser : Parser {
    override fun parse(text: String): Map<String, Any> {
        return mapOf("yaml" to text)
    }
}

// 3. Crawler-ul care folosește khttp
class Crawler(private val url: String) {

    fun getResource(): String {
        return get(url).text
    }

    fun processContent(contentType: String) {
            val response = getResource()

            val parser: Parser = when (contentType) {
                "json" -> JsonParser()
                "xml"  -> XmlParser()
                "yaml" -> YamlParser()
                else -> throw IllegalArgumentException("Tip necunoscut")
            }

            return parser.parse(response)
}

fun main() {
    val crawler = Crawler("exemple.com")
    val res=crawler.processContent("json")
    println(res)
}