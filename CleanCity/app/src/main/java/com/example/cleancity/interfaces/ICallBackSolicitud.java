package com.example.cleancity.interfaces;

import com.example.cleancity.modelos.SolicitudModelo;

import java.util.List;

public interface ICallBackSolicitud {
    void retornar(List<SolicitudModelo> resultado);
    void retorno(SolicitudModelo resultado);
}
