package com.hd.misaleawianegager.presentation.component.fav

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.hd.misaleawianegager.utils.compose.favList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor( private val savedStateHandle: SavedStateHandle): ViewModel() {


                   private val _favStateFlow = MutableStateFlow(emptyList<String>())
                  val favStateFlow get() = _favStateFlow.asStateFlow()





    companion object {
        private const val SCROLLINDEX = "scrollIndex"
    }

    val scrollValue = MutableStateFlow(savedStateHandle.get<Int>(SCROLLINDEX) ?:0)

     fun setScroll(value: Int){

        scrollValue.value = value
        savedStateHandle[SCROLLINDEX] = value
    }



             fun readFavList(){
               _favStateFlow.value = favList
              }
           }
