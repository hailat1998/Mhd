package com.hd.misaleawianegager.domain.local

import android.content.Context

interface WorkerTextService {
    fun readSingleText(context: Context) : String
}