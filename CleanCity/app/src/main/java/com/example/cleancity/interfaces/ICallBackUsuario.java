package com.example.cleancity.interfaces;

import com.example.cleancity.modelos.UsuarioModelo;

import org.json.JSONObject;

import java.util.List;

public interface ICallBackUsuario {
    void retornar(List<UsuarioModelo> resultado);
    void retorno(UsuarioModelo resultado);
}
