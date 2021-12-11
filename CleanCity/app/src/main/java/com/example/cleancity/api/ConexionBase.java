package com.example.cleancity.api;

import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConexionBase {
    private static OkHttpClient cliente = new OkHttpClient();

    public void solicitar(String url, List<Parametro> parametros, Callback callback){
        FormBody.Builder builder = new FormBody.Builder();

        for(Parametro parametro : parametros){
            builder.add(parametro.getNombre(), parametro.getValor());
        }

        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        cliente.newCall(request).enqueue(callback);
    }
}
