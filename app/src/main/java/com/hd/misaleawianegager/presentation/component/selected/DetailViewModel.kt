package com.hd.misaleawianegager.presentation.component.selected

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.AITextRepository
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.utils.Resources
import com.hd.misaleawianegager.utils.compose.favList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val textRepository: TextRepository,
                                          @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
                                          @ApplicationContext private val context: Context,
                                private val aiTextRepository: AITextRepository
) : ViewModel() {

    private val _detailStateFlow = MutableStateFlow(emptyList<String>())
    val detailStateFlow get() = _detailStateFlow.asStateFlow()

    private val _detailsAITextStateFlow = MutableStateFlow(DetailUiState())
    val detailsAITextStateFlow = _detailsAITextStateFlow.asStateFlow()

    fun onEvent(e: DetailEvent){
        when(e){
            is DetailEvent.LoadLetter -> {
                detailFeedQuery(context, e.q)
              }
            is DetailEvent.LoadFav -> {
                detailFeedFav()
              }
            is DetailEvent.LoadRecent -> {
                detailFeedRecent(context)
            }
            is DetailEvent.LoadAIContent -> {
                detailAIFeed(e.proverb)
            }
            is DetailEvent.LoadSingle -> {
                readSingle()
            }
        }
    }

    private fun detailFeedQuery(context: Context, query: String) {
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            DataProvider.letterMap[query]?.let { assetName ->
                textRepository.readTextAsset(context, assetName).collect { resource ->
                    resource.data?.let { data ->
                        list.add(data.trim())
                    }
                }
                _detailStateFlow.update { list }
            } ?: run {
                _detailStateFlow.update { emptyList() }
            }
        }
    }

    private fun detailFeedRecent(context: Context) {
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            textRepository.readTextFile(context, 1).collect {
                list.add(it.data!!)
            }
            _detailStateFlow.update { list.reversed() }
        }
    }

    private fun detailFeedFav(){
        val list = mutableListOf<String>()
        list.addAll(favList)
        _detailStateFlow.update { list }
    }

    private fun detailAIFeed(proverb: String) {

        viewModelScope.launch(coroutineDispatcher) {
            aiTextRepository.getFromNetwork(proverb)
                .collect { resource ->
                    _detailsAITextStateFlow.update { currentState ->
                        val newState = when (resource) {
                            is Resources.Loading -> {
                                // Use the loading value from the resource
                                currentState.copy(isLoading = resource.isLoading)
                            }
                            is Resources.Success -> {
                                currentState.copy(
                                    isLoading = false,
                                    enMeaning = resource.data?.enMeaning,
                                    amMeaning = resource.data?.amMeaning,
                                    error = null
                                )
                            }
                            is Resources.Error -> {
                                currentState.copy(
                                    isLoading = false,
                                    error = resource.message
                                )
                            }
                        }

                      newState
                    }
                }
          }
    }

    private fun readSingle() {
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            textRepository.readSingle().collect{
                list.add(it.data!!)
            }
            _detailStateFlow.update { list }
        }
    }
}