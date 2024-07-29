package saha.subham.bookshelf.Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import saha.subham.bookshelf.Model.Villain

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromVillainString(value: String): List<Villain> {
        val listType = object : TypeToken<List<Villain>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromVillainList(list: List<Villain>): String {
        return Gson().toJson(list)
    }
}