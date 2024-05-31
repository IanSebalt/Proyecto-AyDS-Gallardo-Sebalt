package ayds.songinfo.moredetails.data.broker.proxys

import ayds.artist.external.lastfm.data.LastFmArticle
import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

interface ProxyLastFm {
    fun getArticle(artistName: String): Card
}
class ProxyLastFmImpl(private val articleService: ArticleLastFMService): ProxyLastFm {
    override fun getArticle(artistName: String): Card {
        val lastFmArticle: LastFmArticle = articleService.getArticle(artistName)
        return resolveArticleToCard(lastFmArticle)
    }


    private fun resolveArticleToCard(lastFmArticle: LastFmArticle): Card {
        if (lastFmArticle.biography == "" && lastFmArticle.articleUrl == "") {
            return Card(lastFmArticle.artistName, "", "", CardSource.LAST_FM)
        }
        return Card(
            lastFmArticle.artistName, 
            lastFmArticle.biography, 
            lastFmArticle.articleUrl, 
            CardSource.LAST_FM
        )
    }


}