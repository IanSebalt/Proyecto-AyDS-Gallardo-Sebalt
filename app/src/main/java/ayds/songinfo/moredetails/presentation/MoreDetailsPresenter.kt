package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.CardRepository
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource


interface MoreDetailsPresenter {
    var uiEventObservable: Observable<List<MoreDetailsState>>

    fun updateCard(artistName: String)
}

class MoreDetailsPresenterImpl(private val repository: CardRepository, private val cardDescriptionHelper: CardDescriptionHelper) : MoreDetailsPresenter {
    private val onActionSubject = Subject<List<MoreDetailsState>>()

    override var uiEventObservable: Observable<List<MoreDetailsState>> = onActionSubject

    override fun updateCard(artistName: String) {
        val card = repository.getCardsByArtistName(artistName)

        val uiStateList = card.map { it.toUiState() }

        onActionSubject.notify(uiStateList)
    }

    private fun Card.toUiState() = MoreDetailsState(
        artistName = artistName,
        description = cardDescriptionHelper.getDescription(this),
        url = url,
        source = source.name,
        sourceLogoUrl = logoUrl
    )
}