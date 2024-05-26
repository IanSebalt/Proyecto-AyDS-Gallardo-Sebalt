package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.CardRepository
import ayds.songinfo.moredetails.domain.Card

class CardRepositoryImpl(
    private val articleService: ArticleLastFMService,
    private val articleLocalStorage: ArticleLocalStorage,
    private val articleToCardResolver: ArticleToCardResolver

): CardRepository {

    override fun getCardByArtistName(artistName: String): Card {
        val dbArticle = articleLocalStorage.getArticle(artistName)

        val artistCard : Card

        if (dbArticle != null) {
            artistCard = dbArticle.apply { markItAsLocal() }
        } else {
            artistCard = articleToCardResolver.lastFmArticleToCard(articleService.getArticle(artistName))
            if(artistCard.description.isNotEmpty()) {
                articleLocalStorage.saveArticle(artistCard)
            }
        }
        return artistCard
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}