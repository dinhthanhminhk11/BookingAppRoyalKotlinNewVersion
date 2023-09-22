package com.example.bookingapproyalkotlinver3.data.db

import androidx.room.TypeConverter
import com.example.bookingapproyalkotlinver3.data.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}