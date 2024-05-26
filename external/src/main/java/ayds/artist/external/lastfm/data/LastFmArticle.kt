package ayds.artist.external.lastfm.data

sealed class LastFmArticle {

        data class ArtistArticle(
            val artistName: String,
            val biography: String,
            val articleUrl: String,
        ):LastFmArticle()

    object EmptyArtistDataExternal: LastFmArticle()



}