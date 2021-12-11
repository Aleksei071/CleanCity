package com.example.cleancityclient.modelos

import com.example.cleancityclient.api.Parametro
import org.json.JSONException
import org.json.JSONObject

class CamionModelo() {
    private var matricula: String? = null
    private var conductor: String? = null
    private var latitud: Double? = null
    private var longitud: Double? = null

    constructor(matricula: String?, conductor: String?, latitud: Double?, longitud: Double?): this(){
        this.matricula = matricula
        this.conductor = conductor
        this.latitud = latitud
        this.longitud = longitud
    }

    @Throws(JSONException::class)
    constructor(fromJSON: JSONObject) : this(){
        matricula = fromJSON.getString("Matricula_Camion")
        conductor = fromJSON.getString("Conductor_Camion")
        latitud = fromJSON.getDouble("Latitud_Camion")
        longitud = fromJSON.getDouble("Longitud_Camion")
    }

    fun busqueda(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        try {
            retornar.add(Parametro("Matricula_Camion", getMatricula()))
        } catch (e: Exception) {
        }
        return retornar
    }

    fun parametros(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        try {
            retornar.add(Parametro("Matricula_Camion", getMatricula()))
        } catch (e: Exception) {
        }
        retornar.add(Parametro("Conductor_Camion", getConductor()))
        retornar.add(Parametro("Latitud_Camion", getLatitud()))
        retornar.add(Parametro("Longitud_Camion", getLongitud()))
        return retornar
    }

    override fun toString(): String {
        return "CamionModelo(matricula=$matricula, conductor=$conductor, latitud=$latitud, longitud=$longitud)"
    }

    fun getMatricula(): String?{ return matricula }

    fun setMatricula(matricula: String?){ this.matricula = matricula }

    fun getConductor(): String?{ return conductor }

    fun setConductor(conductor: String?){ this.conductor = conductor}

    fun getLatitud(): Double? { return latitud}

    fun setLatitud(latitud: Double?){ this.latitud = latitud}

    fun getLongitud(): Double?{ return longitud}

    fun setLongitud(longitud: Double?){ this.longitud = longitud}
}