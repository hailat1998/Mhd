package com.hd.misaleawianegager.presentation.component.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hd.misaleawianegager.R
import com.hd.misaleawianegager.domain.repository.SettingRepository
import com.hd.misaleawianegager.presentation.theme.Font
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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), R.font.abyssinica_gentium )

    val letterType = settingRepository.letterType
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), "ሀ" )

   private fun setFont(font: Int){
       viewModelScope.launch {
           settingRepository.setFont(font)
       }
   }


    private fun setLetterSpace(space: Double) {
        if (letterSpace.value + space in 0.1 .. 2.0) {

            viewModelScope.launch {
                settingRepository.setLetterSpace(space)
            }
        }
    }

    private fun setTheme(theme: String){
        viewModelScope.launch {
            settingRepository.setTheme(theme)
        }
    }

    private fun setFontSize(size: Int) {
        if (fontSize.value + size in 5..49) {
            viewModelScope.launch {
                settingRepository.setFontSize(size)
            }
        }
    }

    private fun setLetterHeight(height: Int) {
        if (letterHeight.value + height in 10..40) {
            viewModelScope.launch {
                settingRepository.setLineHeight(height)
            }
        }
    }

    private fun setLetterType(type: String){
        viewModelScope.launch {
            settingRepository.setLetterType(type)
        }
    }

}