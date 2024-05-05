package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Observer
import ayds.observer.Subject
import ayds.songinfo.moredetails.data.local.article.room.ArticleEntity
import ayds.songinfo.moredetails.domain.Article
import ayds.songinfo.moredetails.domain.ArticleRepository

interface MoreDetailsPresenter {
    val articleObservable: Observable<Article.ArtistArticle>

    fun setMoreDetailsView(moreDetailsView: MoreDetailsView)
}

class MoreDetailsPresenterImpl(private val repository: ArticleRepository) : MoreDetailsPresenter {
    override val articleObservable = Subject<Article.ArtistArticle>()

    private lateinit var moreDetailsView: MoreDetailsView

    override fun setMoreDetailsView(moreDetailsView: MoreDetailsView) {
        this.moreDetailsView = moreDetailsView
        moreDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<MoreDetailsEvent> =
        Observer { getArticle()
        }

    private fun getArticle() {
        var article = Article.ArtistArticle("", "", "")
        Thread {
            article = repository.getArticleByArtistName(moreDetailsView.uiState.artistName)
            articleObservable.notify(article)
        }.start()
    }
}