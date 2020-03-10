package com.example.torito.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ToritoApi {
    @FormUrlEncoded
    @POST("auth/login")

    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    companion object {
        operator fun invoke(): ToritoApi {
            return Retrofit.Builder()
                .baseUrl("http://serverIpAddress/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ToritoApi::class.java)
        }
    }
}