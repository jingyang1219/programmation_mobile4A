package com.example.android4a.domain.usecase

import com.example.android4a.data.repository.UserRepository
import com.example.android4a.domain.entity.User

class CreateAccountUseCase (
    private val userRepository : UserRepository

){
    suspend fun invoke(user: User) {
        userRepository.createAccount(user)
    }
}