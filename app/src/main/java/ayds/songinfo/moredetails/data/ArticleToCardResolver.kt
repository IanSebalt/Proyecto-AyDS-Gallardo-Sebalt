package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.LastFmArticle
import ayds.songinfo.moredetails.domain.Article.ArtistArticle

interface ArticleToCardResolver {
    fun LastFmArticleToCard(lastFmArticle: LastFmArticle.ArtistArticle): ArtistArticle
}
class ArticleToCardResolverImpl: ArticleToCardResolver{

    override fun LastFmArticleToCard(lastFmArticle: LastFmArticle.ArtistArticle): ArtistArticle{
        return ArtistArticle(
            lastFmArticle.artistName,
            lastFmArticle.biography,
            lastFmArticle.articleUrl
        )
    }



}