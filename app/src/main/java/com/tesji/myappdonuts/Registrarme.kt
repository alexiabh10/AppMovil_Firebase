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

class Registrarme : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarme)

        firebaseAuth = Firebase.auth

        val textEmailReg = findViewById<TextInputEditText>(R.id.editCorreoReg)
        val textPaswdRed = findViewById<TextInputEditText>(R.id.editPasswdReg)
        val textPaswdReg2 = findViewById<TextInputEditText>(R.id.editPasswdReg2)

        val buttonCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        buttonCrearCuenta.setOnClickListener {
            var pass1 = textPaswdRed.text.toString().trim()
            var pass2 = textPaswdReg2.text.toString().trim()

            if(pass1.equals(pass2)){
                createAcount(textEmailReg.text.toString().trim(),textPaswdRed.text.toString().trim())
            }
            else{
                Toast.makeText(baseContext, "Error las contraseñas no coinciden" , Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun createAcount( email: String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    sendEmailVerification()
                    Toast.makeText(baseContext, "Cuenta creada Correctamente, Se requiere VERIFICACION", Toast.LENGTH_SHORT).show()
                    val intent: Intent = Intent(this,MainActivity:: class.java )
                    startActivity(intent)
                }else{
                    Toast.makeText(baseContext, "Algo salio Mal, Error:" + task.exception, Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun sendEmailVerification(){
        val user = firebaseAuth.currentUser!!
        user.sendEmailVerification().addOnCompleteListener(this){
                task->

        }
    }
}