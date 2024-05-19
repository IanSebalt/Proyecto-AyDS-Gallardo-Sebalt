package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Article
import ayds.songinfo.moredetails.domain.ArticleRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MoreDetailsPresenterTest{
    private val articleRepository: ArticleRepository = mockk{
        every { getArticleByArtistName(any()) } returns Article.ArtistArticle("Gustavo Cerati", "Biography", "info")
    }

    private val articleDescriptionHelper: ArtistArticleDescriptionHelper = mockk{
        every { getDescription(any()) } returns "Biography"
    }

    private val presenter: MoreDetailsPresenter = MoreDetailsPresenterImpl(articleRepository, articleDescriptionHelper)


     @Test
    fun `on call to get the article with an existing artist name should notify with a complete uiState`(){
        val artistName = "Gustavo Cerati"
        val articleTester: (MoreDetailsState) -> Unit = mockk(relaxed = true)

        val uiState = MoreDetailsState(artistName, "Biography", "info")

        presenter.uiEventObservable.subscribe {
            articleTester(it)
        }

        presenter.getArticle(artistName)

        verify{ articleTester(uiState) }
    }

}


