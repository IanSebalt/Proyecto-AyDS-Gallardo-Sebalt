package ayds.songinfo.moredetails.data.local.article.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: ArticleEntity)

    @Query("SELECT * FROM Articleentity WHERE artistName LIKE :artistName LIMIT 1")
    fun getArticleByArtistName(artistName: String): ArticleEntity?

}