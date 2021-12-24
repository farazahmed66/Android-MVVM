package com.eateasily.codewars.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Test(
    @PrimaryKey
    val test: String)
