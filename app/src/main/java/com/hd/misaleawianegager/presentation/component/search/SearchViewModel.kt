package com.hd.misaleawianegager.presentation.component.search

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val textRepository: TextRepository ,
                      @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
                      @ApplicationContext private val context: Context) : ViewModel() {

    var searchResult by mutableStateOf(listOf<String>())
        private set

    fun search(query: String){
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
           textRepository.search( context, query).collect{
               list.add(it.data!!)
            }
            searchResult = list
        }
    }
}