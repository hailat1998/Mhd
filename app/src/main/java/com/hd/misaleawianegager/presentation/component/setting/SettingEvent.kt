package com.hd.misaleawianegager.presentation.component.setting

sealed class SettingEvent {
    data class LetterSpace(val value: Double) : SettingEvent()
    data class Theme(val value: String ): SettingEvent()
    data class FontSize(val value: Int): SettingEvent()
    data class FontFamily(val value: String): SettingEvent()
    data class LineHeight(val value: Int): SettingEvent()
    data class LetterType(val value: String): SettingEvent()
    data class SetBoarding(val isShown: Boolean): SettingEvent()
}