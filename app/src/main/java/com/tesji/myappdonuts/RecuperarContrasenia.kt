package com.tesji.myappdonuts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecuperarContrasenia : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contrasenia)

        val textEmailRec = findViewById<TextInputEditText>(R.id.editCorreoContra)
        val buttonContra = findViewById<Button>(R.id.btnRecuperarContra)

        buttonContra.setOnClickListener {
            sendPassword(textEmailRec.text.toString().trim())
        }

        firebaseAuth= Firebase.auth
    }

    private fun sendPassword(email: String){
        firebaseAuth.sendPasswordResetEmail(email
        ).addOnCompleteListener(){
                task ->
            if (task.isSuccessful){
                Toast.makeText(baseContext, "Correo de cambio de contraseña enviado", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this,MainActivity:: class.java )
                startActivity(intent)
            }else{
                Toast.makeText(baseContext, "Error, No se pudo completar el proceso", Toast.LENGTH_SHORT).show()

            }
        }
    }
}