package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.Article.ArtistArticle

interface ArticleLastFMService {
    fun getArticle(artistName: String): ArtistArticle
}