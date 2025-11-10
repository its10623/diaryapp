package com.example.diaryapp.data.repository

import com.example.diaryapp.domain.model.User
import com.example.diaryapp.domain.repository.UserRepository
import com.example.diaryapp.presentation.FakeContext
import org.mindrot.jbcrypt.BCrypt
import java.io.File

class UserRepositoryImpl(private val context: FakeContext) : UserRepository {

    private val userDir = File(context.filesDir, "users")

    init {
        if (!userDir.exists()) userDir.mkdirs()
    }

    override fun loadUser(name: String): User? {
        val file = File(userDir, "${name}.txt")

        if (!file.exists()) return null

        val password = file.readText()
        return User(name = name, password = password)
    }

    override fun saveUser(user: User) {
        val file = File(userDir, "${user.name}.txt")
        val hashPassword = encryptPassword(user.password)
        file.writeText(hashPassword)
    }

    override fun deleteUser(name: String): Boolean {
        val file = File(userDir, "${name}.txt")
        return file.delete()
    }

    override fun isUserExists(name: String): Boolean {
        val file = File(userDir, "${name}.txt")
        return file.exists()
    }

    private fun encryptPassword(rawPassword: String): String {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt())
    }
}