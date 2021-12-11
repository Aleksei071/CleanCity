package com.example.cleancityclient.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cleancityclient.R
import com.example.cleancityclient.api.SolicitudConexion
import com.example.cleancityclient.interfaces.ICallBackSolicitud
import com.example.cleancityclient.modelos.SolicitudModelo
import java.util.*
import kotlin.collections.ArrayList


class ListadoActivity : AppCompatActivity() {
    private var lista: MutableList<SolicitudModelo> = ArrayList()
    private lateinit var tabla: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listado)

        Objects.requireNonNull(supportActionBar)!!.hide()

        tabla = findViewById(R.id.tablaSol)

        poblarTabla()
    }

    private fun poblarTabla(){
        val con = SolicitudConexion()
        con.seleccionar(object : ICallBackSolicitud{
            override fun retornar(resultado: List<SolicitudModelo>) {
                lista.addAll(resultado)
                this@ListadoActivity.runOnUiThread {
                    for (i in lista.indices) {
                        val tr = TableRow(baseContext)
                        val tv1 = TextView(baseContext)
                        val tv2 = TextView(baseContext)
                        val tv3 = TextView(baseContext)

                        tv1.layoutParams = TableRow.LayoutParams(130, TableRow.LayoutParams.WRAP_CONTENT)
                        tv1.setTextColor(Color.BLACK)
                        tv1.textSize = 16F
                        tv1.text = lista[i].getFecha()
                        tr.addView(tv1)

                        tv2.layoutParams = TableRow.LayoutParams(130, TableRow.LayoutParams.WRAP_CONTENT)
                        tv2.setTextColor(Color.BLACK);
                        tv2.textSize = 16F
                        tv2.text = lista[i].getDescripcion()
                        tr.addView(tv2)

                        tv3.layoutParams = TableRow.LayoutParams(130, TableRow.LayoutParams.WRAP_CONTENT)
                        tv3.setTextColor(Color.BLACK);
                        tv3.textSize = 16F
                        tv3.text = lista[i].getTipo()
                        tr.addView(tv3)

                        tr.layoutParams = TableRow.LayoutParams(380, TableRow.LayoutParams.WRAP_CONTENT)
                        tr.isClickable = true
                        tr.id = i

                        tr.setOnClickListener {
                            fragmentoLista(lista[i])
                        }

                        tabla.addView(tr)
                    }
                }
            }
            override fun retorno(resultado: SolicitudModelo) { }
        })
    }

    private fun fragmentoLista(sol: SolicitudModelo){
        val I = Intent(this, SolicitudActivity::class.java)
        I.putExtra("Solicitud", sol)
        startActivity(I)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val I = Intent(this, MainActivity::class.java)
        startActivity(I)
    }
}