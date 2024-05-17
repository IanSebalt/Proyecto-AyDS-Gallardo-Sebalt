package ayds.songinfo.moredetails.data.local.article.room

import androidx.room.Database
import androidx.room.RoomDatabase




@Database(entities = [ArticleEntity::class] , version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun ArticleDao(): ArticleDao
}