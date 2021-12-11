package com.example.cleancityclient.interfaces

import com.example.cleancityclient.modelos.SolicitudModelo

interface ICallBackSolicitud {
    fun retornar(resultado: List<SolicitudModelo>)
    fun retorno(resultado: SolicitudModelo)
}