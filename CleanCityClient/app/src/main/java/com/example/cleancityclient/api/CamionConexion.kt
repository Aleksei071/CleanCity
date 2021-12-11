package com.example.cleancityclient.api

import android.util.Log
import com.example.cleancityclient.interfaces.ICallBackCamion
import com.example.cleancityclient.interfaces.ICallBackFunciono
import com.example.cleancityclient.interfaces.ICallBackUsuario
import com.example.cleancityclient.modelos.CamionModelo
import com.example.cleancityclient.modelos.UsuarioModelo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Boolean.parseBoolean
import java.util.ArrayList

class CamionConexion : ConexionBase() {
    fun seleccionar(callback: ICallBackCamion) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/camion/seleccionar.php",
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
                        val listaRetorno: MutableList<CamionModelo> = ArrayList<CamionModelo>()
                        var i = 0
                        while (i < lista.length()) {
                            val camion = CamionModelo(lista.getJSONObject(i))
                            listaRetorno.add(camion)
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

    fun buscar(camion: CamionModelo, callback: ICallBackCamion) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/camion/buscar.php",
            camion.busqueda(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val respuesta = response.body!!.string()
                    try {
                        val lista = JSONArray(respuesta)
                        val truck = CamionModelo(lista.getJSONObject(0))
                        callback.retorno(truck)
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun insertar(camion: CamionModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/camion/insertar.php",
            camion.parametros(),
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

    fun editar(camion: CamionModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/camion/editar.php",
            camion.parametros(),
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

    fun eliminar(camion: CamionModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/camion/eliminar.php",
            camion.busqueda(),
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