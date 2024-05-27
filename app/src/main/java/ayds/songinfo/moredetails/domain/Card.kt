package ayds.songinfo.moredetails.domain

data class Card(
    val artistName: String,
    val description: String,
    val infoUrl: String,
    val source: CardSource = CardSource.LAST_FM,
    val sourceLogoUrl: String = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png",
    var isLocallyStored: Boolean = false
)

enum class CardSource {
    LAST_FM,
}