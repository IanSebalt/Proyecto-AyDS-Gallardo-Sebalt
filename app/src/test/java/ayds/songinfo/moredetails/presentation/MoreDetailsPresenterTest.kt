package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MoreDetailsPresenterTest {
    private val cardRepository: CardRepository = mockk(relaxed = true)

    private val cardDescriptionHelper: CardDescriptionHelper = mockk(relaxed = true)

    private val presenter: MoreDetailsPresenter = MoreDetailsPresenterImpl(cardRepository, cardDescriptionHelper)

     @Test
    fun `on call to get the article with an existing artist name should notify with a complete uiState`(){
        val artistName = "Gustavo Cerati"
        val articleTester: (MoreDetailsState) -> Unit = mockk(relaxed = true)
        val card = Card(
            artistName = artistName,
            text = "Biography",
            url = "info"
        )
        val uiState = MoreDetailsState(artistName, "Biography", "info", "LastFM")

        every {
            cardDescriptionHelper.getDescription(any())
        } returns "Biography"

        every {
            cardRepository.getCardsByArtistName(artistName)
        } returns card

        presenter.uiEventObservable.subscribe {
            articleTester(it)
        }

        presenter.updateCard(artistName)

        verify{ articleTester(uiState) }
    }

}


