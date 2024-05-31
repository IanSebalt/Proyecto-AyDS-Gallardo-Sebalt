package ayds.songinfo.moredetails.data.broker.proxys

import ayds.artist.external.wikipedia.data.WikipediaArticle
import ayds.artist.external.wikipedia.data.WikipediaTrackService
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

interface ProxyWikipedia {
    fun getArticle(artistName: String): Card
}
class ProxyWikipediaImpl(private val articleService: WikipediaTrackService): ProxyWikipedia {
    override fun getArticle(artistName: String): Card {
        val wikipediaArticle: WikipediaArticle? = articleService.getInfo(artistName)
        return resolveArticleToCard(wikipediaArticle, artistName)
    }


    private fun resolveArticleToCard(wikipediaArticle: WikipediaArticle?, artistName: String): Card {
        if (wikipediaArticle == null) {
            return Card(artistName, "", "", CardSource.WIKIPEDIA)
        }
        return Card(
            artistName,
            wikipediaArticle.description,
            wikipediaArticle.wikipediaURL,
            CardSource.WIKIPEDIA
        )
    }


}