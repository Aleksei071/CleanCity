package com.example.cleancity.interfaces;

import com.example.cleancity.modelos.RecorridoModelo;

import java.util.List;

public interface ICallBackRecorrido {
        void retornar(List<RecorridoModelo> resultado);
        void retorno(RecorridoModelo resultado);
}
