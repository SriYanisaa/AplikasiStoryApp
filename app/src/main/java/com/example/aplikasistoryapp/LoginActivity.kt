package com.example.aplikasistoryapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasistoryapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModelLogin: ViewModelLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewModelFactory : ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModelLogin = ViewModelProvider(this, viewModelFactory)[ViewModelLogin::class.java]

        binding.tvRegis.setOnClickListener {
            intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLog.setOnClickListener {
            login()
        }

        playAnimation()
    }

    private fun showLoading(state: Boolean){
        if (state == true){
            binding.progresLogin.visibility = View.VISIBLE
        } else {
            binding.progresLogin.visibility = View.GONE
        }
    }

    private fun saveUser(user: UserModel){
        viewModelLogin.saveUser(user)
    }

    private fun login(){
        val email = binding.edtEmailogin.text.toString()
        val password = binding.edtPass.text.toString()
        viewModelLogin.login(email, password).observe(this){
            when(it){
                is Result.Success -> {
                    showLoading(false)
                    val response = it.data
                    saveUser(
                        UserModel(
                        response.loginResult.name.toString(),
                        response.loginResult.token.toString(),
                        true
                    )
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                is Result.Loading -> showLoading(true)
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val txtLogin = ObjectAnimator.ofFloat(binding.txtLogin, View.ALPHA, 1f).setDuration(500)
        val txtEmail = ObjectAnimator.ofFloat(binding.txtEmail, View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.iptEmail, View.ALPHA, 1f).setDuration(500)
        val txtPassword = ObjectAnimator.ofFloat(binding.txtPassword, View.ALPHA, 1f).setDuration(500)
        val edtPassword = ObjectAnimator.ofFloat(binding.iptPassword, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLog, View.ALPHA, 1f).setDuration(500)
        val txtConfirm = ObjectAnimator.ofFloat(binding.textView4, View.ALPHA, 1f).setDuration(500)
        val txtRegis = ObjectAnimator.ofFloat(binding.tvRegis, View.ALPHA, 1f).setDuration(500)

        val setEmail = AnimatorSet().apply { playTogether(txtEmail, edtEmail) }
        val setPassword = AnimatorSet().apply { playTogether(txtPassword, edtPassword) }
        val setConfirm = AnimatorSet(). apply { playTogether(txtConfirm, txtRegis) }
        AnimatorSet().apply {
            playSequentially(txtLogin, setEmail, setPassword, btnLogin, setConfirm)
            startDelay = 400
        }.start()

    }
}