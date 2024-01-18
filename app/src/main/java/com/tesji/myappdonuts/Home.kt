package com.tesji.myappdonuts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {

    //private val db = FirebaseFirestore.getInstance()
    //private val tuColeccion=db.collection("productos")
    //private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val db : FirebaseFirestore = FirebaseFirestore.getInstance()

        val txId = findViewById<TextInputEditText>(R.id.txt_ID)
        val txNombre = findViewById<EditText>(R.id.txt_Nombre)
        val txtPrecio = findViewById<EditText>(R.id.txt_Precio)
        val btGuardar = findViewById<Button>(R.id.btGuardar)
        val btConsultar = findViewById<Button>(R.id.btConsultar)
        val btActualizar = findViewById<Button>(R.id.btActulizar)
        val btEliminar = findViewById<Button>(R.id.btEliminar)
        val tvConsultar = findViewById<TextView>(R.id.tvConsultar)


        btConsultar.setOnClickListener {

            var datosList = mutableListOf<String>()
            db.collection("productos")
                .get()
                .addOnSuccessListener { resultado ->
                    for (documento in resultado){
                        val data = documento.data
                        val id = documento.id
                        val formatedData = "$id:" +
                                "Nombre: ${data["nombre"]}\n,"+
                                "Precio: ${data["precio"]}\n"
                        datosList.add(formatedData)
                    }
                    tvConsultar.text = datosList.joinToString ("\n" )
                }
                .addOnFailureListener { exception ->
                    tvConsultar.text = "No se ha podido consultar"
                }

            /*var datos = ""
            db.collection("productos")
                .get()
                .addOnSuccessListener { resultado ->
                    for(documento in resultado){
                        datos += "${documento.id}: ${documento.data}\n"
                    }
                    tvConsultar.text = datos
                }
                .addOnFailureListener{exception ->
                    tvConsultar.text = "No se ha podido consultar"

                }*/

        }

        btGuardar.setOnClickListener {
            guardarDatos( db )
        }

        btActualizar.setOnClickListener {
            guardarDatos(db)
        }

        btEliminar.setOnClickListener {
            if (txId.text.toString().trim().isNotBlank()) {

                db.collection("productos")
                    .document(txId.text.toString())
                    .delete()
                    .addOnSuccessListener { _ ->

                        tvConsultar.text = "Eliminado Correctamente"
                    }
                    .addOnFailureListener { _ ->
                        tvConsultar.text = "No se ha podido Eliminar"

                    }

            }
        }
    }

    private fun guardarDatos(db: FirebaseFirestore) {
        val txId = findViewById<EditText>(R.id.txt_ID)
        val txNombre = findViewById<TextInputEditText>(R.id.txt_Nombre)
        val txtPrecio = findViewById<TextInputEditText>(R.id.txt_Precio)
        val tvConsultar = findViewById<TextView>(R.id.tvConsultar)

        if (txNombre.text.toString().trim().isNotBlank() &&
            txtPrecio.text.toString().trim().isNotBlank() &&
            txId.text.toString().trim().isNotBlank()
        ) {

            var dato = hashMapOf(
                // "id" to txId.text,
                "nombre" to txNombre.text.toString(),
                "precio" to txtPrecio.text.toString()
            )
            db.collection("productos")
                .document(txId.text.toString())
                .set(dato)
                .addOnSuccessListener { _ ->

                    tvConsultar.text = "Añadido Correctamente"
                }
                .addOnFailureListener { _ ->
                    tvConsultar.text = "No se ha podido añadir"

                }

        }
    }


}