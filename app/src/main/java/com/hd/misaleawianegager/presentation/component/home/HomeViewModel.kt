package com.hd.misaleawianegager.presentation.component.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TextRepository,
                                        @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
                   @ApplicationContext private val context: Context) : ViewModel() {

   private val _homeStateFlow = MutableStateFlow(emptyList<String>())
    val homeStateFlow get() = _homeStateFlow.asStateFlow()

    var working: Flow<String> =
        repository.enqueueWork().stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000L), "RUNNING")


    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.LoadLetter -> {
                homeDataFeed(context, event.value)
            }
            is HomeEvent.WriteText -> {
                writeText(context, 1 , event.text)
            }
        }
    }

  private fun homeDataFeed(context: Context, query: String ){

        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            repository.readTextAsset(context, query).collect{it ->
               list.add(it.data!!)
            }
            _homeStateFlow.value = list
        }
    }

    private fun writeText(context: Context, type: Int, text: String){
        viewModelScope.launch(coroutineDispatcher) {
            repository.writeTextFile(context, type, text)
        }
    }

}