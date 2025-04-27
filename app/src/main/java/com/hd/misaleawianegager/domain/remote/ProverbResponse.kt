package com.hd.misaleawianegager.domain.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProverbResponse(
    @SerialName("en_meaning") val enMeaning: String,
    @SerialName("am_meaning") val amMeaning: String
)