package com.hd.misaleawianegager.presentation.component.recent

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.local.FileService
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecentViewModel @Inject constructor(private val textRepository: TextRepository,
                                          @ApplicationContext private val context: Context ,
                     @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher) : ViewModel()  {

    private val _recentStateFlow = MutableStateFlow(emptyList<String>())
    val recentStateFlow get() = _recentStateFlow.asStateFlow()

init {
    readText(context)
}

    private fun readText(context: Context){
        viewModelScope.launch(coroutineDispatcher) {
            val list = mutableListOf<String>()
            textRepository.readTextFile(context, 1).collect{
                list.add(it.data!!)
            }
            _recentStateFlow.value = list
        }
    }

}