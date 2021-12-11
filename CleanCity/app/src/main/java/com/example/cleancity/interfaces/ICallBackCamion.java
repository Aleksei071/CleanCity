package com.example.cleancity.interfaces;


import com.example.cleancity.modelos.CamionModelo;
import java.util.List;

public interface ICallBackCamion {
    void retornar(List<CamionModelo> resultado);
    void retorno(CamionModelo resultado);
}
