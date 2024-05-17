package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.Article.ArtistArticle
import ayds.songinfo.moredetails.domain.ArticleRepository

interface MoreDetailsPresenter {
    val uiEventObservable: Observable<MoreDetailsState>

    fun getArticle(artistName: String)
}

class MoreDetailsPresenterImpl(private val repository: ArticleRepository, private val artistArticleDescriptionHelper: ArtistBiographyDescriptionHelper) : MoreDetailsPresenter {
    private val onActionSubject = Subject<MoreDetailsState>()

    override val uiEventObservable: Observable<MoreDetailsState> = onActionSubject

    override fun getArticle(artistName: String) {
        val article = repository.getArticleByArtistName(artistName)

        val uiState = article.toUiState()

        onActionSubject.notify(uiState)
    }

    private fun ArtistArticle.toUiState() = MoreDetailsState(
        artistName = artistName,
        articleDescription = artistArticleDescriptionHelper.getDescription(this),
        articleUrl = articleUrl,
    )
}