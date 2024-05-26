package ayds.artist.external.lastfm.injector


import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.artist.external.lastfm.data.ArticleLastFMServiceImpl
import ayds.artist.external.lastfm.data.LastFMAPI
import ayds.artist.external.lastfm.data.LastFMToArticleResolverImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"
object LastFMInjector {

    private val LastFMAPIRetrofit  = Retrofit.Builder()
        .baseUrl(LASTFM_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val lastFM = LastFMAPIRetrofit.create(LastFMAPI::class.java)
    private val lastFMToArticleResolver = LastFMToArticleResolverImpl()

    val lastFMApiService: ArticleLastFMService =
        ArticleLastFMServiceImpl(
            lastFM,
            lastFMToArticleResolver
        )
}
