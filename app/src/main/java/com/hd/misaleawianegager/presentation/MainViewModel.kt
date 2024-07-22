package com.hd.misaleawianegager.presentation

import android.content.Context
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
class MainViewModel @Inject constructor(private val textRepository: TextRepository,
                                @ApplicationContext private val context: Context,
                               @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
): ViewModel() {

     fun writeFavList(favList: MutableList<String>){
        viewModelScope.launch(coroutineDispatcher) {
            for(i in favList){
                textRepository.writeTextFile(context , 2, i)
            }
        }
    }
}