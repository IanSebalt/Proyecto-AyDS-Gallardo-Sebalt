package ayds.songinfo.moredetails.domain

import ayds.songinfo.moredetails.domain.Article.ArtistArticle

interface ArticleRepository {

    fun getArticleByArtistName(artistName: String): ArtistArticle

}