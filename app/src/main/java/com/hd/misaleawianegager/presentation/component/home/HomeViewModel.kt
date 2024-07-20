package com.hd.misaleawianegager.presentation.component.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TextRepository,
                                        @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {

   val homeStateFlow =  mutableStateListOf<String>()


    fun homeDataFeed(context: Context, ){
        var count = 0
        viewModelScope.launch(coroutineDispatcher) {
            repository.readTextAsset(context, "01Ha.txt").collect{it ->
                homeStateFlow.add(it.data!!)
                count++
                Log.i("VIEWMODEL", "${count} it.data")
            }
        }
    }
}