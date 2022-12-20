package com.example.harrypotter

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.harrypotter.databinding.ActivityMainBinding
import com.example.harrypotter.databinding.ActivityPasswordChangeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordChange : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityPasswordChangeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityPasswordChangeBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)


        binding.btnGirisYap.setOnClickListener{
             var newPass=binding.pass.text.toString()
            var KullaniciBilgisi=binding.etMail.text.toString()
            var KullanciPass=binding.etSifre.text.toString()
            auth.signInWithEmailAndPassword(KullaniciBilgisi, KullanciPass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful){
                        val user=auth.currentUser
                        user?.updatePassword(newPass)?.addOnCompleteListener(){}

                        Toast.makeText(baseContext, "şifre değişti! ",
                            Toast.LENGTH_SHORT).show()
                    }
                    else{  Toast.makeText(baseContext, "şifre değişemedi!! ",
                        Toast.LENGTH_SHORT).show()}

                }

        }

        binding.button.setOnClickListener{

            intent = Intent(applicationContext, Home::class.java)
            startActivity(intent)

        }
    }

}