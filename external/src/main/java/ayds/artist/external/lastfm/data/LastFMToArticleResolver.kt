package ayds.artist.external.lastfm.data

import com.google.gson.Gson
import com.google.gson.JsonObject

interface LastFMToArticleResolver {
    fun getArticleDescription(serviceData: String?, artistName: String): LastFmArticle
}


private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"


internal class LastFMToArticleResolverImpl: LastFMToArticleResolver {

    override fun getArticleDescription(serviceData: String?,artistName: String): LastFmArticle {
        val gson = Gson()
        val jobj = gson.fromJson(serviceData, JsonObject::class.java)

        val artist = jobj.getArtist()
        val bio = artist.getBio()
        val extract = bio.getContent()
        val url = artist.getUrl()
        val textBio = extract?: "No Results"

        return LastFmArticle(artistName, textBio, url)

    }

    private fun JsonObject.getArtist() = this[ARTIST].asJsonObject
    private fun JsonObject.getBio() = this[BIO].asJsonObject
    private fun JsonObject.getContent() = this[CONTENT].asString
    private fun JsonObject.getUrl() = this[URL].asString






}