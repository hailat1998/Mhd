package com.hd.misaleawianegager.presentation.component.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: TextRepository, @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher) : ViewModel() {

    private var _homeStateFlow = MutableStateFlow<HomeState>(HomeState(loading = false))
    val homeStateFlow get() = _homeStateFlow.asStateFlow()


    fun homeDataFeed(context: Context, ){
        viewModelScope.launch {
            repository.readTextAsset(context, "01Ha.txt", coroutineDispatcher).collect{it ->

                if(it.data == null){
                    Log.i("From view", "if")
                    if(_homeStateFlow.value.list.isEmpty()){
                        _homeStateFlow.value = HomeState(loading = true)
                    }
                }else{
                    Log.i("From view", "else")
                    val homeState = _homeStateFlow.value
                    homeState.list.add(it.data)
                    homeState.loading = false
                    _homeStateFlow.value = homeState
                    Log.i("From view", "else ${homeState.list.size}")
                }
            }
        }
    }
}