package com.example.architecturetest

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

	val allWords : LiveData<List<Word>> = repository.allWords.asLiveData()

	fun insert(word: Word) = viewModelScope.launch {
		repository.insert(word)
	}
}

// ViewModel 클래스를 상속하여 정의한 클래스는 개발자가 직접 생성자를 통하여서 인스턴스를 생성할 수 없고, ViewModelProvider.Factory 인터페이스 구현 필요
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		if(modelClass.isAssignableFrom(WordViewModel::class.java)) {
			return WordViewModel(repository) as T
		}
		throw IllegalArgumentException("Unknown ViewModel Class")
	}

}