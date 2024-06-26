package ayds.songinfo.moredetails.presentation

data class MoreDetailsState (
    val artistName: String = "",
    val description: String = "",
    val url: String = "",
    val source: String = "",
    val sourceLogoUrl: String = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png",
)