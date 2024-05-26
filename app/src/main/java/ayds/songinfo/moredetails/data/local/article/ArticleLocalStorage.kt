package ayds.songinfo.moredetails.data.local.article

import ayds.songinfo.moredetails.domain.Card

interface ArticleLocalStorage {
    fun getArticle(artistName: String): Card?
    fun saveArticle(card: Card)
}