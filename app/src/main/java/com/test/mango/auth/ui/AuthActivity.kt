package com.test.mango.auth.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import com.test.mango.R
import com.test.mango.databinding.ActivityAuthBinding
import com.test.mango.registration.RegistrationActivity

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

//    lateinit var authViewModelFactory: AuthViewModelFactory
//    private val authViewModel by viewModels<AuthViewModel> { loginViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener(registerClickListener)

//        binding.usernameInput.addTextChangedListener(afterTextChangedListener)
//        binding.passwordInput.addTextChangedListener(afterTextChangedListener)
//        binding.passwordInput.setOnEditorActionListener(onEditChangeListener)
//        binding.loginBtn.setOnClickListener(loginClickListener)
    }

    private val registerClickListener = View.OnClickListener {
        val intent = Intent(this, RegistrationActivity::class.java)
        this@AuthActivity.startActivity(intent)
    }

//    private val onEditChangeListener = TextView.OnEditorActionListener { _, actionId, _ ->
//        if (actionId == EditorInfo.IME_ACTION_DONE) {
//            authViewModel.login(
//                binding.usernameInput.text.toString(),
//                binding.passwordInput.text.toString()
//            )
//        }
//        false
//    }
//
//    private val afterTextChangedListener = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//        override fun afterTextChanged(s: Editable) {
//            authViewModel.loginDataChanged(
//                binding.usernameInput.text.toString(),
//                binding.passwordInput.toString()
//            )
//        }
//    }
}