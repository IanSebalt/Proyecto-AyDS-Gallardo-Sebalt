package ayds.artist.external.lastfm.data


interface ArticleLastFMService {
    fun getArticle(artistName: String): LastFmArticle
}