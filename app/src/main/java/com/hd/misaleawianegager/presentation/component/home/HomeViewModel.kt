package com.hd.misaleawianegager.presentation.component.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TextRepository,
                                        @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {

   val homeStateFlow by  mutableStateOf(listOf<String>().toMutableStateList())


    fun homeDataFeed(context: Context, ){
        var count = 0
        viewModelScope.launch {
            repository.readTextAsset(context, "23Ye.txt", coroutineDispatcher).collect{it ->
                homeStateFlow.add(it.data!!)
                count++
                Log.i("VIEWMODEL", "${count} it.data")
            }
        }
    }
}