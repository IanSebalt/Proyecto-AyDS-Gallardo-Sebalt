package ayds.songinfo.moredetails.injectors


import ayds.songinfo.moredetails.data.external.ArticleLastFMService
import ayds.songinfo.moredetails.data.external.article.lastfm.LastFMAPI
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ayds.songinfo.moredetails.data.external.article.lastfm.ArticleLastFMServiceImpl
import ayds.songinfo.moredetails.data.external.article.lastfm.LastFMToArticleResolverImpl


const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
object LastFMInjector {

    private val LastFMAPIRetrofit  = Retrofit.Builder()
        .baseUrl(LASTFM_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val lastFM = LastFMAPIRetrofit.create(LastFMAPI::class.java)
    private val lastFMToArticleResolver = LastFMToArticleResolverImpl()

    val lastFMApiService: ArticleLastFMService = ArticleLastFMServiceImpl(
        lastFM,
        lastFMToArticleResolver
    )
}
