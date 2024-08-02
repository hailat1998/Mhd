package com.hd.misaleawianegager.presentation.component.fav

import android.content.Context
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.di.IoDispatcher
import com.hd.misaleawianegager.domain.repository.TextRepository
import com.hd.misaleawianegager.utils.compose.favList
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor(private val textRepository: TextRepository ,
                   @ApplicationContext private val context: Context,
                   @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val savedStateHandle: SavedStateHandle): ViewModel() {


                   private val _favStateFlow = MutableStateFlow(emptyList<String>())
                  val favStateFlow get() = _favStateFlow.asStateFlow()




          init{
              readFavList()
          }

    companion object {
        private const val SCROLLINDEX = "scrollIndex"
    }

    val scrollValue = MutableStateFlow(savedStateHandle.get<Int>(SCROLLINDEX) ?:0)

     fun setScroll(value: Int){

        Log.i("HOMEVIEWMODEL", "$value")

        scrollValue.value = value
        savedStateHandle[SCROLLINDEX] = value
    }



           private  fun readFavList(){
               _favStateFlow.value = favList
              }
           }
