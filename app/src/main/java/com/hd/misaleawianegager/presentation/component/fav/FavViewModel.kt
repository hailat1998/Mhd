package com.hd.misaleawianegager.presentation.component.fav

import android.content.Context
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
                   @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher): ViewModel() {


                   private val _favStateFlow = MutableStateFlow(emptyList<String>())
                  val favStateFlow get() = _favStateFlow.asStateFlow()




init {
    readFavList(favList)
}

                fun onEvent(event: FavEvent){
                    when(event) {
                        is FavEvent.ReadFav -> {
                            readFavList(favList)
                             }
                        is FavEvent.WriteFile -> {
                         //   writeFavList(favList)
                        }
                        else -> {}
                    }
                }




           private  fun readFavList(favList: MutableList<String>){
           viewModelScope.launch(coroutineDispatcher) {
               val list2 = mutableListOf<String>()
               textRepository.readTextFile(context , 2).collect{
                   list2.add(it.data!!)
               }
               favList.addAll(list2)
               _favStateFlow.value = favList
           }
           }

}