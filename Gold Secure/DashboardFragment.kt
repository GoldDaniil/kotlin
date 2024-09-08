package com.example.goldsecure.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.goldsecure.R

class DashboardFragment : Fragment() {

    private lateinit var passwordEditText: EditText
    private lateinit var unlockButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordEditText = view.findViewById(R.id.password_edit_text)
        unlockButton = view.findViewById(R.id.unlock_button)

        unlockButton.setOnClickListener {
            val password = passwordEditText.text.toString()
            if (validateDashboardPassword(password)) {
                // Show the Dashboard content
            } else {
                Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateDashboardPassword(password: String): Boolean {
        // Implement your password validation here
        return password == "dashboard_password"
    }
}
