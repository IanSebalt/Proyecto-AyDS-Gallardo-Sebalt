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

interface MoreDetailsView {



}

class MoreDetailsViewActivity : MoreDetailsView, Activity() {
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private lateinit var cardDescriptionTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var sourceImageView: ImageView
    private lateinit var sourceLabel1: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initObserver()
        initViewProperties()
        getArtistCardAsync()
    }

    private fun initModule() {
        MoreDetailsInjector.initArticleDatabase(this)
        moreDetailsPresenter = MoreDetailsInjector.getMoreDetailsModel()
    }

    private fun initObserver() {
        moreDetailsPresenter.uiEventObservable.subscribe{
            uiState -> updateUi(uiState)
        }
    }

    private fun initViewProperties() {
        cardDescriptionTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton1)
        sourceImageView = findViewById(R.id.imageView1)
        sourceLabel1 = findViewById(R.id.sourceLabel1)
    }

    private fun getArtistCardAsync() {
        Thread {
            getArtistCard()
        }.start()
    }

    private fun updateUi(uiState : MoreDetailsState) {
        runOnUiThread() {
            updateOpenUrlButton(uiState.url)
            updateSourceLabel(uiState.source)
            updateLastFMLogo(uiState.sourceLogoUrl)
            updateArticleText(uiState.description)
        }
    }

    private fun updateOpenUrlButton(url: String) {
        openUrlButton.setOnClickListener {
            navigateToUrl(url)
        }
    }

    private fun updateSourceLabel(source: String) {
        sourceLabel1.text = source
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateLastFMLogo(url: String) {
        Picasso.get().load(url).into(sourceImageView)
    }

    private fun updateArticleText(articleDescription: String) {
        cardDescriptionTextView.text = Html.fromHtml(articleDescription)
    }

    private fun getArtistCard() {
        moreDetailsPresenter.updateCard(getArtistName())
    }

    private fun getArtistName(): String {
        return intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("No artist name")
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }


}