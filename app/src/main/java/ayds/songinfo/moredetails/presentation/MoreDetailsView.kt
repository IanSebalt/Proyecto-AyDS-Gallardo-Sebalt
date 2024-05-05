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
import ayds.songinfo.moredetails.domain.Article
import ayds.songinfo.moredetails.injectors.MoreDetailsInjector
import com.squareup.picasso.Picasso
import java.util.Locale

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
        uiState = uiState.copy(artistName = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: "")
        MoreDetailsInjector.initArticleDatabase(this)
        moreDetailsPresenter = MoreDetailsInjector.getMoreDetailsModel()
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

    private fun updateArticleInfo(article: Article.ArtistArticle) {
        updateArticleUiState(article)
        updateUi()
    }

    private fun updateArticleUiState(article: Article.ArtistArticle) {
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
        Picasso.get().load(LASTFM_IMAGE_URL).into(lastFMImageView)
    }

    private fun updateArticleText() {
        val text = uiState.articleDescription.replace("\n", "<br>")
        articleTextView.text = Html.fromHtml(
            textToHtml(
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

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val LASTFM_IMAGE_URL ="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

        fun textToHtml(text: String, term: String?): String {
            val builder = StringBuilder()
            builder.append("<html><div width=400>")
            builder.append("<font face=\"arial\">")
            val textWithBold = text
                .replace("'", " ")
                .replace("\n", "<br>")
                .replace(
                    "(?i)$term".toRegex(),
                    "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
                )
            builder.append(textWithBold)
            builder.append("</font></div></html>")
            return builder.toString()
        }
    }


}