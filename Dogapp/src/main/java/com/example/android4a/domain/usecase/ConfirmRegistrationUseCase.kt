package com.example.android4a.domain.usecase

import com.example.android4a.data.repository.UserRepository
import com.example.android4a.domain.entity.User

class ConfirmRegistrationUseCase (
    private val userRepository : UserRepository
    ) {
        suspend fun invoke(newEmail: String): User? {
            return userRepository.confirmRegistration(newEmail)
    }
}