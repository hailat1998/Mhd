package com.hd.misaleawianegager.presentation.component.recent

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecentViewModel @Inject constructor(private val textRepository: TextRepository,
                     @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle) : ViewModel()  {

    private val _recentStateFlow = MutableStateFlow(emptyList<String>())
    val recentStateFlow get() = _recentStateFlow.asStateFlow()

    companion object {
        private const val SCROLLINDEX = "scrollIndex"
    }

    val scrollValue = MutableStateFlow(savedStateHandle.get<Int>(SCROLLINDEX) ?:0)

     fun setScroll(value: Int) {
         scrollValue.update { value }
        savedStateHandle[SCROLLINDEX] = value
    }

     fun readText(context: Context) {
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            textRepository.readTextFile(context, 1).collect{
                list.add(it.data!!.trim())
            }
            _recentStateFlow.update { list.reversed() }
        }
    }
}