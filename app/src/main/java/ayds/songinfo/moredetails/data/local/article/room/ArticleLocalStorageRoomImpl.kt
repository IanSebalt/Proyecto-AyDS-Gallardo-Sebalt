package ayds.songinfo.moredetails.data.local.article.room

import ayds.songinfo.moredetails.domain.Article.ArtistArticle
import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage

internal class ArticleLocalStorageRoomImpl(
    database: ArticleDatabase,
):ArticleLocalStorage {

    private val articleDao: ArticleDao = database.ArticleDao()

    override fun getArticle(artistName: String): ArtistArticle? {
        return articleDao.getArticleByArtistName(artistName)?.toArtistArticle()
    }

    override fun saveArticle(article: ArtistArticle) {
        articleDao.insertArticle(article.toArticleEntity())
    }

    private fun ArticleEntity.toArtistArticle(): ArtistArticle {
        return ArtistArticle(this.artistName,this.biography,this.articleUrl)
    }

    private fun ArtistArticle.toArticleEntity(): ArticleEntity {
        return ArticleEntity(this.artistName,this.biography,this.articleUrl)
    }


}