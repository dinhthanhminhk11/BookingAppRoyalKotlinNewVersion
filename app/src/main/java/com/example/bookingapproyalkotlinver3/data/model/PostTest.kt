package com.example.bookingapproyalkotlinver3.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "post"
)
class PostTest(
    @PrimaryKey(autoGenerate = true) val idLocal: Int? = null, val idUser: String
)