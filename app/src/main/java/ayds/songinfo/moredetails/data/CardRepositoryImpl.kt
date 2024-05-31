package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.broker.Broker
import ayds.songinfo.moredetails.data.local.article.CardLocalStorage
import ayds.songinfo.moredetails.domain.CardRepository
import ayds.songinfo.moredetails.domain.Card

class CardRepositoryImpl(
    private val broker: Broker,
    private val cardLocalStorage: CardLocalStorage,
): CardRepository {

    override fun getCardsByArtistName(artistName: String): List<Card> {
        val dbCards = cardLocalStorage.getCards(artistName)

        var artistCards : List<Card>

        if (dbCards.isNotEmpty()) {
            for (card in dbCards) {
                card.apply { markItAsLocal() }
            }
            artistCards = dbCards
        } else {
            artistCards = broker.getCards(artistName)
            for (card in artistCards) {
                if (card.text != "") {
                    cardLocalStorage.insertCard(card)
                }
            }
        }
        return artistCards
    }

    private fun Card.markItAsLocal() {
        isLocallyStored = true
    }
}