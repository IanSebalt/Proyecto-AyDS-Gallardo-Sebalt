package ayds.songinfo.moredetails.data.broker.proxys

import ayds.artist.external.lastfm.data.LastFmArticle
import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

interface ProxyLastFm {
    fun getArticle(artistName: String): Card
}

private const val LAST_FM_LOGO_URL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

class ProxyLastFmImpl(private val articleService: ArticleLastFMService): ProxyLastFm {
    override fun getArticle(artistName: String): Card {
        val lastFmArticle: LastFmArticle = articleService.getArticle(artistName)
        return resolveArticleToCard(lastFmArticle)
    }


    private fun resolveArticleToCard(lastFmArticle: LastFmArticle): Card {
        if (lastFmArticle.biography == "" && lastFmArticle.articleUrl == "") {
            return Card(lastFmArticle.artistName, "", "", CardSource.LAST_FM, LAST_FM_LOGO_URL)
        }
        return Card(
            lastFmArticle.artistName, 
            lastFmArticle.biography, 
            lastFmArticle.articleUrl,
            CardSource.LAST_FM,
            LAST_FM_LOGO_URL
        )
    }


}