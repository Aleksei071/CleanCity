package com.example.cleancityclient.interfaces

import com.example.cleancityclient.modelos.CamionModelo

interface ICallBackCamion {
    fun retornar(resultado: List<CamionModelo>)
    fun retorno(resultado: CamionModelo)
}