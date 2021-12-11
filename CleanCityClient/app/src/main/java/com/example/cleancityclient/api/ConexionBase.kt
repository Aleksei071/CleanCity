package com.example.cleancityclient.api

import okhttp3.*

open class ConexionBase {
    private val cliente = OkHttpClient()
    fun solicitar(url: String?, parametros: List<Parametro>?, callback: Callback?) {
        val builder = FormBody.Builder()
        for (parametro in parametros!!) {
            builder.add(parametro.getNombre()!!, parametro.getValor()!!)
        }
        val body: RequestBody = builder.build()
        val request: Request = Request.Builder()
            .url(url.toString())
            .post(body)
            .build()
        cliente.newCall(request).enqueue(callback!!)
    }
}