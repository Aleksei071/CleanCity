package com.example.cleancityclient.interfaces

import com.example.cleancityclient.modelos.RecorridoModelo

interface ICallBackRecorrido {
    fun retornar(resultado: List<RecorridoModelo>)
    fun retorno(resultado: RecorridoModelo)
}