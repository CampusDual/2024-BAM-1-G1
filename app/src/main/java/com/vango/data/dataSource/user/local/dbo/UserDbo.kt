package com.vango.data.dataSource.user.local.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbo(
    @PrimaryKey val id: Int,
    val token: String,

    @ColumnInfo(name = "username")
    val name: String,
)
