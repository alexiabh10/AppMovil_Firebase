package com.tesji.myappdonuts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        setTheme(R.style.Base_Theme_MyAppDonuts)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = Firebase.auth

        val txtCorreo = findViewById<TextInputEditText>(R.id.editCorreoLogin)
        val txtPasswd = findViewById<TextInputEditText>(R.id.editPasswdLogin)
        val btnIniciar = findViewById<Button>(R.id.btnIniciar)
        val buttonRegistro = findViewById<Button>(R.id.btnRegistrarse)
        val buttonContrasenia = findViewById<Button>(R.id.btnContraseña)

        btnIniciar.setOnClickListener {
            signIn(txtCorreo.text.toString().trim(), txtPasswd.text.toString().trim())
        }

        buttonRegistro.setOnClickListener {
            val intent: Intent = Intent(this, Registrarme::class.java)
            startActivity(intent)
        }

        buttonContrasenia.setOnClickListener {
            val intent: Intent = Intent(this, RecuperarContrasenia::class.java)
            startActivity(intent)
        }
    }

    private fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val verifica = user?.isEmailVerified

                    if (verifica == true) {
                        //Toast.makeText(baseContext,user?.uid.toString(), Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this, Home::class.java)
                        startActivity(intent)
                        }else{
                        Toast.makeText(baseContext,"Tienes que verificar tu correo", Toast.LENGTH_SHORT).show()
                    }
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Error de contraseña o Email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }
