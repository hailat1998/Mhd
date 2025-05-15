package com.hd.misaleawianegager.presentation.component.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val textRepository: TextRepository ,
                            @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
                            @ApplicationContext private val context: Context,
                            ) : ViewModel() {

   private var _searchResult = MutableStateFlow(emptyList<String>())
    val searchResult get() = _searchResult.asStateFlow()

    private var _wordResult = MutableStateFlow(SearchUiState())
    val wordResult = _wordResult.asStateFlow()

    fun onEvent(searchEvent: SearchEvent){
        when(searchEvent) {
            is SearchEvent.SearchProverb -> {
                search(query = searchEvent.query)
            }
            is SearchEvent.ConvertWord -> {
                convert(word = searchEvent.word)
            }
            is SearchEvent.LoadSingle -> {
                readSingle()
            }
        }
    }

    private fun readSingle() {
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            textRepository.readSingle().collect{
                list.add(it.data!!)
            }
            _searchResult.update { list }
        }
    }

    private fun search(query: String){
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
           textRepository.search( context, query).collect{
               list.add(it.data!!)
            }
            _searchResult.update { list }
        }
    }

    private fun convert(word: String) {
        viewModelScope.launch(coroutineDispatcher) {
            textRepository.laOren2am(word).collect { result ->
                _wordResult.update { currentState ->
                    when (result) {
                        is Resources.Loading -> currentState.copy(isLoading = result.isLoading)
                        is Resources.Error -> currentState.copy(
                            isLoading = false,
                            error = result.message ?: "Unknown error",
                            word = result.data ?: currentState.word
                        )
                        is Resources.Success -> currentState.copy(
                            isLoading = false,
                            word = result.data ?: currentState.word,
                            error = null
                        )
                    }
                }
            }
        }
    }
}
