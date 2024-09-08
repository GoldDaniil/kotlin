package com.example.goldsecure.utils

object CaptchaUtils {
    fun verifyCaptcha(captcha: String): Boolean {
        // Implement your captcha validation logic here
        return captcha == "expected_captcha_value"
    }
}
