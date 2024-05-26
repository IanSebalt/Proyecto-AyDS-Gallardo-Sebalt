package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.CardRepository
import ayds.songinfo.moredetails.domain.Card

interface MoreDetailsPresenter {
    var uiEventObservable: Observable<MoreDetailsState>

    fun getArticle(artistName: String)
}

class MoreDetailsPresenterImpl(private val repository: CardRepository, private val cardDescriptionHelper: CardDescriptionHelper) : MoreDetailsPresenter {
    private val onActionSubject = Subject<MoreDetailsState>()

    override var uiEventObservable: Observable<MoreDetailsState> = onActionSubject

    override fun getArticle(artistName: String) {
        val card = repository.getCardByArtistName(artistName)

        val uiState = card.toUiState()

        onActionSubject.notify(uiState)
    }

    private fun Card.toUiState() = MoreDetailsState(
        artistName = artistName,
        articleDescription = cardDescriptionHelper.getDescription(this),
        articleUrl = infoUrl,
        source = source
    )
}