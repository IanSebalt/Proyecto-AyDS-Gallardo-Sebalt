package ayds.songinfo.moredetails.test.presentetation

import ayds.observer.Subject
import ayds.songinfo.moredetails.data.ArticleRepositoryImpl
import ayds.songinfo.moredetails.data.external.ArticleLastFMService
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.ArticleRepository
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelper
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenter
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import ayds.songinfo.moredetails.presentation.MoreDetailsState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MoreDetailsPresenterTest{
    private val articleLocalRepository: ArticleLocalStorage = mockk(relaxUnitFun = true)
    private val articleService: ArticleLastFMService = mockk(relaxUnitFun = true)

    private val articleRepository: ArticleRepository = ArticleRepositoryImpl(articleService, articleLocalRepository)

    private val articleDescriptionHelper: ArtistBiographyDescriptionHelper = mockk(relaxUnitFun = true)

    private val presenter: MoreDetailsPresenter = MoreDetailsPresenterImpl(articleRepository, articleDescriptionHelper)

    private val onActionSubject = mockk<Subject<MoreDetailsState>>(relaxUnitFun = true)




    @Test
    fun onGetArticle(){
        presenter.uiEventObservable = onActionSubject

        val artistName = "artistName"

        every {presenter.getArticle(artistName)}

        verify(exactly = 1) { (presenter.uiEventObservable as Subject<MoreDetailsState>).notify(any())}
    }

}


