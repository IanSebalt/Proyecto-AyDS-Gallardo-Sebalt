package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MoreDetailsPresenterTest{
    private val cardRepository: CardRepository = mockk{
        every { getCardByArtistName(any()) } returns Card("Gustavo Cerati", "Biography", "info")
    }

    private val articleDescriptionHelper: CardDescriptionHelper = mockk{
        every { getDescription(any()) } returns "Biography"
    }

    private val presenter: MoreDetailsPresenter = MoreDetailsPresenterImpl(cardRepository, articleDescriptionHelper)


     @Test
    fun `on call to get the article with an existing artist name should notify with a complete uiState`(){
        val artistName = "Gustavo Cerati"
        val articleTester: (MoreDetailsState) -> Unit = mockk(relaxed = true)

        val uiState = MoreDetailsState(artistName, "Biography", "info", "LastFM")

        presenter.uiEventObservable.subscribe {
            articleTester(it)
        }

        presenter.getArticle(artistName)

        verify{ articleTester(uiState) }
    }

}


