package com.example.cleancityclient.modelos

import com.example.cleancityclient.api.Parametro
import org.json.JSONException
import org.json.JSONObject

class RecorridoModelo() {
    private var id = 0
    private var sector: String? = null
    private var horario: String? = null
    private var calendario = 0
    private var ruta: String? = null
    private var retiro: String? = null
    private var matricula: String? = null

    constructor(id: Int, sector: String?, horario: String?, calendario: Int, ruta: String?, retiro: String?, matricula: String?): this() {
        this.id = id
        this.sector = sector
        this.horario = horario
        this.calendario = calendario
        this.ruta = ruta
        this.retiro = retiro
        this.matricula = matricula
    }

    constructor(sector: String?, horario: String?, calendario: Int, ruta: String?, retiro: String?, matricula: String? ): this() {
        this.sector = sector
        this.horario = horario
        this.calendario = calendario
        this.ruta = ruta
        this.retiro = retiro
        this.matricula = matricula
    }

    @Throws(JSONException::class)
    constructor(fromJSON: JSONObject): this() {
        id = fromJSON.getInt("Id_Recorrido")
        sector = fromJSON.getString("Sector_Recorrido")
        horario = fromJSON.getString("Horario_Recorrido")
        calendario = fromJSON.getInt("Calendario_Recorrido")
        ruta = fromJSON.getString("Ruta_Cachureo")
        retiro = fromJSON.getString("Retiro_Escombro")
        matricula = fromJSON.getString("Matricula_Camion")
    }

    fun busqueda(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        try {
            retornar.add(Parametro("Id_Recorrido", getId()))
        } catch (e: Exception) {
        }
        return retornar
    }

    fun parametros(): List<Parametro>? {
        val retornar: MutableList<Parametro> = ArrayDeque<Parametro>()
        retornar.add(Parametro("Id_Recorrido", getId()))
        retornar.add(Parametro("Sector_Recorrido", getSector()))
        retornar.add(Parametro("Horario_Recorrido", getHorario()))
        retornar.add(Parametro("Calendario_Recorrido", getCalendario()))
        retornar.add(Parametro("Ruta_Cachureo", getRuta()))
        retornar.add(Parametro("Retiro_Escombro", getRetiro()))
        retornar.add(Parametro("Matricula_Camion", getMatricula()))
        return retornar
    }

    override fun toString(): String {
        return "RecorridoModelo{" +
                "id=" + id +
                ", sector='" + sector + '\'' +
                ", horario='" + horario + '\'' +
                ", calendario=" + calendario +
                ", ruta='" + ruta + '\'' +
                ", retiro='" + retiro + '\'' +
                ", matricula='" + matricula + '\'' +
                '}'
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getSector(): String? {
        return sector
    }

    fun setSector(sector: String?) {
        this.sector = sector
    }

    fun getHorario(): String? {
        return horario
    }

    fun setHorario(horario: String?) {
        this.horario = horario
    }

    fun getCalendario(): Int {
        return calendario
    }

    fun setCalendario(calendario: Int) {
        this.calendario = calendario
    }

    fun getRuta(): String? {
        return ruta
    }

    fun setRuta(ruta: String?) {
        this.ruta = ruta
    }

    fun getRetiro(): String? {
        return retiro
    }

    fun setRetiro(retiro: String?) {
        this.retiro = retiro
    }

    fun getMatricula(): String? {
        return matricula
    }

    fun setMatricula(matricula: String?) {
        this.matricula = matricula
    }
}