package com.example.houyuapp.domain.usecase

import com.example.houyuapp.data.repository.UserRepository
import com.example.houyuapp.domain.entity.User

class ConfirmRegistrationUseCase (
    private val userRepository : UserRepository
    ) {
        suspend fun invoke(newEmail: String): User? {
            return userRepository.confirmRegistration(newEmail)
    }
}