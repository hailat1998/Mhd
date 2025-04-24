package com.hd.misaleawianegager.domain.remote

import kotlinx.serialization.Serializable

@Serializable
data class ProverbResponse(val enMeaning: String, val amMeaning: String)
