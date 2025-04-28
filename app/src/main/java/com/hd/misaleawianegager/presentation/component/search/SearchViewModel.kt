package com.hd.misaleawianegager.presentation.component.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.MisaleSpellChecker
import com.hd.misaleawianegager.utils.Resources
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
                            @ApplicationContext private val context: Context,
                            private val misaleSpellChecker: MisaleSpellChecker) : ViewModel() {

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
            _searchResult.value = list
        }
    }

    private fun search(query: String){
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
           textRepository.search( context, query).collect{
               list.add(it.data!!)
            }
            _searchResult.value = list
        }
    }

    override fun onCleared() {
        super.onCleared()
        misaleSpellChecker.closeSession()
    }
    private fun convert(word: String) {
        viewModelScope.launch(coroutineDispatcher) {

            val isValid = misaleSpellChecker.checkWord(word)

            val flow = if (isValid) {
                textRepository.en2am(word)
            } else {
                textRepository.la2am(word)
            }


            flow.collect { result ->
                when (result) {
                    is Resources.Loading -> {
                        _wordResult.value = SearchUiState(isLoading = true)
                    }

                    is Resources.Error -> {
                        _wordResult.value = SearchUiState(
                            isLoading = false,
                            error = result.message ?: "Unknown error"
                        )
                    }

                    is Resources.Success -> {
                        _wordResult.value = SearchUiState(
                            isLoading = false,
                            word = result.data
                        )
                    }
                }
            }
        }
    }
}