package com.example.cleancityclient.api

class Parametro() {
    private var nombre: String? = null
    private var valor: String? = null

    constructor (nombre: String?, valor: Any?) : this() {
        this.nombre = nombre
        this.valor = valor.toString()
    }

    fun getNombre(): String? {
        return nombre
    }

    fun getValor(): String? {
        return valor
    }
}

