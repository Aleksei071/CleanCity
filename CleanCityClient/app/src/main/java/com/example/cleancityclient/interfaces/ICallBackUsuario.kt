package com.example.cleancityclient.interfaces

import com.example.cleancityclient.modelos.UsuarioModelo

interface ICallBackUsuario {
    fun retornar(resultado: List<UsuarioModelo>)
    fun retorno(resultado: UsuarioModelo)
}