package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.ArticleLastFMService
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.Article
import ayds.songinfo.moredetails.domain.ArticleRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleRepositoryTest {
    private val articleLocalRepository: ArticleLocalStorage = mockk(relaxUnitFun = true)
    private val articleService: ArticleLastFMService = mockk(relaxUnitFun = true)

    private val articleRepository: ArticleRepository = ArticleRepositoryImpl(articleService, articleLocalRepository)
    @Test
    fun givenANullLocalArticleItShouldReturnARemoteArticle() {
        val artistName = "Gustavo Cerati"
        val remoteArticle = Article.ArtistArticle("Gustavo Cerati", "Biography", "info", false)
        every { articleLocalRepository.getArticle(artistName) } returns null
        every { articleService.getArticle(artistName) } returns remoteArticle

        val result = articleRepository.getArticleByArtistName(artistName)

        assertEquals(remoteArticle, result)
        verify { articleLocalRepository.saveArticle(remoteArticle) }
    }

    @Test
    fun  givenALocalArticleItShouldBeLocallyStored() {
        val artistName = "Gustavo Cerati"
        val localArticle = Article.ArtistArticle("Gustavo Cerati", "Biography", "info", true)
        every { articleLocalRepository.getArticle(artistName) } returns localArticle

        val result = articleRepository.getArticleByArtistName(artistName)

        assertEquals(localArticle, result)
        verify(exactly = 0) { articleLocalRepository.saveArticle(any()) }
    }

    @Test
    fun givenARemoteNullBiographyItShouldntBeStored() {
        val artistName = "artistName"
        val remoteArticle = Article.ArtistArticle("artistName", "", "", false)
        every { articleLocalRepository.getArticle(artistName) } returns null
        every { articleService.getArticle(artistName) } returns remoteArticle

        val result = articleRepository.getArticleByArtistName(artistName)

        assertEquals(remoteArticle, result)
        verify(exactly = 0) { articleLocalRepository.saveArticle(any()) }
    }

}