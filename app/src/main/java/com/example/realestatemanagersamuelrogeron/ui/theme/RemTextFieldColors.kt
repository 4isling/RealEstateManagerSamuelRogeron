package com.example.realestatemanagersamuelrogeron.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable

@Composable
fun remTextFieldColors(): TextFieldColors {
    val isLightTheme = !isSystemInDarkTheme()

    // Define colors based on the theme using your color scheme
    val focusedTextColor = if (isLightTheme) onSurfaceLight else onSurfaceDark
    val unfocusedTextColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledTextColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorTextColor = if (isLightTheme) onErrorLight else onErrorDark

    val focusedContainerColor = if (isLightTheme) surfaceLight else surfaceDark
    val unfocusedContainerColor = if (isLightTheme) surfaceVariantLight else surfaceVariantDark
    val disabledContainerColor = if (isLightTheme) surfaceVariantLight.copy(alpha = 0.4f) else surfaceVariantDark.copy(alpha = 0.4f)
    val errorContainerColor = if (isLightTheme) errorContainerLight else errorContainerDark

    val cursorColor = if (isLightTheme) primaryLight else primaryDark
    val errorCursorColor = if (isLightTheme) errorLight else errorDark

    val focusedIndicatorColor = if (isLightTheme) primaryLight else primaryDark
    val unfocusedIndicatorColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledIndicatorColor = if (isLightTheme) outlineVariantLight.copy(alpha = 0.4f) else outlineVariantDark.copy(alpha = 0.4f)
    val errorIndicatorColor = if (isLightTheme) errorLight else errorDark

    val focusedLeadingIconColor = if (isLightTheme) onSurfaceLight else onSurfaceDark
    val unfocusedLeadingIconColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledLeadingIconColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorLeadingIconColor = if (isLightTheme) errorLight else errorDark

    val focusedTrailingIconColor = if (isLightTheme) onSurfaceLight else onSurfaceDark
    val unfocusedTrailingIconColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledTrailingIconColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorTrailingIconColor = if (isLightTheme) errorLight else errorDark

    val focusedLabelColor = if (isLightTheme) primaryLight else primaryDark
    val unfocusedLabelColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledLabelColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorLabelColor = if (isLightTheme) errorLight else errorDark

    val focusedPlaceholderColor = if (isLightTheme) onSurfaceLight.copy(alpha = 0.6f) else onSurfaceDark.copy(alpha = 0.6f)
    val unfocusedPlaceholderColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.6f) else onSurfaceVariantDark.copy(alpha = 0.6f)
    val disabledPlaceholderColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorPlaceholderColor = if (isLightTheme) errorLight.copy(alpha = 0.6f) else errorDark.copy(alpha = 0.6f)

    val focusedSupportingTextColor = if (isLightTheme) onSurfaceLight else onSurfaceDark
    val unfocusedSupportingTextColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledSupportingTextColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorSupportingTextColor = if (isLightTheme) errorLight else errorDark

    val focusedPrefixColor = if (isLightTheme) onSurfaceLight else onSurfaceDark
    val unfocusedPrefixColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledPrefixColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorPrefixColor = if (isLightTheme) errorLight else errorDark

    val focusedSuffixColor = if (isLightTheme) onSurfaceLight else onSurfaceDark
    val unfocusedSuffixColor = if (isLightTheme) onSurfaceVariantLight else onSurfaceVariantDark
    val disabledSuffixColor = if (isLightTheme) onSurfaceVariantLight.copy(alpha = 0.4f) else onSurfaceVariantDark.copy(alpha = 0.4f)
    val errorSuffixColor = if (isLightTheme) errorLight else errorDark

    val textSelectionColors = TextSelectionColors(
        handleColor = if (isLightTheme) primaryLight else primaryDark,
        backgroundColor = if (isLightTheme) primaryContainerLight else primaryContainerDark
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