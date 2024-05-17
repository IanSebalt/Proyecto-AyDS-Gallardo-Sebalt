package ayds.songinfo.moredetails.injectors

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.ArticleRepositoryImpl
import ayds.songinfo.moredetails.data.external.ArticleLastFMService
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.data.local.article.room.ArticleDatabase
import ayds.songinfo.moredetails.data.local.article.room.ArticleLocalStorageRoomImpl
import ayds.songinfo.moredetails.domain.ArticleRepository
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
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
        val lastFMService: ArticleLastFMService = LastFMInjector.lastFMApiService
        val repository: ArticleRepository = ArticleRepositoryImpl(lastFMService, articleLocalRoomStorage)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository, artistBiographyDescriptionHelper)
    }




}