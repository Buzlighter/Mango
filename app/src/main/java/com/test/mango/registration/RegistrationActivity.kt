package com.test.mango.registration

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.security.crypto.EncryptedSharedPreferences
import com.test.mango.App
import com.test.mango.R
import com.test.mango.auth.ui.AuthActivity
import com.test.mango.data.ext.collectIt
import com.test.mango.data.model.RegisterResponse
import com.test.mango.data.model.RegistrationBody
import com.test.mango.data.model.Status
import com.test.mango.databinding.ActivityRegistrationBinding
import com.test.mango.registration.di.DaggerRegistrationComponent
import javax.inject.Inject

const val ACCESS_TOKEN_KEY = "ATK"
const val REFRESH_TOKEN_KEY = "RTK"

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var encryptedSharedPreferences: SharedPreferences

    @Inject
    lateinit var registrationViewModelFactory: RegistrationViewModelFactory
    private val registrationViewModel by viewModels<RegistrationViewModel> { registrationViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registrationComponent = DaggerRegistrationComponent.builder()
            .appComponent((application as App).appComponent)
            .build()

        registrationComponent.inject(this)

        encryptedSharedPreferences = registrationComponent.getEncryptedSharedPref()

//        binding.numberInput.addTextChangedListener()

        binding.registerBtn.setOnClickListener {
            val registrationData = RegistrationBody(
                binding.numberInput.text.toString(),
                binding.nameInput.text.toString(),
                binding.usernameInput.text.toString()
            )
            registrationViewModel.registerUser(registrationData)
        }

        registrationViewModel.stateFlow.collectIt(this, response)
    }

    private val onEditorNumberListener = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {

        }
    }

    private val response: (Status<RegisterResponse>) -> Unit = { response ->
        when (response) {
            is Status.Success -> {
                storeData(response.data!!)
                transitionToMainScreen()
            }
            is Status.Error -> {
                showError(response.errorMsg)
            }
            else -> {}
        }
    }

    private fun storeData(data: RegisterResponse)  {
        val editor = encryptedSharedPreferences.edit().apply {
            putString(ACCESS_TOKEN_KEY, data.accessToken)
            putString(REFRESH_TOKEN_KEY, data.refreshToken)
        }
        editor.apply()
    }

    private fun transitionToMainScreen() {
        val intent = Intent(this@RegistrationActivity, AuthActivity::class.java)
        this@RegistrationActivity.startActivity(intent)
    }


    private fun showError(error: String?) {
        if (error.isNullOrEmpty()) {
            Toast.makeText(this, resources.getString(R.string.unexpected_error), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}