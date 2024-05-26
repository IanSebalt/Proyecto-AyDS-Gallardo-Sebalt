package ayds.artist.external.lastfm.data

import ayds.artist.external.lastfm.data.LastFmArticle.ArtistArticle

interface ArticleLastFMService {
    fun getArticle(artistName: String): ArtistArticle
}