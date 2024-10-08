package com.example.goldsecure.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.goldsecure.NoteActivity
import com.example.goldsecure.R

class DashboardFragment : Fragment() {

    private lateinit var etPassword: EditText
    private lateinit var btnSubmitPassword: Button
    private lateinit var tvFileContent: TextView

    // правильный пароль
    private val correctPassword = "5678"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        etPassword = view.findViewById(R.id.etPassword)
        btnSubmitPassword = view.findViewById(R.id.btnSubmitPassword)
        tvFileContent = view.findViewById(R.id.tvFileContent)

        btnSubmitPassword.setOnClickListener {
            val inputPassword = etPassword.text.toString()

            if (inputPassword == correctPassword) {
                // если пароль верный, открыть NoteActivity
                val intent = Intent(requireContext(), NoteActivity::class.java)
                startActivity(intent)
            } else {
                tvFileContent.text = "incorrect password"
            }
        }

        return view
    }
}
