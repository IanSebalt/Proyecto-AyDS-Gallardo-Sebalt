package ayds.songinfo.moredetails.data.local.article.room

import androidx.room.Database
import androidx.room.RoomDatabase




@Database(entities = [ArticleEntity::class] , version = 1) //pq con el ::class no tira error de companion
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun ArticleDao(): ArticleDao
}