package ayds.songinfo.moredetails.data.local.article

import ayds.songinfo.moredetails.domain.Article.ArtistArticle

interface ArticleLocalStorage {
    fun getArticle(artistName: String): ArtistArticle?
    fun saveArticle(article: ArtistArticle)
}