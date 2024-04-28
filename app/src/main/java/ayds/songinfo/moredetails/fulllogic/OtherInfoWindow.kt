package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.room.Room.databaseBuilder
import ayds.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

data class ArtistBiography(val artistName: String, val biography: String, val articleUrl: String) {

}

class OtherInfoWindow : Activity() {
    private lateinit var articleTextPanel: TextView
    private lateinit var openUrlButton: Button

    private lateinit var articleDatabase: ArticleDatabase
    private lateinit var lastFMAPI: LastFMAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        initProperties()
        initDatabase()
        initLastFMAPI()
        getArtistInfoAsync()
    }

    private fun initProperties() {
        articleTextPanel = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton1)
    }

    private fun initDatabase() {
        articleDatabase =
            databaseBuilder(this, ArticleDatabase::class.java, "database-name-thename").build()
    }

    private fun getArtistInfoAsync() {
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistInfo() {
        val articleInDataBase = getArtistInfoFromDB()
        val textToShowInArticle = if (articleInDataBase != null) {
            getArticleTextFromDB(articleInDataBase)
        } else {
            getArticleTextFromService()
        }

        runOnUiThread {
            Picasso.get().load(SOURCE_IMAGE_URL)
                .into(findViewById<View>(R.id.imageView1) as ImageView)
            articleTextPanel.text = Html.fromHtml(textToShowInArticle)
        }
    }

    private fun getArtistInfoFromDB() =
        articleDatabase.ArticleDao().getArticleByArtistName(getArtistName())

    private fun getArtistName() = intent.getStringExtra(ARTIST_NAME_EXTRA) ?: ""

    private fun initLastFMAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        lastFMAPI = retrofit.create(LastFMAPI::class.java)
    }

    private fun getArticleTextFromDB(
        articleInDataBase: ArticleEntity
    ): String {
        val textToShowInArticle1 = "[*]" + articleInDataBase.biography
        setViewArticleButton(articleInDataBase.articleUrl)
        return textToShowInArticle1
    }

    private fun getArticleTextFromService(): String {
        val artistName = getArtistName()
        var articleText = ""
        val artistCallResponse: Response<String>
        try {
            artistCallResponse = lastFMAPI.getArtistInfo(artistName).execute()
            val dataJson = Gson().fromJson(artistCallResponse.body(), JsonObject::class.java)
            val artist = dataJson["artist"].getAsJsonObject()
            val artistBiographyContent = artist["bio"].getAsJsonObject()["content"]
            val articleUrl = artist["url"]

            if (artistBiographyContent == null) {
                articleText = "No Results"
            } else {
                articleText = artistBiographyContent.asString.replace("\\n", "\n")
                articleText = textToHtml(articleText, artistName)
                saveArticleInDataBase(artistName, articleText, articleUrl)
            }

            setViewArticleButton(articleUrl.asString)

        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return articleText
    }

    private fun saveArticleInDataBase(
        artistName: String,
        articleText: String,
        articleUrl: JsonElement
    ) {
        Thread {
            articleDatabase.ArticleDao().insertArticle(
                ArticleEntity(
                    artistName, articleText, articleUrl.asString
                )
            )
        }
            .start()
    }



    private fun setViewArticleButton(urlString: String) {
        openUrlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlString))
            startActivity(intent)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val SOURCE_IMAGE_URL ="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

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
