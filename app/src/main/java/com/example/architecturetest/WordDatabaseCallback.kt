package com.example.architecturetest

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KParameter

class WordDatabaseCallback(
	private val scope : CoroutineScope
): RoomDatabase.Callback() {
	override fun onCreate(db: SupportSQLiteDatabase) {
		super.onCreate(db)
		INSTANCE?.let {

		}

	}
}