package ru.tumist.surfgallery.data.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import java.util.*


class EpochDateJsonAdapter {
    @ToJson fun toJson(value: Date): String {
        return value.toString()
    }

    @FromJson fun fromJson(value: String): Date {
        return Date(value.toLong())
    }
}