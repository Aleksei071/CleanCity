package com.example.cleancityclient.api

import android.util.Log
import com.example.cleancityclient.interfaces.ICallBackFunciono
import com.example.cleancityclient.interfaces.ICallBackRecorrido
import com.example.cleancityclient.modelos.RecorridoModelo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Boolean.parseBoolean
import java.util.*

class RecorridoConexion: ConexionBase() {
    fun seleccionar(callback: ICallBackRecorrido) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/seleccionar.php",
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
                        val listaRetorno: MutableList<RecorridoModelo> =
                            ArrayList<RecorridoModelo>()
                        for (i in 0 until lista.length()) {
                            val rec = RecorridoModelo(lista.getJSONObject(i))
                            listaRetorno.add(rec)
                        }
                        callback.retornar(listaRetorno)
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun buscar(rec: RecorridoModelo, callback: ICallBackRecorrido) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/buscar.php",
            rec.busqueda(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val respuesta = response.body!!.string()
                    try {
                        val lista = JSONArray(respuesta)
                        val recorrido = RecorridoModelo(lista.getJSONObject(0))
                        callback.retorno(recorrido)
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun insertar(rec: RecorridoModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/insertar.php",
            rec.parametros(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun editar(rec: RecorridoModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/editar.php",
            rec.parametros(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun eliminar(rec: RecorridoModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/recorrido/eliminar.php",
            rec.busqueda(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    try {
                        val funciono = response.body!!.string()
                        callback.retornar(parseBoolean(funciono))
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }
}