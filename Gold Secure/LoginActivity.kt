package com.example.goldsecure

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class LoginActivity : AppCompatActivity() {

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var captchaTextView: TextView
    private lateinit var captchaEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var attemptsTextView: TextView

    private var attemptsLeft = 3
    private var currentCaptcha = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        captchaTextView = findViewById(R.id.captcha_text_view)
        captchaEditText = findViewById(R.id.captcha_edit_text)
        loginButton = findViewById(R.id.login_button)
        attemptsTextView = findViewById(R.id.attempts_text_view)

        // Скрыть капчу и количество попыток до первой ошибки
        captchaTextView.visibility = android.view.View.GONE
        captchaEditText.visibility = android.view.View.GONE
        attemptsTextView.visibility = android.view.View.GONE

        loginButton.setOnClickListener {
            val login = loginEditText.text.toString()
            val password = passwordEditText.text.toString()
            val captcha = captchaEditText.text.toString()

            if (validateCredentials(login, password)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()  // Закрытие активности входа после успешного входа
            } else {
                if (attemptsLeft > 1) {
                    attemptsLeft--
                    attemptsTextView.text = "Attempts left: $attemptsLeft"
                    attemptsTextView.visibility = android.view.View.VISIBLE
                    // Показать капчу только после первой неудачной попытки
                    if (attemptsLeft < 3) {
                        captchaTextView.visibility = android.view.View.VISIBLE
                        captchaEditText.visibility = android.view.View.VISIBLE
                        generateCaptcha()
                    }
                    showErrorMessage()
                } else {
                    Toast.makeText(this, "Too many failed attempts. Please try again later.", Toast.LENGTH_LONG).show()
                    loginButton.isEnabled = false
                }
            }
        }
    }

    private fun validateCredentials(login: String, password: String): Boolean {
        return login == "Ivan" && password == "1234" && validateCaptcha(captchaEditText.text.toString())
    }

    private fun validateCaptcha(userInput: String): Boolean {
        return userInput.equals(currentCaptcha, ignoreCase = true)
    }

    private fun generateCaptcha() {
        currentCaptcha = generateRandomCaptcha()
        captchaTextView.text = currentCaptcha
        captchaTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Invalid credentials or CAPTCHA. Try again.", Toast.LENGTH_SHORT).show()
        captchaTextView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out))
    }

    private fun generateRandomCaptcha(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random(System.currentTimeMillis())
        val captcha = StringBuilder()
        for (i in 0 until 5) {
            val index = random.nextInt(chars.length)
            captcha.append(chars[index])
        }
        return captcha.toString()
    }
}
