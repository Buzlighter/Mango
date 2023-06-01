package com.test.mango.profile

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.hbb20.CountryCodePicker
import com.test.mango.App
import com.test.mango.R
import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.data.ext.collectIt
import com.test.mango.data.model.Status
import com.test.mango.databinding.ActivityProfileBinding
import com.test.mango.profile.di.DaggerProfileComponent
import com.test.mango.profile.model.ProfileData
import com.test.mango.profile.model.User
import com.test.mango.registration.ACCESS_TOKEN_KEY
import com.test.mango.registration.REFRESH_TOKEN_KEY
import com.test.mango.registration.RegistrationActivity
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    lateinit var encryptedSharedPreferences: SharedPreferences

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private val profileViewModel by viewModels<ProfileViewModel> { profileViewModelFactory}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profileComponent = DaggerProfileComponent
            .builder()
            .appComponent((application as App).appComponent)
            .build()
        profileComponent.inject(this)

        encryptedSharedPreferences = profileComponent.getEncryptedSharedPref()

        Log.i("ssss", encryptedSharedPreferences.getString(ACCESS_TOKEN_KEY, "").toString())

        profileViewModel.getUser(encryptedSharedPreferences.getString(ACCESS_TOKEN_KEY, "").toString())


        profileViewModel.userFlow.collectIt(this, observeUserResponse)
    }

    private val observeUserResponse: (Status<User>) -> Unit = { response ->
        when(response) {
            is Status.Success -> {
                setUser(response.data!!.profileData)
            }
            is Status.Error -> {
                Toast.makeText(this, R.string.error_user, Toast.LENGTH_SHORT).show()
            }
            is Status.Loading -> {  }
            else ->  { }
        }
    }

    private fun setUser(profile: ProfileData) {
        binding.apply {
            Glide.with(binding.avatar)
                .load(profile.avatar ?: AppCompatResources.getDrawable(this@ProfileActivity, R.drawable.unknown_avatar))
                .fitCenter()
                .into(binding.avatar)

            name.text = profile.name
            username.text = profile.username
            city.text = profile.city
            birthday.text = profile.birthday
            phone.text = profile.phone

        }
    }

}