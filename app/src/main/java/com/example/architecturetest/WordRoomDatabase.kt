package com.example.architecturetest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
	entities = arrayOf(Word::class),
	version = 1,
	exportSchema = false // exportScheme -> 데이터 베이스 이전
)
abstract class WordRoomDatabase : RoomDatabase() {

	abstract fun wordDao(): WordDao

	class WordDatabaseCallback(
		private val scope: CoroutineScope
	) : RoomDatabase.Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			super.onCreate(db)
			INSTANCE?.let { database ->
				scope.launch {
					populateDatabase(database.wordDao())
				}
			}

		}

		suspend fun populateDatabase(wordDao: WordDao) {
			wordDao.deleteAll()

//			var word = Word("Hello")
//			wordDao.insert(word)
//
//			word = Word("World")
//			wordDao.insert(word)
		}
	}

	companion object {

		@Volatile
		private var INSTANCE: WordRoomDatabase? = null

		fun getDatabase(
			context: Context,
			scope: CoroutineScope
		): WordRoomDatabase {
			// TODO: 인스턴스 중복 생성 방지 위해 synchronized
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					WordRoomDatabase::class.java,
					"word_database"
				)
					.addCallback(WordDatabaseCallback(scope))
					.build()
				INSTANCE = instance
				instance
			}
		}
	}

}