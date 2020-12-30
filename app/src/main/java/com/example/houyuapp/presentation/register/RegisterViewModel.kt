package com.example.houyuapp.presentation.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.houyuapp.domain.entity.User
import com.example.houyuapp.domain.usecase.ConfirmRegistrationUseCase
import com.example.houyuapp.domain.usecase.CreateAccountUseCase
import com.example.houyuapp.presentation.main.RegisterError
import com.example.houyuapp.presentation.main.RegisterStatus
import com.example.houyuapp.presentation.main.RegisterSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(
    private val createAccountUseCase: CreateAccountUseCase,
    private val confirmRegistrationUseCase: ConfirmRegistrationUseCase
):ViewModel() {
    val registerLiveData: MutableLiveData<RegisterStatus> = MutableLiveData()
    fun onClickedRegister(newUser: User){

        viewModelScope.launch(Dispatchers.IO) {
            val newEmail = confirmRegistrationUseCase.invoke(newUser.email.toString().trim())
            val registerStatus = if (newEmail != null){
                RegisterError(newEmail.email)
            }else{
                RegisterSuccess
            }
            withContext(Dispatchers.Main){
                registerLiveData.value = registerStatus
            }
            createAccountUseCase.invoke(newUser)
        }
    }

//    fun insertUser(newUser:User){
//        viewModelScope.launch(Dispatchers.IO) {
//            createAccountUseCase.invoke(newUser)
//        }
//    }
}