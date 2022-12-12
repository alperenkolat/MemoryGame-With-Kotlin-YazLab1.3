package com.example.harrypotter

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.harrypotter.databinding.ActivityMainBinding
import com.example.harrypotter.databinding.ActivityRegister2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Register2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        super.onStart()
        val binding=ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonRegisterKaydet.setOnClickListener{
            var KullaniciBilgisi=binding.editTextEmailAddress.text.toString()
            var KullanciPass=binding.editTextPassword.text.toString()
            println(KullaniciBilgisi)

            auth.createUserWithEmailAndPassword(KullaniciBilgisi,KullanciPass)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(applicationContext, "Kayıt Başarılı",
                            Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(applicationContext, "şifre en az 6 karakter olmalı geçerli mail giriniz",
                            Toast.LENGTH_LONG).show()

                    }
                }
        }


    }

}