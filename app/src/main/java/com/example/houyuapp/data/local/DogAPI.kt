package com.example.houyuapp.data.local

import com.example.houyuapp.domain.entity.RestDogResponse
import retrofit2.Call

import retrofit2.http.GET

interface DogAPI {
    @GET("houyuApi.json")
    fun breedResponse(): Call<RestDogResponse>
}