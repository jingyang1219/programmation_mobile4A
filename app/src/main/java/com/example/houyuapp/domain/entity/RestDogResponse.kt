package com.example.houyuapp.domain.entity

class RestDogResponse {
    private val messages: MutableList<Dog?>? = null
    private val status: String? = null

    fun getInformation(): MutableList<Dog?>? {
        return messages
    }

    fun getStatus(): String? {
        return status
    }

}