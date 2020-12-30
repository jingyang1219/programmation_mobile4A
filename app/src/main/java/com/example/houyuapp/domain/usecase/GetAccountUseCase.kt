package com.example.houyuapp.domain.usecase

import com.example.houyuapp.data.repository.UserRepository
import com.example.houyuapp.domain.entity.User

class GetAccountUseCase (
    private val userRepository : UserRepository
    ){
        suspend fun invoke(email: String, password: String): User? {
            return userRepository.getAccount(email, password)
        }
}