package com.test.mango.auth.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.hbb20.CountryCodePicker
import com.test.mango.App
import com.test.mango.R
import com.test.mango.auth.ui.di.DaggerAuthComponent
import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.data.ext.collectIt
import com.test.mango.data.model.Status
import com.test.mango.databinding.ActivityAuthBinding
import com.test.mango.profile.ProfileActivity
import com.test.mango.registration.ACCESS_TOKEN_KEY
import com.test.mango.registration.REFRESH_TOKEN_KEY
import com.test.mango.registration.RegistrationActivity
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    lateinit var encryptedSharedPreferences: SharedPreferences

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private val authViewModel by viewModels<AuthViewModel> { authViewModelFactory }
    private var selectedCountryCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authComponent = DaggerAuthComponent
            .builder()
            .appComponent((application as App).appComponent)
            .build()
        authComponent.inject(this)

        encryptedSharedPreferences = authComponent.getEncryptedSharedPref()

        adjustCountryPicker()
        binding.countryPicker.setOnCountryChangeListener(onCountryChangeListener)
        binding.registerBtn.setOnClickListener(registerClickListener)
        binding.numInput.addTextChangedListener(afterTextChangedListener)
        binding.loginBtn.setOnClickListener(loginClickListener)
    }

    private val observeAuthResponse: (Status<AuthResponse>) -> Unit = { response ->
        when(response) {
            is Status.Success -> {
                Toast.makeText(this, R.string.enter_hail, Toast.LENGTH_SHORT).show()
                binding.smsInput.visibility = View.VISIBLE
            }
            is Status.Error -> {
                Toast.makeText(this, R.string.enter_error, Toast.LENGTH_SHORT).show()
                binding.smsInput.visibility = View.GONE
            }
            is Status.Loading -> { binding.smsInput.visibility = View.GONE }
            else ->  { binding.smsInput.visibility = View.GONE }
        }
    }

    private val observeAuthCheckResponse: (Status<AuthTokenResponse>) -> Unit = { response ->
        when(response) {
            is Status.Success -> {
                storeData(response.data!!)
                checkUser(response.data)
                outOfLoadingState()
            }
            is Status.Error -> { outOfLoadingState() }
            is Status.Loading -> { loadingViewState() }
            else -> {}
        }
    }
    private fun storeData(data: AuthTokenResponse) {
        val editor = encryptedSharedPreferences.edit().apply {
            putString(ACCESS_TOKEN_KEY, data.accessToken)
            putString(REFRESH_TOKEN_KEY, data.refreshToken)
        }
        editor.apply()
    }
    private fun checkUser(data: AuthTokenResponse) {
        if (data.isUserExist) {
            val profileIntent = Intent(this@AuthActivity, ProfileActivity::class.java)
            this@AuthActivity.startActivity(profileIntent, bundleOf())
        } else {
            val registerIntent = Intent(this@AuthActivity, RegistrationActivity::class.java)
            this@AuthActivity.startActivity(registerIntent)
        }
    }


    private val registerClickListener = OnClickListener {
        val intent = Intent(this, RegistrationActivity::class.java)
        this@AuthActivity.startActivity(intent)
    }

    private val onCountryChangeListener = CountryCodePicker.OnCountryChangeListener {
        selectedCountryCode = binding.countryPicker.selectedCountryCode
    }

    private val afterTextChangedListener = object : TextWatcher {
        override fun beforeTextChanged(numInput: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(numInput: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(numInput: Editable) {
            binding.loginBtn.isEnabled = numInput.length == 10
        }
    }

    private val loginClickListener = OnClickListener {
        val num = "+" + selectedCountryCode + binding.numInput.text.toString()
        authViewModel.authUser(
            AuthBody(num)
        )
        authViewModel.authFlow.collectIt(this, observeAuthResponse)
        authViewModel.checkAuthUserFlow.collectIt(this, observeAuthCheckResponse)
    }
    private fun adjustCountryPicker() {
        binding.countryPicker.detectNetworkCountry(true)
        binding.countryPicker.detectLocaleCountry(true)
        selectedCountryCode = binding.countryPicker.selectedCountryCode
    }

    override fun onStop() {
        super.onStop()
        authViewModel.authFlow.value = Status.StateLess()
        authViewModel.checkAuthUserFlow.value = Status.StateLess()
    }

    private fun loadingViewState() { binding.loadProgressBar.visibility = View.VISIBLE }
    private fun outOfLoadingState() { binding.loadProgressBar.visibility = View.GONE }
}