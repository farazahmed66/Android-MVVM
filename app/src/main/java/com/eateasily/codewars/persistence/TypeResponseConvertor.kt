package com.eateasily.codewars.persistence

import androidx.room.ProvidedTypeConverter
import com.squareup.moshi.Moshi
import javax.inject.Inject

@ProvidedTypeConverter
class TypeResponseConvertor @Inject constructor(
    private val moshi: Moshi
){
}