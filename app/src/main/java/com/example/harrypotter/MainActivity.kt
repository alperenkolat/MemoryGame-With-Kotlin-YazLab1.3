package com.example.harrypotter

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.harrypotter.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)

        binding.btnGirisYap.setOnClickListener{
            var KullaniciBilgisi=binding.etMail.text.toString()
            var KullanciPass=binding.etSifre.text.toString()
            println(KullaniciBilgisi)
            auth.signInWithEmailAndPassword(KullaniciBilgisi, KullanciPass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(baseContext, "Giriş başarılı",
                            Toast.LENGTH_SHORT).show()
                       intent =Intent(applicationContext,Home::class.java)
                       startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "şifre veya e-mail yanlış",
                            Toast.LENGTH_LONG).show()
                       //intent =Intent(applicationContext,MainActivity::class.java)
                        //startActivity(intent)
                    }
                }
        }
        binding.btnKayit.setOnClickListener{
            intent =Intent(applicationContext,Register2::class.java)
            startActivity(intent)

        }

    }


}