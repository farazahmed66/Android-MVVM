package com.eateasily.codewars.persistence

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.eateasily.codewars.models.Ranks
import com.eateasily.codewars.models.User
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class TypeResponseConvertor @Inject constructor(
    private val moshi: Moshi
){

    @TypeConverter
    fun stringToList(string: String): List<String> {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val adapter = moshi.adapter<List<String>>(type)
        return adapter.toJson(list)
    }

}