package com.example.houyuapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houyuapp.domain.entity.User
import com.example.houyuapp.domain.usecase.ConfirmRegistrationUseCase
import com.example.houyuapp.domain.usecase.CreateAccountUseCase
import com.example.houyuapp.domain.usecase.GetAccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (
    private val getAccountUseCase: GetAccountUseCase

) : ViewModel()
{
    val loginLiveData: MutableLiveData<LoginStatus> = MutableLiveData()


    fun onClickedLogin(emailUser: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val user = getAccountUseCase.invoke(emailUser, password)

            val loginStatus =  if (user != null){
                LoginSuccess(user.email, user.password)
            }else{
                LoginError
            }
            withContext(Dispatchers.Main){
                loginLiveData.value = loginStatus
            }

        }
    }


}