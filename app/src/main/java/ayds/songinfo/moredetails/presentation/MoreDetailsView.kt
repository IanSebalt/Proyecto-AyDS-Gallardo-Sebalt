package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.R
import ayds.songinfo.moredetails.data.local.article.room.ArticleEntity
import ayds.songinfo.moredetails.fulllogic.ArtistBiography
import ayds.songinfo.moredetails.fulllogic.OtherInfoWindow
import ayds.songinfo.moredetails.presentation.MoreDetailsEvent
import com.squareup.picasso.Picasso

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsEvent>
    val uiState: MoreDetailsState
}

class MoreDetailsViewActivity : MoreDetailsView, Activity() {
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private val onActionSubject = Subject<MoreDetailsEvent>()

    private lateinit var articleTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView

    override val uiEventObservable: Observable<MoreDetailsEvent> = onActionSubject
    override var uiState: MoreDetailsState = MoreDetailsState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initViewProperties()
        initObservers()
        getArtistInfo()
    }

    private fun initModule() {
        TODO("Cambiar al inyector")
        moreDetailsPresenter = MoreDetailsPresenterImpl()
        moreDetailsPresenter.setMoreDetailsView(this)
    }

    private fun initViewProperties() {
        articleTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton1)
        lastFMImageView = findViewById(R.id.imageView1)
    }

    private fun initObservers() {
        moreDetailsPresenter.articleObservable.subscribe {
            value -> updateArticleInfo(value)
        }
    }

    private fun updateArticleInfo(article: ArticleEntity) {
        updateArticleUiState(article)
        updateUi()
    }

    private fun updateArticleUiState(article: ArticleEntity) {
        uiState = uiState.copy(
            artistName = article.artistName,
            articleUrl = article.articleUrl,
            articleDescription = article.biography)
    }

    private fun updateUi() {
        runOnUiThread() {
            updateOpenUrlButton()
            updateLastFMLogo()
            updateArticleText()
        }
    }

    private fun updateOpenUrlButton() {
        openUrlButton.setOnClickListener {
            navigateToUrl(uiState.articleUrl)
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateLastFMLogo() {
        Picasso.get().load(OtherInfoWindow.LASTFM_IMAGE_URL).into(lastFMImageView)
    }

    private fun updateArticleText() {
        val text = uiState.articleDescription.replace("\n", "<br>")
        articleTextView.text = Html.fromHtml(
            OtherInfoWindow.textToHtml(
                text,
                uiState.artistName
            )
        )
    }

    private fun getArtistInfo() {
        notifyArticleSearch()
    }

    private fun notifyArticleSearch() {
        onActionSubject.notify(MoreDetailsEvent.GetArticle)
    }


}