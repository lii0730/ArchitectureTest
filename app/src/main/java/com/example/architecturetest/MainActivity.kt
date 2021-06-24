package com.example.architecturetest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

	private val newWordActivityRequestCode = 1

	private val wordViewModel : WordViewModel by viewModels {
		WordViewModelFactory((application as WordsApplication).repository)
	}

	private val floatingActionButton : FloatingActionButton by lazy {
		findViewById(R.id.floatingActionButton)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
		val adapter = WordListAdapter()
		recyclerView.adapter = adapter
		recyclerView.layoutManager = LinearLayoutManager(this)

		wordViewModel.allWords.observe(this, Observer { words ->
			words?.let {
				adapter.submitList(it)
			}
		})

		floatingActionButton.setOnClickListener {

			val intent = Intent(this, NewWordActivity::class.java)
			startActivityForResult(intent, newWordActivityRequestCode)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if(requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
			data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
				val word = Word(it)
				wordViewModel.insert(word)
			}
		} else {
			Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_SHORT).show()
		}
	}
}