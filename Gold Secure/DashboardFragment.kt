package com.example.goldsecure.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.goldsecure.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DashboardFragment : Fragment() {

    private lateinit var etPassword: EditText
    private lateinit var btnSubmitPassword: Button
    private lateinit var tvFileContent: TextView

    // Правильный пароль
    private val correctPassword = "5678"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        etPassword = view.findViewById(R.id.etPassword)
        btnSubmitPassword = view.findViewById(R.id.btnSubmitPassword)
        tvFileContent = view.findViewById(R.id.tvFileContent)

        btnSubmitPassword.setOnClickListener {
            val inputPassword = etPassword.text.toString()

            if (inputPassword == correctPassword) {
                // Если пароль верный, открыть и показать содержимое файла
                readFileContent()
            } else {
                tvFileContent.text = "Incorrect password"
            }
        }

        return view
    }

    // Метод для чтения файла из assets и отображения его содержимого
    private fun readFileContent() {
        try {
            // Открываем файл из папки assets
            val inputStream = requireContext().assets.open("sample.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Чтение содержимого файла
            val content = reader.use { it.readText() }

            // Закрываем потоки
            reader.close()

            // Отображаем содержимое файла в TextView
            tvFileContent.text = content

        } catch (e: IOException) {
            Log.e("DashboardFragment", "Error reading file", e)
            tvFileContent.text = "Error reading file"
        }
    }
}
