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

        // Чтение файла и загрузка его в EditText
        readFileContent()

        // Установка слушателя на кнопку выхода
        btnExit.setOnClickListener {
            finish() // Закрыть активность
        }
    }

    // Метод для чтения файла и отображения содержимого в EditText
    private fun readFileContent() {
        try {
            // Открываем файл из папки assets
            val inputStream = assets.open("sample.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Чтение содержимого файла
            val content = reader.use { it.readText() }

            // Закрытие потока чтения
            reader.close()

            // Установка текста в EditText для редактирования
            etNoteContent.setText(content)

        } catch (e: IOException) {
            Log.e("NoteActivity", "Error reading file", e)
            etNoteContent.setText("Error reading file")
        }
    }
}
