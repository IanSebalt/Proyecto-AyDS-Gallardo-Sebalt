package ayds.songinfo.moredetails.injectors

import android.content.Context
import androidx.room.Room
import ayds.artist.external.lastfm.data.ArticleLastFMService
import ayds.artist.external.lastfm.injector.LastFMInjector.lastFMApiService
import ayds.songinfo.moredetails.data.CardRepositoryImpl
import ayds.songinfo.moredetails.data.ArticleToCardResolver
import ayds.songinfo.moredetails.data.ArticleToCardResolverImpl
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.data.local.article.room.ArticleDatabase
import ayds.songinfo.moredetails.data.local.article.room.ArticleLocalStorageRoomImpl
import ayds.songinfo.moredetails.domain.CardRepository
import ayds.songinfo.moredetails.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenter
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import ayds.songinfo.moredetails.presentation.MoreDetailsView



object MoreDetailsInjector{
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsModel(): MoreDetailsPresenter = moreDetailsPresenter

    fun initArticleDatabase(moreDetailsView: MoreDetailsView) {
        val database = Room.databaseBuilder(
            moreDetailsView as Context,
            ArticleDatabase::class.java,
            "article-database"
        ).build()

        val articleLocalRoomStorage: ArticleLocalStorage = ArticleLocalStorageRoomImpl(database)
        val lastFMService: ArticleLastFMService = lastFMApiService
        val articleToCardResolver : ArticleToCardResolver = ArticleToCardResolverImpl()
        val repository: CardRepository = CardRepositoryImpl(lastFMService, articleLocalRoomStorage, articleToCardResolver)

        val artistBiographyDescriptionHelper = CardDescriptionHelperImpl()

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository, artistBiographyDescriptionHelper)
    }




}