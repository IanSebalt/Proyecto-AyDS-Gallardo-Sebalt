package ayds.songinfo.moredetails.data.local.article.room

import ayds.songinfo.moredetails.data.local.article.ArticleLocalStorage
import ayds.songinfo.moredetails.domain.Card

internal class ArticleLocalStorageRoomImpl(
    database: ArticleDatabase,
):ArticleLocalStorage {

    private val articleDao: ArticleDao = database.ArticleDao()

    override fun getArticle(artistName: String): Card? {
        return articleDao.getArticleByArtistName(artistName)?.toCard()
    }

    override fun saveArticle(card: Card) {
        articleDao.insertArticle(card.toArticleEntity())
    }

    private fun ArticleEntity.toCard(): Card {
        return Card(artistName = this.artistName, description = this.biography, infoUrl = this.articleUrl)
    }

    private fun Card.toArticleEntity(): ArticleEntity {
        return ArticleEntity(artistName, description, infoUrl)
    }


}