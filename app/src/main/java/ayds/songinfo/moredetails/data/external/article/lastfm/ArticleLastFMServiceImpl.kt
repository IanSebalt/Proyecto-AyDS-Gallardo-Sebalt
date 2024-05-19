package ayds.songinfo.moredetails.data.external.article.lastfm

import ayds.songinfo.moredetails.data.external.ArticleLastFMService
import ayds.songinfo.moredetails.domain.Article.ArtistArticle
import java.io.IOException


internal class ArticleLastFMServiceImpl(
    private val lastFMArticleAPI: LastFMAPI,
    private val lastFMArticleResolver: LastFMToArticleResolver
) : ArticleLastFMService {

    override fun getArticle(artistName: String): ArtistArticle {
        var artistArticle = ArtistArticle(artistName, "", "")
        try{
            val articleResponse = getArticleFromService(artistName)
            artistArticle = lastFMArticleResolver.getArticleDescription(articleResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistArticle
    }

    private fun getArticleFromService(artistName: String) =
        lastFMArticleAPI.getArtistInfo(artistName).execute()


}