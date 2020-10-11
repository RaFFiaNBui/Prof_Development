package ru.samarin.prodev.model.data

import com.google.gson.annotations.SerializedName

class SearchResult(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meaning") val meaning: List<Meanings>?
)