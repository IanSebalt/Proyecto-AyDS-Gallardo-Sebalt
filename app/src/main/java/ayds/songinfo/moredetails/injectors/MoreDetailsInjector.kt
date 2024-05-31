package ayds.songinfo.moredetails.injectors

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.CardRepositoryImpl
import ayds.songinfo.moredetails.data.local.article.room.CardDatabase
import ayds.songinfo.moredetails.data.local.article.room.CardLocalStorageRoomImpl
import ayds.songinfo.moredetails.domain.CardRepository
import ayds.songinfo.moredetails.presentation.CardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenter
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenterImpl



private const val ARTICLE_BD_NAME = "article-database"

object MoreDetailsInjector{
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    fun getMoreDetailsModel(): MoreDetailsPresenter = moreDetailsPresenter

    fun initArticleDatabase(context: Context) {
        val cardDatabase =
            Room.databaseBuilder(context, CardDatabase::class.java, ARTICLE_BD_NAME).build()

        val cardLocalRoomStorage = CardLocalStorageRoomImpl(cardDatabase)

        val broker = BrokerInjector.broker

        val repository: CardRepository = CardRepositoryImpl(broker, cardLocalRoomStorage)

        val artistBiographyDescriptionHelper = CardDescriptionHelperImpl()

        moreDetailsPresenter = MoreDetailsPresenterImpl(repository, artistBiographyDescriptionHelper)
    }




}