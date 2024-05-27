package ayds.artist.external.lastfm.data

import java.io.IOException


internal class ArticleLastFMServiceImpl(
    private val lastFMArticleAPI: LastFMAPI,
    private val lastFMArticleResolver: LastFMToArticleResolver
) : ArticleLastFMService {

    override fun getArticle(artistName: String): LastFmArticle {
        var artistArticle = LastFmArticle(artistName, "", "")
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