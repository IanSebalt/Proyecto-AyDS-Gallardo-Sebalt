package ayds.songinfo.moredetails.data.local.article.room

import androidx.room.Entity


@Entity(primaryKeys = ["artistName", "source"])
data class CardEntity(
    val artistName: String,
    val content: String,
    val url: String,
    val source: Int,
    val logoUrl: String,
)

