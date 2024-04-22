package ayds.songinfo.moredetails.fulllogic

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
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

class OtherInfoWindow : Activity() {
    private var articleTextPanel: TextView? = null
    private var dataBase: ArticleDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        articleTextPanel = findViewById(R.id.textPane1)
        dataBase = databaseBuilder(this, ArticleDatabase::class.java, "database-name-thename").build()
        getARtistInfo(intent.getStringExtra("artistName"))
    }

    private fun getARtistInfo(artistName: String?) {
        createArticle(artistName, lastFMAPIURLBuilder())
    }

    private fun lastFMAPIURLBuilder(): LastFMAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        return retrofit.create(LastFMAPI::class.java)
    }

    private fun createArticle(
        artistName: String?,
        lastFMAPI: LastFMAPI
    ) {
        Thread {
            val articleInDataBase = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            val textToShowInArticle = if (articleInDataBase != null) {
                getArticleFromDataBase(articleInDataBase)
            } else {
                getArticleFromService(lastFMAPI, artistName)
            }

            val imageUrl ="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
            runOnUiThread {
                Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView1) as ImageView)
                articleTextPanel!!.text = Html.fromHtml(textToShowInArticle)
            }
        }.start()
    }

    private fun getArticleFromDataBase(
        articleInDataBase: ArticleEntity
    ): String {
        val textToShowInArticle1 = "[*]" + articleInDataBase.biography
        setViewArticleButton(articleInDataBase.articleUrl)
        return textToShowInArticle1
    }

    private fun getArticleFromService(
        lastFMAPI: LastFMAPI,
        artistName: String
    ): String {
        var articleText = ""
        val artistCallResponse: Response<String>
        try {
            artistCallResponse = lastFMAPI.getArtistInfo(artistName).execute()
            Log.e("TAG", "JSON " + artistCallResponse.body())
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
            dataBase!!.ArticleDao().insertArticle(
                ArticleEntity(
                    artistName, articleText, articleUrl.asString
                )
            )
        }
            .start()
    }



    private fun setViewArticleButton(urlString: String) {
        findViewById<View>(R.id.openUrlButton1).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlString))
            startActivity(intent)
        }
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
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
