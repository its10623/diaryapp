package com.example.diaryapp.data.local.dataStore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.diaryapp.domain.model.User
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<List<User>> {

    override val defaultValue: List<User> = emptyList()

    override suspend fun readFrom(input: InputStream): List<User> {
        return try {
            val text = input.readBytes().decodeToString()
            Json.decodeFromString(
                ListSerializer(User.serializer()),
                text
            )
        } catch (e: Exception) {
            throw CorruptionException("데이터 스토어를 읽을 수 없습니다", e)
        }
    }

    override suspend fun writeTo(t: List<User>, output: OutputStream) {
        val text = Json.encodeToString(
            ListSerializer(User.serializer()),
            t
        )
        output.write(text.encodeToByteArray()
        )
    }
}