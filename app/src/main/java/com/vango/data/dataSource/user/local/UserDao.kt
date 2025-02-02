package com.vango.data.dataSource.user.local

import androidx.room.*
import com.vango.data.dataSource.user.local.dbo.UserDbo

@Dao
interface UserDao {

    // CREATE o UPDATE (Insertar usuario, reemplaza si ya existe)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbo)

    // READ (Obtener usuario)
    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserDbo?

    // READ (Obtener solo el username)
    @Query("SELECT username FROM user LIMIT 1")
    suspend fun getUserName(): String?

    // READ (Obtener solo el token)
    @Query("SELECT token FROM user LIMIT 1")
    suspend fun getUserToken(): String?

    // READ (Obtener solo el ID)
    @Query("SELECT id FROM user LIMIT 1")
    suspend fun getUserId(): Int?

    // UPDATE (Actualizar usuario manualmente sin sobrescribir toda la entidad)
    @Query("UPDATE user SET token = :token, username = :username WHERE id = :id")
    suspend fun updateUser(id: Int, token: String, username: String)

    // DELETE (Eliminar usuario específico)
    @Delete
    suspend fun deleteUser(user: UserDbo)

    // DELETE (Eliminar todos los usuarios)
    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()
}
