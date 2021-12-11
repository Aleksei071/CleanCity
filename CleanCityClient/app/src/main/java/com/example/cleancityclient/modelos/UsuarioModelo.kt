package com.example.cleancityclient.modelos

import com.example.cleancityclient.api.Parametro
import org.json.JSONException
import org.json.JSONObject

class UsuarioModelo() {
    private var rut: String? = null
    private var nombre: String? = null
    private var apellido: String? = null
    private var sexo: String? = null
    private var correo: String? = null
    private var sector: String? = null
    private var direccion: String? = null
    private var telefono: String? = null
    private var pass: String? = null

    constructor(rut: String?, nombre: String?, apellido: String?, sexo: String?, correo: String?, sector: String?, direccion: String?, telefono: String?, pass: String?): this() {
        this.rut = rut
        this.nombre = nombre
        this.apellido = apellido
        this.sexo = sexo
        this.correo = correo
        this.sector = sector
        this.direccion = direccion
        this.telefono = telefono
        this.pass = pass
    }

    @Throws(JSONException::class)
    constructor(fromJSON: JSONObject) : this(){
        rut = fromJSON.getString("Rut_User")
        nombre = fromJSON.getString("Nombre_User")
        apellido = fromJSON.getString("Apellido_User")
        sexo = fromJSON.getString("Sexo_User")
        correo = fromJSON.getString("Correo_User")
        sector = fromJSON.getString("Sector_User")
        direccion = fromJSON.getString("Direccion_User")
        telefono = fromJSON.getString("Telefono_User")
        pass = fromJSON.getString("Pass_User")
    }

    fun busqueda(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        try {
            retornar.add(Parametro("Rut_User", getRut()))
        } catch (e: Exception) {
        }
        return retornar
    }

    fun parametros(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        try {
            retornar.add(Parametro("Rut_User", getRut()))
        } catch (e: Exception) {
        }
        retornar.add(Parametro("Nombre_User", getNombre()))
        retornar.add(Parametro("Apellido_User", getApellido()))
        retornar.add(Parametro("Sexo_User", getSexo()))
        retornar.add(Parametro("Correo_User", getCorreo()))
        retornar.add(Parametro("Sector_User", getSector()))
        retornar.add(Parametro("Direccion_User", getDireccion()))
        retornar.add(Parametro("Telefono_User", getTelefono()))
        retornar.add(Parametro("Pass_User", getPass()))
        return retornar
    }

    override fun toString(): String {
        return "UsuarioModelo{" +
                "rut='" + rut + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", sexo='" + sexo + '\'' +
                ", correo='" + correo + '\'' +
                ", sector='" + sector + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", pass='" + pass + '\'' +
                '}'
    }

    fun getRut(): String? {
        return rut
    }

    fun setRut(rut: String?) {
        this.rut = rut
    }

    fun getNombre(): String? {
        return nombre
    }

    fun setNombre(nombre: String?) {
        this.nombre = nombre
    }

    fun getApellido(): String? {
        return apellido
    }

    fun setApellido(apellido: String?) {
        this.apellido = apellido
    }

    fun getSexo(): String? {
        return sexo
    }

    fun setSexo(sexo: String?) {
        this.sexo = sexo
    }

    fun getCorreo(): String? {
        return correo
    }

    fun setCorreo(correo: String?) {
        this.correo = correo
    }

    fun getSector(): String? {
        return sector
    }

    fun setSector(sector: String?) {
        this.sector = sector
    }

    fun getDireccion(): String? {
        return direccion
    }

    fun setDireccion(direccion: String?) {
        this.direccion = direccion
    }

    fun getTelefono(): String? {
        return telefono
    }

    fun setTelefono(telefono: String?) {
        this.telefono = telefono
    }

    fun getPass(): String? {
        return pass
    }

    fun setPass(pass: String?) {
        this.pass = pass
    }
}