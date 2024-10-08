package com.example.goldsecure

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class NoteActivity : AppCompatActivity() {

    private lateinit var etNoteContent: EditText
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        etNoteContent = findViewById(R.id.etNoteContent)
        btnExit = findViewById(R.id.btnExit)

        //чтение файла и загрузка его в EditText
        readFileContent()

        //установка слушателя на кнопку выхода
        btnExit.setOnClickListener {
            finish() //закрыть активность
        }
    }

    //метод для чтения файла и отображения содержимого в EditText
    private fun readFileContent() {
        try {
            //открываем файл из папки assets
            val inputStream = assets.open("sample.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            //читаем содержимого файла
            val content = reader.use { it.readText() }

            reader.close()

            //установка текста в textedit для редактирования
            etNoteContent.setText(content)

        } catch (e: IOException) {
            Log.e("NoteActivity", "Error reading file", e)
            etNoteContent.setText("Error reading file")
        }
    }
}
