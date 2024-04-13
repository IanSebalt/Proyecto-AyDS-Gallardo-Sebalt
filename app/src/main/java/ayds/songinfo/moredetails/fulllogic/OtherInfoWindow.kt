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
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.Locale

class OtherInfoWindow : Activity() {
    private var textPane1: TextView? = null

    //private JPanel imagePanel;
    // private JLabel posterImageLabel;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        textPane1 = findViewById(R.id.textPane1)
        open(intent.getStringExtra("artistName"))
    }

    private fun getARtistInfo(artistName: String?) {

        // create
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/2.0/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)
        Log.e("TAG", "artistName $artistName")
        createArticleThread(artistName, lastFMAPI)
    }

    private fun createArticleThread(
        artistName: String?,
        lastFMAPI: LastFMAPI
    ) {
        Thread {
            val article = dataBase!!.ArticleDao().getArticleByArtistName(artistName!!)
            var text = ""
            if (article != null) { // exists in db
                text = "[*]" + article.biography
                getArticleFromDataBase(article)
            } else { // get from service
                text = getArticleFromService(lastFMAPI, artistName, text)
            }
            val imageUrl =
                "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"
            Log.e("TAG", "Get Image from $imageUrl")
            val finalText = text
            runOnUiThread {
                Picasso.get().load(imageUrl).into(findViewById<View>(R.id.imageView1) as ImageView)
                textPane1!!.text = Html.fromHtml(finalText)
            }
        }.start()
    }

    private fun getArticleFromService(
        lastFMAPI: LastFMAPI,
        artistName: String,
        text: String
    ): String {
        var text1 = text
        val callResponse: Response<String>
        try {
            callResponse = lastFMAPI.getArtistInfo(artistName).execute()
            Log.e("TAG", "JSON " + callResponse.body())
            val gson = Gson()
            val jobj = gson.fromJson(callResponse.body(), JsonObject::class.java)
            val artist = jobj["artist"].getAsJsonObject()
            val bio = artist["bio"].getAsJsonObject()
            val extract = bio["content"]
            val url = artist["url"]
            if (extract == null) {
                text1 = "No Results"
            } else {
                text1 = extract.asString.replace("\\n", "\n")
                text1 = textToHtml(text1, artistName)


                // save to DB  <o/
                val text2 = text1
                Thread {
                    dataBase!!.ArticleDao().insertArticle(
                        ArticleEntity(
                            artistName, text2, url.asString
                        )
                    )
                }
                    .start()
            }
            val urlString = url.asString
            findViewById<View>(R.id.openUrlButton1).setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(urlString))
                startActivity(intent)
            }
        } catch (e1: IOException) {
            Log.e("TAG", "Error $e1")
            e1.printStackTrace()
        }
        return text1
    }

    private fun getArticleFromDataBase(article: ArticleEntity) {
        val urlString = article.articleUrl
        findViewById<View>(R.id.openUrlButton1).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(urlString))
            startActivity(intent)
        }
    }

    private var dataBase: ArticleDatabase? = null
    private fun open(artist: String?) {
        dataBase =
            databaseBuilder(this, ArticleDatabase::class.java, "database-name-thename").build()
        Thread {
            dataBase!!.ArticleDao().insertArticle(ArticleEntity("test", "sarasa", ""))
            Log.e("TAG", "" + dataBase!!.ArticleDao().getArticleByArtistName("test"))
            Log.e("TAG", "" + dataBase!!.ArticleDao().getArticleByArtistName("nada"))
        }.start()
        getARtistInfo(artist)
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
