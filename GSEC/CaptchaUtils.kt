package com.example.goldsecure.utils

object CaptchaUtils {
    fun verifyCaptcha(captcha: String): Boolean {
        return captcha == "expected_captcha_value"
    }
}
