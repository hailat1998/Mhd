package com.hd.misaleawianegager.presentation.component.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.domain.repository.SettingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val settingRepository: SettingRepository) : ViewModel() {


    fun onEvent(event: SettingEvent){
        when(event){
            is SettingEvent.FontSize ->  setFontSize(event.value)
            is SettingEvent.Theme -> setTheme(event.value)
            is SettingEvent.FontFamily -> setFont(event.value)
            is SettingEvent.LetterSpace -> setLetterSpace(event.value)
            is SettingEvent.LineHeight -> setLetterHeight(event.value)
            is SettingEvent.LetterType -> setLetterType(event.value)
        }
    }

    val letterSpace = settingRepository.letterSpace
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), 0.5 )

    val fontSize = settingRepository.fontSize
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), 16)

    val theme = settingRepository.theme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), "dark" )

    val letterHeight = settingRepository.letterHeight
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), 26 )

    val font = settingRepository.font
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), "default" )

    val letterType = settingRepository.letterType
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), "01Ha.txt" )

   private fun setFont(font: String){
       viewModelScope.launch {
           settingRepository.setFont(font)
       }
   }


    private fun setLetterSpace(space: Double) {
        if (letterSpace.value + space in 0.1 .. 7.0) {
            viewModelScope.launch {
                settingRepository.setLetterSpace(letterSpace.value + space)
            }
        }
    }

    private fun setTheme(theme: String){

        viewModelScope.launch {
            settingRepository.setTheme(theme)
        }
    }

    private fun setFontSize(size: Int) {

        if (fontSize.value + size in 5..30) {
            viewModelScope.launch {
                settingRepository.setFontSize(fontSize.value + size)
                settingRepository.setLineHeight(letterHeight.value + 3)
            }
        }
    }

    private fun setLetterHeight(height: Int) {

        if (letterHeight.value + height in 10..40) {
            viewModelScope.launch {
                settingRepository.setLineHeight(letterHeight.value + height)
            }
        }
    }

    private fun setLetterType(type: String){
        viewModelScope.launch {
            settingRepository.setLetterType(type)
        }
    }
}