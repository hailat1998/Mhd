package com.hd.misaleawianegager.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val textRepository: TextRepository,
                                @ApplicationContext private val context: Context,
                               @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {


    val working = textRepository.enqueueWork().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), "RUNNING")

     fun writeFavList(favList: MutableList<String>){
        viewModelScope.launch(coroutineDispatcher) {
            val j = favList.toHashSet()
           val text = j.joinToString("\n")
                textRepository.writeTextFile(context , 2, text)
        }
    }


  fun readFavList(favList: MutableList<String>){
        viewModelScope.launch(coroutineDispatcher) {
            val list2 = mutableListOf<String>()
            textRepository.readTextFile(context , 2).collect{
                list2.add(it.data!!)
            }
            favList.addAll(list2)
        }
    }
}