package com.example.cleancityclient.modelos

import com.example.cleancityclient.api.Parametro
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class SolicitudModelo(): Serializable {
    private var id: Int? = 0
    private var fecha: String? = null
    private var descripcion: String? = null
    private var sector: String? = null
    private var tipo: String? = null
    private var direccion: String? = null
    private var rutUser: String? = null

    constructor(fecha: String?, descripcion: String?, sector: String?, tipo: String?, direccion: String?, rutUser: String?): this() {
        this.fecha = fecha
        this.descripcion = descripcion
        this.sector = sector
        this.tipo = tipo
        this.direccion = direccion
        this.rutUser = rutUser
    }

    @Throws(JSONException::class)
    constructor(fromJSON: JSONObject): this() {
        id = fromJSON.getInt("Id_Retiro")
        fecha = fromJSON.getString("Fecha_Retiro")
        descripcion = fromJSON.getString("Descripcion_Retiro")
        sector = fromJSON.getString("Sector_Retiro")
        tipo = fromJSON.getString("Tipo_Escombro")
        direccion = fromJSON.getString("Direccion_Retiro")
        rutUser = fromJSON.getString("Rut_User")
    }

    fun busqueda(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        try {
            retornar.add(Parametro("Id_Retiro", getId()))
        } catch (e: Exception) {
        }
        return retornar
    }

    fun parametros(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        retornar.add(Parametro("Fecha_Retiro", getFecha()))
        retornar.add(Parametro("Descripcion_Retiro", getDescripcion()))
        retornar.add(Parametro("Sector_Retiro", getSector()))
        retornar.add(Parametro("Tipo_Escombro", getTipo()))
        retornar.add(Parametro("Direccion_Retiro", getDireccion()))
        retornar.add(Parametro("Rut_User", getRutUser()))
        return retornar
    }

    override fun toString(): String {
        return "SolicitudModelo{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", sector='" + sector + '\'' +
                ", tipo='" + tipo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", rutUser='" + rutUser + '\'' +
                '}'
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getFecha(): String? {
        return fecha
    }

    fun setFecha(fecha: String?) {
        this.fecha = fecha
    }

    fun getDescripcion(): String? {
        return descripcion
    }

    fun setDescripcion(descripcion: String?) {
        this.descripcion = descripcion
    }

    fun getSector(): String? {
        return sector
    }

    fun setSector(sector: String?) {
        this.sector = sector
    }

    fun getTipo(): String? {
        return tipo
    }

    fun setTipo(tipo: String?) {
        this.tipo = tipo
    }

    fun getDireccion(): String? {
        return direccion
    }

    fun setDireccion(direccion: String?) {
        this.direccion = direccion
    }

    fun getRutUser(): String? {
        return rutUser
    }

    fun setRutUser(rutUser: String?) {
        this.rutUser = rutUser
    }
}