package ayds.songinfo.moredetails.data.local.article.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CardEntity(
    @PrimaryKey
    val content: String,
    val artistName: String,
    val url: String,
    val source: Int
)

