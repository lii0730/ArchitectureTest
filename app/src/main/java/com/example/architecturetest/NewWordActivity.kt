package com.example.architecturetest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewWordActivity : AppCompatActivity() {

	private val editWord : EditText by lazy {
		findViewById(R.id.edit_word)
	}

	private val button_save : Button by lazy {
		findViewById(R.id.button_save)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_new_word)

		button_save.setOnClickListener {
			val replyIntent = Intent()
			if(TextUtils.isEmpty(editWord.text)) {
				setResult(Activity.RESULT_CANCELED, replyIntent)
			} else {
				val word = editWord.text.toString()
				replyIntent.putExtra(EXTRA_REPLY, word)
				setResult(Activity.RESULT_OK, replyIntent)
			}
			finish()
		}
	}

	companion object {
		const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
	}
}