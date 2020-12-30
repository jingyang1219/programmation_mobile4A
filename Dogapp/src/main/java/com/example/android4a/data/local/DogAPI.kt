package com.example.android4a.data.local

import com.example.android4a.domain.entity.RestDogResponse
import retrofit2.Call

import retrofit2.http.GET

interface DogAPI {
    @GET("DogApi.json")
    fun breedResponse(): Call<RestDogResponse>
}