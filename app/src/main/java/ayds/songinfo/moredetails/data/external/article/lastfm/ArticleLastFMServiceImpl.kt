package ayds.songinfo.moredetails.data.external.article.lastfm

import ayds.songinfo.moredetails.data.external.ArticleLastFMService
import ayds.songinfo.moredetails.domain.Article.ArtistArticle


internal class ArticleLastFMServiceImpl(
    private val lastFMArticleAPI: LastFMAPI,
    private val lastFMArticleResolver: LastFMToArticleResolver
) : ArticleLastFMService {

    override fun getArticle(artistName: String): ArtistArticle {
        val articleResponse = lastFMArticleAPI.getArtistInfo(artistName).execute()
        return lastFMArticleResolver.getArticleDescription(articleResponse.body(), artistName)
    }


}