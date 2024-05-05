package ayds.songinfo.moredetails.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
sealed class Article{
    data class ArtistArticle(
        @PrimaryKey
        val artistName: String,
        var biography: String,
        val articleUrl: String,
    ):Article(){

    }
}


