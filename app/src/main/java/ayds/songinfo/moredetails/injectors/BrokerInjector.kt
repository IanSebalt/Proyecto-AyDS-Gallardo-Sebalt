package ayds.songinfo.moredetails.injectors

import ayds.songinfo.moredetails.data.broker.BrokerImpl
import ayds.songinfo.moredetails.data.broker.Broker
import ayds.songinfo.moredetails.data.broker.proxys.ProxyLastFmImpl
import ayds.songinfo.moredetails.data.broker.proxys.ProxyNYTimesImpl
import ayds.songinfo.moredetails.data.broker.proxys.ProxyWikipediaImpl
import ayds.songinfo.moredetails.data.broker.proxys.ProxyLastFm
import ayds.songinfo.moredetails.data.broker.proxys.ProxyNYTimes
import ayds.songinfo.moredetails.data.broker.proxys.ProxyWikipedia
import ayds.artist.external.lastfm.injector.LastFMInjector
import ayds.artist.external.wikipedia.injector.WikipediaInjector
import ayds.artist.external.newyorktimes.injector.NYTimesInjector

object ProxyInjector {

    val proxyLastFm: ProxyLastFm = ProxyLastFmImpl(
        LastFMInjector.lastFMApiService
    )

    val proxyNYTimes: ProxyNYTimes = ProxyNYTimesImpl(
        NYTimesInjector.nyTimesService
    )

    val proxyWikipedia: ProxyWikipedia = ProxyWikipediaImpl(
        WikipediaInjector.wikipediaTrackService
    )

}


object BrokerInjector {
    val broker:Broker = BrokerImpl(
        ProxyInjector.proxyLastFm,
        ProxyInjector.proxyWikipedia,
        ProxyInjector.proxyNYTimes
    )
}