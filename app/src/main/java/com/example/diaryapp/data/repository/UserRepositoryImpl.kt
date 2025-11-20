package com.example.diaryapp.data.repository

import com.example.diaryapp.data.local.dataStore.UserLocalDataSource
import com.example.diaryapp.domain.model.User
import com.example.diaryapp.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import org.mindrot.jbcrypt.BCrypt

class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource
) : UserRepository {

    override suspend fun findUser(userName: String): User? =
        local.findUser(userName)

    override fun getAllUsers(): Flow<List<User>> =
        local.getAllUsers()

    override suspend fun register(userName: String, password: String): Boolean {
        val hash = BCrypt.hashpw(password, BCrypt.gensalt())
        local.register(User(userName = userName, password =  hash))
        return true
    }

    override suspend fun login(userName: String, password: String): Boolean {
        val user = local.findUser(userName) ?: return false
        return BCrypt.checkpw(password, user.password)
    }

    override fun getAutoLogin(): Flow<Boolean> =
        local.getAutoLogin()

    override suspend fun setAutoLogin(value: Boolean) =
        local.saveAutoLogin(value)

    override suspend fun updatePassword(userName: String, newPassword: String): Boolean {
        val user = findUser(userName) ?: return false
        val newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt())
        val updateUser = user.copy(password = newHash)

        local.updateUser(updateUser)
        return true
    }
}