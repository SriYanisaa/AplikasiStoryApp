package com.example.aplikasistoryapp

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasistoryapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelRegister: ViewModelRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModelRegister = ViewModelProvider(this, viewModelFactory)[ViewModelRegister::class.java]

        binding.txtLogin.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            viewModelRegister.userRegister(name, email, password).observe(this){
                when(it){
                    is Result.Success -> {
                        showLoading(false)
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                    is Result.Loading -> showLoading(true)
                    is Result.Error -> {
                        Toast.makeText(this, "Akun sudah terdaftar", Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }

        playAnimation()
    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.progresRegister.visibility = View.VISIBLE
        } else {
            binding.progresRegister.visibility = View.GONE
        }
    }

    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView2, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val txtRegister = ObjectAnimator.ofFloat(binding.txtRegister, View.ALPHA, 1f).setDuration(500)
        val txtName = ObjectAnimator.ofFloat(binding.txtname, View.ALPHA, 1f).setDuration(500)
        val edtName = ObjectAnimator.ofFloat(binding.inputName, View.ALPHA, 1f).setDuration(500)
        val txtEmail = ObjectAnimator.ofFloat(binding.txtEmail, View.ALPHA, 1f).setDuration(500)
        val edtEmail = ObjectAnimator.ofFloat(binding.inputEmail, View.ALPHA, 1f).setDuration(500)
        val txtPassword = ObjectAnimator.ofFloat(binding.txtPassword, View.ALPHA, 1f).setDuration(500)
        val edtPassword = ObjectAnimator.ofFloat(binding.inputPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)
        val txtConfirm = ObjectAnimator.ofFloat(binding.txtConfirm, View.ALPHA, 1f).setDuration(500)
        val txtLogin = ObjectAnimator.ofFloat(binding.txtLogin, View.ALPHA, 1f).setDuration(500)

        val setName = AnimatorSet().apply { playTogether(txtName, edtName) }
        val setEmail = AnimatorSet().apply { playTogether(txtEmail, edtEmail) }
        val setPassword = AnimatorSet().apply { playTogether(txtPassword, edtPassword) }
        val setConfirm = AnimatorSet(). apply { playTogether(txtConfirm, txtLogin) }
        AnimatorSet().apply {
            playSequentially(txtRegister, setName, setEmail, setPassword, btnRegister, setConfirm)
            start()
        }
    }
}