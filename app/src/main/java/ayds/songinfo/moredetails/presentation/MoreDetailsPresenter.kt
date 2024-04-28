package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Observer
import ayds.observer.Subject
import ayds.songinfo.moredetails.data.local.article.room.ArticleEntity

interface MoreDetailsPresenter {
    val articleObservable: Observable<ArticleEntity>

    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

class MoreDetailsPresenterImpl : MoreDetailsPresenter {
    override val articleObservable = Subject<ArticleEntity>()

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsEvent> =
        Observer { value -> getArticle()
        }

    private fun getArticle() {
        TODO("Pedir al repositorio")
        val articlePlaceholder = ArticleEntity("","", "")
        articleObservable.notify(articlePlaceholder)
    }
}