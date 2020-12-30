package com.example.houyuapp.data.repository

import com.example.houyuapp.data.local.DatabaseDao
import com.example.houyuapp.data.local.models.UserLocal
import com.example.houyuapp.data.local.models.toData
import com.example.houyuapp.data.local.models.toEntity
import com.example.houyuapp.domain.entity.User

class UserRepository(
    private val databaseDao: DatabaseDao
) {
    fun createAccount(user: User){
        databaseDao.insert(user.toData())
    }

    fun deleteAccount(user: User){
        databaseDao.delete(user.toData())
    }

    fun getAccount(email: String, password: String): User?{
        val emailLocal = databaseDao.findByName(email, password)
        return emailLocal?.toEntity()
    }

    fun confirmRegistration(newEmail: String): User?{
        val newEmailLocal = databaseDao.findByEmail(newEmail)
        return newEmailLocal?.toEntity()
    }

}