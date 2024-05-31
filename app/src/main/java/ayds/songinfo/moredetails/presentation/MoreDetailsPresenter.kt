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

        print("MoreDetailsPresenterImpl: updateCard: uiStateList: $uiStateList")

        onActionSubject.notify(uiStateList)
    }

    private fun Card.toUiState() = MoreDetailsState(
        artistName = artistName,
        description = cardDescriptionHelper.getDescription(this),
        url = url,
        source = source.name,
        sourceLogoUrl = resolveSourceLogoUrl()
    )



    private fun Card.resolveSourceLogoUrl() =
        when (source) {
            CardSource.LAST_FM -> "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
            CardSource.WIKIPEDIA -> "https://upload.wikimedia.org/wikipedia/commons/8/8c/Wikipedia-logo-v2-es.png"
            CardSource.NY_TIMES -> "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"
        }
}