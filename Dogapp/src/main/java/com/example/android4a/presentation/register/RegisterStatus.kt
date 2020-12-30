package com.example.android4a.presentation.main

sealed class RegisterStatus

data class RegisterError(val email:String) : RegisterStatus()
object RegisterSuccess : RegisterStatus()