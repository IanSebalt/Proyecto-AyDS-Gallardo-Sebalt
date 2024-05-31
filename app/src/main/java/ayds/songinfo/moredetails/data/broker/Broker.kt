package ayds.songinfo.moredetails.data.broker

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.data.broker.proxys.ProxyLastFm
import ayds.songinfo.moredetails.data.broker.proxys.ProxyWikipedia
import ayds.songinfo.moredetails.data.broker.proxys.ProxyNYTimes

interface Broker{
    fun getCards(artistName: String): List<Card>
}

class BrokerImpl(
    private val proxyLastFm: ProxyLastFm,
    private val proxyWikipedia: ProxyWikipedia,
    private val proxyNYTimes: ProxyNYTimes
):Broker{
    override fun getCards(artistName: String): List<Card> {
        val cards = mutableListOf<Card>()
        val lastFmCard:Card = proxyLastFm.getArticle(artistName)
        val wikipediaCard:Card = proxyWikipedia.getArticle(artistName)
        val nyTimesCard:Card = proxyNYTimes.getArticle(artistName)
        if (lastFmCard.text != "") {
            cards.add(lastFmCard)
        }
        if (wikipediaCard.text != "") {
            cards.add(wikipediaCard)
        }
        if (nyTimesCard.text != "") {
            cards.add(nyTimesCard)
        }
        return cards
    }
}
