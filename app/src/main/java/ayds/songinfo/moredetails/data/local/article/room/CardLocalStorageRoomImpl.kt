package ayds.songinfo.moredetails.data.local.article.room

import ayds.songinfo.moredetails.data.local.article.CardLocalStorage
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.CardSource

internal class CardLocalStorageRoomImpl(
    private val cardDatabase: CardDatabase,
):CardLocalStorage {

    override fun getCards(artistName: String): List<Card> {
        val cards = cardDatabase.CardDao().getArticleByArtistName(artistName)
        return getListOfCards(cards)
    }

    private fun getListOfCards(cards: List<CardEntity>?): List<Card> {
        val cardList = mutableListOf<Card>()
        if (cards != null) {
            for (card in cards) {
                cardList.add(
                    Card(
                        card.artistName, card.content, card.url, CardSource.values()[card.source]
                    )
                )
            }
        }
        return cardList
    }

    override fun insertCard(card: Card) {
        cardDatabase.CardDao().insertCard(
            CardEntity(
                card.text, card.artistName, card.url, card.source.ordinal
            )
        )
    }

}