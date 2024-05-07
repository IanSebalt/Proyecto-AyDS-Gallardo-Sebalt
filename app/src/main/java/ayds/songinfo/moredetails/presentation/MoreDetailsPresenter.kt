package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.Article
import ayds.songinfo.moredetails.domain.ArticleRepository

interface MoreDetailsPresenter {
    val uiState: MoreDetailsState
    val uiEventObservable: Observable<MoreDetailsEvent>

    fun getArticle(artistName: String)
}

class MoreDetailsPresenterImpl(private val repository: ArticleRepository) : MoreDetailsPresenter {
    override var uiState: MoreDetailsState = MoreDetailsState()
    private val onActionSubject = Subject<MoreDetailsEvent>()

    override val uiEventObservable: Observable<MoreDetailsEvent> = onActionSubject

    override fun getArticle(artistName: String) {
        var article: Article.ArtistArticle
        Thread {
            article = repository.getArticleByArtistName(artistName)
            uiState = uiState.copy(
                artistName = article.artistName,
                articleDescription = article.biography,
                articleUrl = article.articleUrl,
            )
            onActionSubject.notify(MoreDetailsEvent.OpenArticle)
        }.start()
    }
}