package com.example.architecturetest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false) // exportScheme -> 데이터 베이스 이전
abstract class WordRoomDatabase : RoomDatabase() {

	abstract fun wordDao() : WordDao

	companion object {

		@Volatile
		private var INSTANCE: WordRoomDatabase? = null

		fun getDatabase(
			context: Context,
			scope: CoroutineScope
		): WordRoomDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					WordRoomDatabase::class.java,
					"word_database"
				).build()
				INSTANCE = instance
				instance
			}
		}
	}

}