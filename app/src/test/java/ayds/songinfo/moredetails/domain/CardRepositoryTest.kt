package ayds.songinfo.moredetails.data

import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class CardRepositoryTest {
    private val articleLocalRepository: ArticleLocalStorage = mockk(relaxUnitFun = true)
    private val articleService: ArticleLastFMService = mockk(relaxUnitFun = true)
    private val articleToCardResolver: ArticleToCardResolver = mockk(relaxUnitFun = true)

    private val cardRepository: CardRepository = CardRepositoryImpl(articleService, articleLocalRepository, articleToCardResolver)
    @Test
    fun `given a non-locally-stored article it should return a remote article`() {
        val artistName = "Gustavo Cerati"
        val remoteCard = Card("Gustavo Cerati", "Biography", "info", isLocallyStored = false)
        every { articleLocalRepository.getArticle(artistName) } returns null
        every { articleToCardResolver.lastFmArticleToCard(articleService.getArticle(artistName)) } returns remoteCard

        val result = cardRepository.getCardByArtistName(artistName)

        assertEquals(remoteCard, result)
        verify { articleLocalRepository.saveArticle(remoteCard) }
    }

    @Test
    fun  `given a locally stored article it should be locally stored and not stored again`() {
        val artistName = "Gustavo Cerati"
        val localArticle = Card("Gustavo Cerati", "Biography", "info", isLocallyStored = true)
        every { articleLocalRepository.getArticle(artistName) } returns localArticle

        val result = cardRepository.getCardByArtistName(artistName)

        assertEquals(localArticle, result)
        verify(exactly = 0) { articleLocalRepository.saveArticle(any()) }
    }

    @Test
    fun `given a non existent remotely retrieved article it shouldnt be stored`() {
        val artistName = "artistName"
        val remoteCard = Card("artistName", "", "", isLocallyStored = false)
        every { articleLocalRepository.getArticle(artistName) } returns null
        every { articleToCardResolver.lastFmArticleToCard(articleService.getArticle(artistName)) } returns remoteCard

        val result = cardRepository.getCardByArtistName(artistName)

        assertEquals(remoteCard, result)
        verify(exactly = 0) { articleLocalRepository.saveArticle(any()) }
    }

}