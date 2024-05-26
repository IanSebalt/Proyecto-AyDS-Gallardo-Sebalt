package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.Article.ArtistArticle
import ayds.songinfo.moredetails.domain.ArticleRepository
class ArticleRepositoryImpl(
    private val articleService: ArticleLastFMService,
    private val articleLocalStorage: ArticleLocalStorage,
    private val articleToCardResolver: ArticleToCardResolver

): ArticleRepository {

    override fun getArticleByArtistName(artistName: String):ArtistArticle {
        val dbArticle = articleLocalStorage.getArticle(artistName)

        val artistBiography: ArtistArticle

        if (dbArticle != null) {
            artistBiography = dbArticle.apply { markItAsLocal() }
        } else {
            artistBiography = articleToCardResolver.LastFmArticleToCard(articleService.getArticle(artistName))
            if(artistBiography.biography.isNotEmpty()) {
                articleLocalStorage.saveArticle(artistBiography)
            }
        }
        return artistBiography
    }

    private fun ArtistArticle.markItAsLocal() {
        isLocallyStored = true
    }
}