package com.example.cleancityclient.api

import android.util.Log
import com.example.cleancityclient.interfaces.ICallBackFunciono
import com.example.cleancityclient.interfaces.ICallBackSolicitud
import com.example.cleancityclient.modelos.SolicitudModelo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Boolean.parseBoolean
import java.util.*

class SolicitudConexion : ConexionBase() {
    fun seleccionar(callback: ICallBackSolicitud){
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/seleccionar.php",
            ArrayList<Parametro>(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val respuesta = response.body!!.string()
                    try {
                        val lista = JSONArray(respuesta)
                        val listaRetorno: MutableList<SolicitudModelo> = ArrayList<SolicitudModelo>()
                        var i = 0
                        while (i < lista.length()) {
                            val sol = SolicitudModelo(lista.getJSONObject(i))
                            listaRetorno.add(sol)
                            i++
                        }
                        callback.retornar(listaRetorno)
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun insertar(sol: SolicitudModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/insertar.php",
            sol.parametros(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: java.lang.Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun editar(sol: SolicitudModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/editar.php",
            sol.parametros(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: java.lang.Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun eliminar(sol: SolicitudModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/eliminar.php",
            sol.busqueda(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: java.lang.Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun buscar(sol: SolicitudModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/solicitud_retiro/insertar.php",
            sol.busqueda(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: java.lang.Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }
}