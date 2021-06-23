package com.example.architecturetest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

	// 데이터 변경사항 관찰, Flow는 네트워크 요청이나 데이터베이스 호출 등 비동기 작업에서 값을 생성할 수 있는 값을 한번에 하나씩 생성
	@Query("SELECT * FROM word_table ORDER BY word ASC")
	fun getAlphabetizedWords() : Flow<List<Word>>

	@Insert(onConflict = OnConflictStrategy.IGNORE) // 이미 목록에 있는 단어와 정확하게 같다면 새로운 단어를 무시
	suspend fun insert(word: Word)

	@Query("DELETE FROM word_table")
	suspend fun deleteAll()

}