package ayds.songinfo.moredetails.data.local.article

import ayds.songinfo.moredetails.domain.Card

interface CardLocalStorage {
    fun getCards(artistName: String): List<Card>
    fun insertCard(card: Card)
}