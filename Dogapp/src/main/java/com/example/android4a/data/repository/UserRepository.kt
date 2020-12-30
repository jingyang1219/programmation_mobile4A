package com.example.android4a.data.repository

import com.example.android4a.data.local.DatabaseDao
import com.example.android4a.domain.entity.User

import com.example.android4a.data.local.models.UserLocal
import com.example.android4a.data.local.models.toData
import com.example.android4a.data.local.models.toEntity


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