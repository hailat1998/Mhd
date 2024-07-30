package com.hd.misaleawianegager.presentation.component.selected

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.presentation.DataProvider
import com.hd.misaleawianegager.utils.compose.favList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val textRepository: TextRepository,
                                          @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
                                          @ApplicationContext private val context: Context
) : ViewModel() {

    private val _detailStateFlow = MutableStateFlow(emptyList<String>())
    val detailStateFlow get() = _detailStateFlow.asStateFlow()


    fun onEvent(e: DetailEvent){
        when(e){
            is DetailEvent.LoadLetter ->{
                detailFeedQuery(context, e.q)
              }
            is DetailEvent.LoadFav ->{
                detailFeedFav()
              }
            is DetailEvent.LoadRecent ->{
                detailFeedRecent(context)
            }
        }
    }


    private fun detailFeedQuery(context: Context, query: String){
        viewModelScope.launch {
            viewModelScope.launch(coroutineDispatcher) {
                val list = mutableListOf<String>()
                val text = DataProvider.letterMap[query]
                textRepository.readTextAsset(context, text!!).collect{ it ->
                     list.add(it.data!!.trim())
                }
                   _detailStateFlow.value = list
            }
        }
    }

    private fun detailFeedRecent(context: Context) {
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            textRepository.readTextFile(context, 1).collect {
                list.add(it.data!!)
            }
            _detailStateFlow.value = list
        }
    }

    private fun detailFeedFav(){
        _detailStateFlow.value = favList
        }

}