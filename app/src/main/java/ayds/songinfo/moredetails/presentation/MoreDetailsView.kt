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

    private lateinit var cardDescriptionTextView1: TextView
    private lateinit var openUrlButton1: Button
    private lateinit var sourceImageView1: ImageView
    private lateinit var sourceLabel1: TextView

    private lateinit var cardDescriptionTextView2: TextView
    private lateinit var openUrlButton2: Button
    private lateinit var sourceImageView2: ImageView
    private lateinit var sourceLabel2: TextView

    private lateinit var cardDescriptionTextView3: TextView
    private lateinit var openUrlButton3: Button
    private lateinit var sourceImageView3: ImageView
    private lateinit var sourceLabel3: TextView

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
        cardDescriptionTextView1 = findViewById(R.id.textPane1)
        openUrlButton1 = findViewById(R.id.openUrlButton1)
        sourceImageView1 = findViewById(R.id.imageView1)
        sourceLabel1 = findViewById(R.id.sourceLabel1)
        cardDescriptionTextView2 = findViewById(R.id.textPane2)
        openUrlButton2 = findViewById(R.id.openUrlButton2)
        sourceImageView2 = findViewById(R.id.imageView2)
        sourceLabel2 = findViewById(R.id.sourceLabel2)
        cardDescriptionTextView3 = findViewById(R.id.textPane3)
        openUrlButton3 = findViewById(R.id.openUrlButton3)
        sourceImageView3 = findViewById(R.id.imageView3)
        sourceLabel3 = findViewById(R.id.sourceLabel3)
    }

    private fun getArtistCardAsync() {
        Thread {
            getArtistCard()
        }.start()
    }

    private fun updateUi(uiState : List<MoreDetailsState>) {
        runOnUiThread() {
            for (i in uiState.indices) {
                when (i) {
                    0 -> updateUiState1(uiState[i])
                    1 -> updateUiState2(uiState[i])
                    2 -> updateUiState3(uiState[i])
                }
            }
        }
    }

    private fun updateUiState1(uiState: MoreDetailsState) {
        updateArticleText1(uiState.description)
        updateOpenUrlButton1(uiState.url)
        updateSourceLabel1(uiState.source)
        updateLastFMLogo1(uiState.sourceLogoUrl)
    }

    private fun updateUiState2(uiState: MoreDetailsState) {
        updateArticleText2(uiState.description)
        updateOpenUrlButton2(uiState.url)
        updateSourceLabel2(uiState.source)
        updateLastFMLogo2(uiState.sourceLogoUrl)
    }

    private fun updateUiState3(uiState: MoreDetailsState) {
        updateArticleText3(uiState.description)
        updateOpenUrlButton3(uiState.url)
        updateSourceLabel3(uiState.source)
        updateLastFMLogo3(uiState.sourceLogoUrl)
    }

    private fun updateOpenUrlButton1(url: String) {
        openUrlButton1.setOnClickListener {
            navigateToUrl(url)
        }
    }

    private fun updateSourceLabel1(source: String) {
        sourceLabel1.text = source
    }

    private fun updateLastFMLogo1(url: String) {
        Picasso.get().load(url).into(sourceImageView1)
    }

    private fun updateArticleText1(articleDescription: String) {
        cardDescriptionTextView1.text = Html.fromHtml(articleDescription)
    }

    private fun updateOpenUrlButton2(url: String) {
        openUrlButton2.visibility = Button.VISIBLE
        openUrlButton2.setOnClickListener {
            navigateToUrl(url)
        }
    }

    private fun updateSourceLabel2(source: String) {
        sourceLabel2.text = source
    }

    private fun updateLastFMLogo2(url: String) {
        Picasso.get().load(url).into(sourceImageView2)
    }

    private fun updateArticleText2(articleDescription: String) {
        cardDescriptionTextView2.text = Html.fromHtml(articleDescription)
    }

    private fun updateOpenUrlButton3(url: String) {
        openUrlButton3.visibility = Button.VISIBLE
        openUrlButton3.setOnClickListener {
            navigateToUrl(url)
        }
    }

    private fun updateSourceLabel3(source: String) {
        sourceLabel3.text = source
    }

    private fun updateLastFMLogo3(url: String) {
        Picasso.get().load(url).into(sourceImageView3)
    }

    private fun updateArticleText3(articleDescription: String) {
        cardDescriptionTextView3.text = Html.fromHtml(articleDescription)
    }


    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
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