package com.hd.misaleawianegager.presentation.component.search

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val textRepository: TextRepository ,
                      @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
                      @ApplicationContext private val context: Context) : ViewModel() {

   private var _searchResult = MutableStateFlow(emptyList<String>())
    val searchResult get() = _searchResult.asStateFlow()

    fun search(query: String){
        Log.i("SEARCHVIEWMODEL", "searching")
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
           textRepository.search( context, query).collect{
               list.add(it.data!!)
            }
            _searchResult.value = list
        }
    }
}