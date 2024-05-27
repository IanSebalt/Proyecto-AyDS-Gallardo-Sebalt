package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFmArticle
import ayds.songinfo.moredetails.domain.Card

interface ArticleToCardResolver {
    fun lastFmArticleToCard(lastFmArticle: LastFmArticle): Card
}
class ArticleToCardResolverImpl: ArticleToCardResolver{

    override fun lastFmArticleToCard(lastFmArticle: LastFmArticle): Card{
        return Card(
            lastFmArticle.artistName,
            lastFmArticle.biography,
            lastFmArticle.articleUrl
        )
    }



}