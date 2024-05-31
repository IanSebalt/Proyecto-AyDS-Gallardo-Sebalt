package ayds.songinfo.moredetails.data.broker.proxys

import ayds.artist.external.newyorktimes.data.NYTimesService
import ayds.artist.external.newyorktimes.data.NYTimesArticle
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

interface ProxyNYTimes {
    fun getArticle(artistName: String): Card
}
class ProxyNYTimesImpl(private val articleService: NYTimesService): ProxyNYTimes {
    override fun getArticle(artistName: String): Card {
        val nyTimesArticle: NYTimesArticle = articleService.getArtistInfo(artistName)
        return resolveArticleToCard(nyTimesArticle, artistName)
    }


    private fun resolveArticleToCard(nyTimesArticle: NYTimesArticle, artistName: String): Card {
        if (nyTimesArticle == NYTimesArticle.EmptyArtistDataExternal) {
            return Card(artistName, "", "", CardSource.NY_TIMES)
        }
        val nyTimesArticleWithData = nyTimesArticle as NYTimesArticle.NYTimesArticleWithData
        return Card(
            nyTimesArticleWithData.name ?: artistName,
            nyTimesArticleWithData.info ?: "",
            nyTimesArticleWithData.url,
            CardSource.NY_TIMES
        )
    }


}