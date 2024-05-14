package com.example.realestatemanagersamuelrogeron.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun remTextFieldColors(
): TextFieldColors {
    val isLightTheme = !isSystemInDarkTheme()
    val focusedTextColor = if (isLightTheme) Color(0xFF321900) else Color(0xFFFFDBCC)
    val unfocusedTextColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledTextColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorTextColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedContainerColor = if (isLightTheme) Color(0xFFFFDDBB) else Color(0xFF7B2F00)
    val unfocusedContainerColor = if (isLightTheme) Color(0xFFEAD9D0) else Color(0xFF5D4034)
    val disabledContainerColor = if (isLightTheme) Color(0xFFF4DED5) else Color(0xFF52443D)
    val errorContainerColor = if (isLightTheme) Color(0xFFFFDAD6) else Color(0xFF93000A)
    val cursorColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val errorCursorColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedIndicatorColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedIndicatorColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val disabledIndicatorColor = if (isLightTheme) Color(0xFFD8C2BA) else Color(0xFF52443D)
    val errorIndicatorColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedLeadingIconColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedLeadingIconColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledLeadingIconColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorLeadingIconColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedTrailingIconColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedTrailingIconColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledTrailingIconColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorTrailingIconColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedLabelColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedLabelColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledLabelColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorLabelColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedPlaceholderColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedPlaceholderColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledPlaceholderColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorPlaceholderColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedSupportingTextColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedSupportingTextColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledSupportingTextColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorSupportingTextColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedPrefixColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedPrefixColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledPrefixColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorPrefixColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)
    val focusedSuffixColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184)
    val unfocusedSuffixColor = if (isLightTheme) Color(0xFF52443D) else Color(0xFFD8C2BA)
    val disabledSuffixColor = if (isLightTheme) Color(0xFF85736C) else Color(0xFFA08D85)
    val errorSuffixColor = if (isLightTheme) Color(0xFFBA1A1A) else Color(0xFFFFB4AB)

    val textSelectionColors = TextSelectionColors(
        handleColor = if (isLightTheme) Color(0xFF995D00) else Color(0xFFFFB184),
        backgroundColor = if (isLightTheme) Color(0xFFEDD6BF) else Color(0xFF442A1F)
    )

    return TextFieldColors(
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        disabledTextColor = disabledTextColor,
        errorTextColor = errorTextColor,
        focusedContainerColor = focusedContainerColor,
        unfocusedContainerColor = unfocusedContainerColor,
        disabledContainerColor = disabledContainerColor,
        errorContainerColor = errorContainerColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        textSelectionColors = textSelectionColors,
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        errorIndicatorColor = errorIndicatorColor,
        focusedLeadingIconColor = focusedLeadingIconColor,
        unfocusedLeadingIconColor = unfocusedLeadingIconColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        errorLeadingIconColor = errorLeadingIconColor,
        focusedTrailingIconColor = focusedTrailingIconColor,
        unfocusedTrailingIconColor = unfocusedTrailingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        errorTrailingIconColor = errorTrailingIconColor,
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        errorLabelColor = errorLabelColor,
        focusedPlaceholderColor = focusedPlaceholderColor,
        unfocusedPlaceholderColor = unfocusedPlaceholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor,
        errorPlaceholderColor = errorPlaceholderColor,
        focusedSupportingTextColor = focusedSupportingTextColor,
        unfocusedSupportingTextColor = unfocusedSupportingTextColor,
        disabledSupportingTextColor = disabledSupportingTextColor,
        errorSupportingTextColor = errorSupportingTextColor,
        focusedPrefixColor = focusedPrefixColor,
        unfocusedPrefixColor = unfocusedPrefixColor,
        disabledPrefixColor = disabledPrefixColor,
        errorPrefixColor = errorPrefixColor,
        focusedSuffixColor = focusedSuffixColor,
        unfocusedSuffixColor = unfocusedSuffixColor,
        disabledSuffixColor = disabledSuffixColor,
        errorSuffixColor = errorSuffixColor
    )
}