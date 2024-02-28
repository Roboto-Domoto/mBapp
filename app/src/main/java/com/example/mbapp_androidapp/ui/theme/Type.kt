package com.example.mbapp_androidapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mbapp_androidapp.R

val amableFamily = FontFamily(
    Font(R.font.amable_font, FontWeight.Normal)
)

val ardnasFamily = FontFamily(
    Font(R.font.ardnas, FontWeight.Normal)
)

val caviarFamily = FontFamily(
    Font(R.font.caviar_dreams, FontWeight.Normal),
    Font(R.font.caviar_dreams_bold, FontWeight.Bold),
    Font(R.font.caviar_dreams_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.caviar_dreams_bolditalic, FontWeight.Bold, FontStyle.Italic)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)