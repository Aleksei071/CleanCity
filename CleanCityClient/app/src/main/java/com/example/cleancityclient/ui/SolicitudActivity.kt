package com.example.cleancityclient.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.cleancityclient.R
import com.example.cleancityclient.api.SolicitudConexion
import com.example.cleancityclient.api.UsuarioConexion
import com.example.cleancityclient.interfaces.ICallBackUsuario
import com.example.cleancityclient.modelos.SolicitudModelo
import com.example.cleancityclient.modelos.UsuarioModelo
import java.util.*

class SolicitudActivity : AppCompatActivity() {
    private var sol = SolicitudModelo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solicitud)

        Objects.requireNonNull(supportActionBar)!!.hide()

        sol = intent.getSerializableExtra("Solicitud") as SolicitudModelo

        buscarDatos(sol)
    }

    private fun buscarDatos(sol: SolicitudModelo){
        val conUser = UsuarioConexion()
        val user = UsuarioModelo()

        user.setRut(sol.getRutUser())

        conUser.buscar(user, object : ICallBackUsuario{
            override fun retornar(resultado: List<UsuarioModelo>) { }

            override fun retorno(resultado: UsuarioModelo) {
                this@SolicitudActivity.runOnUiThread {
                    mostrarDatos(sol, resultado)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarDatos(sol: SolicitudModelo, user: UsuarioModelo){
        val nombre: TextView = findViewById(R.id.nombreTV)
        val phone: TextView = findViewById(R.id.phoneTV)
        val email: TextView = findViewById(R.id.emailTV)
        val fecha: TextView = findViewById(R.id.fechaTV)
        val desc: TextView = findViewById(R.id.descTV)
        val sector: TextView = findViewById(R.id.sectorTV)
        val tipo: TextView = findViewById(R.id.tipoTV)
        val direc: TextView = findViewById(R.id.direcTV)

        nombre.text = user.getNombre() + " " + user.getApellido()
        phone.text = user.getTelefono()
        email.text = user.getCorreo()
        fecha.text = sol.getFecha()
        desc.text = sol.getDescripcion()
        sector.text = sol.getSector()
        tipo.text = sol.getTipo()
        direc.text = sol.getDireccion()
    }
}