package com.gym.delta.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gym.delta.R

// Define your custom font family
val kanitFontFamily = FontFamily(
    Font(R.font.kanit_regular, FontWeight.Normal),
    Font(R.font.kanit_italic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.kanit_bold, FontWeight.Bold),
    Font(R.font.kanit_bolditalic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.kanit_semibold, FontWeight.SemiBold),
    Font(R.font.kanit_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),

    Font(R.font.kanit_extrabold, FontWeight.ExtraBold),
    Font(R.font.kanit_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),

    Font(R.font.kanit_thin, FontWeight.Thin),
    Font(R.font.kanit_thinitalic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.kanit_medium, FontWeight.Medium),
    Font(R.font.kanit_mediumitalic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.kanit_black, FontWeight.Black),
    Font(R.font.kanit_blackitalic, FontWeight.Black, FontStyle.Italic),

    Font(R.font.kanit_light, FontWeight.Light),
    Font(R.font.kanit_lightitalic, FontWeight.Light, FontStyle.Italic),

    Font(R.font.kanit_extralight, FontWeight.ExtraLight),
    Font(R.font.kanit_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    // Add other font styles as needed
)


// Set of Material typography styles to start with
val kanitTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = kanitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = kanitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = kanitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 22.sp,
//        lineHeight = 28.sp,
//        letterSpacing = 0.sp
//    ),
    titleSmall = TextStyle(
        fontFamily = kanitFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.2.sp
    ),
//    labelSmall = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Medium,
//        fontSize = 11.sp,
//        lineHeight = 16.sp,
//        letterSpacing = 0.5.sp
//    )
    headlineMedium = TextStyle(
        fontFamily = kanitFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
//        letterSpacing = 0.2.sp
    ),
)