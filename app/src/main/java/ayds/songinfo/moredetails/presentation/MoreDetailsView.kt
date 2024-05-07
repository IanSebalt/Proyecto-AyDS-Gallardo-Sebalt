package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import ayds.songinfo.R
import ayds.songinfo.moredetails.injectors.MoreDetailsInjector
import com.squareup.picasso.Picasso
import java.util.Locale

interface MoreDetailsView {

}

class MoreDetailsViewActivity : MoreDetailsView, Activity() {
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private lateinit var articleTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initObserver()
        initViewProperties()
        getArtistInfo()
    }

    private fun initModule() {
        MoreDetailsInjector.initArticleDatabase(this)
        moreDetailsPresenter = MoreDetailsInjector.getMoreDetailsModel()
    }

    private fun initObserver() {
        moreDetailsPresenter.uiEventObservable.subscribe{
            updateUi()
        }
    }

    private fun initViewProperties() {
        articleTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton1)
        lastFMImageView = findViewById(R.id.imageView1)
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
            navigateToUrl(moreDetailsPresenter.uiState.articleUrl)
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
        val text = moreDetailsPresenter.uiState.articleDescription.replace("\n", "<br>")
        articleTextView.text = Html.fromHtml(
            textToHtml(
                text,
                moreDetailsPresenter.uiState.artistName
            )
        )
    }

    private fun getArtistInfo() {
        moreDetailsPresenter.getArticle(getArtistName())
    }

    private fun getArtistName(): String {
        return intent.getStringExtra(ARTIST_NAME_EXTRA) ?: ""
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