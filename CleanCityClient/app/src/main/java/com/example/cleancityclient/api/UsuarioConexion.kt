package com.example.cleancityclient.api

import android.util.Log
import com.example.cleancityclient.interfaces.ICallBackFunciono
import com.example.cleancityclient.interfaces.ICallBackUsuario
import com.example.cleancityclient.modelos.UsuarioModelo
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.lang.Boolean.parseBoolean
import java.util.*

class UsuarioConexion : ConexionBase() {
    fun seleccionar(callback: ICallBackUsuario) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/seleccionar.php",
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
                        val listaRetorno: MutableList<UsuarioModelo> = ArrayList<UsuarioModelo>()
                        var i = 0
                        while (i < lista.length()) {
                            val user = UsuarioModelo(lista.getJSONObject(i))
                            listaRetorno.add(user)
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

    fun buscar(user: UsuarioModelo, callback: ICallBackUsuario) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/buscar.php",
            user.busqueda(),
            object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("RESPONSE", e.message!!)
                }

                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    val respuesta = response.body!!.string()
                    try {
                        val lista = JSONArray(respuesta)
                        val usuario = UsuarioModelo(lista.getJSONObject(0))
                        callback.retorno(usuario)
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.message!!)
                        e.printStackTrace()
                    }
                }
            })
    }

    fun insertar(user: UsuarioModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/insertar.php",
            user.parametros(),
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

    fun editar(user: UsuarioModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/editar.php",
            user.parametros(),
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

    fun eliminar(user: UsuarioModelo, callback: ICallBackFunciono) {
        solicitar(
            "https://cleancitysp.000webhostapp.com/api/solicitudes/usuario/eliminar.php",
            user.busqueda(),
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