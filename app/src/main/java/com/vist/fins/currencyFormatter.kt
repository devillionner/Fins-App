package com.vist.fins

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import com.vist.fins.data.OperationType
import com.vist.fins.ui.theme.Currency_expense_color
import com.vist.fins.ui.theme.Currency_expense_color_base
import com.vist.fins.ui.theme.Currency_income_color
import com.vist.fins.ui.theme.Currency_income_color_base
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

fun currencyFormatter(num: Float): String {
    val m = num.toString().replace(",",".")
//    val m = num.toString()
    val formatter = DecimalFormat("###,###,###.##")
    return formatter.format(m.toDouble()).replace(","," ")
}

val annotatedStringBase = AnnotatedString.Builder().run {
    append("0")
    toAnnotatedString()
}

val anStringBase = buildAnnotatedString {
    append("0")
}

class CurrencyTransformation(operationType: String) : VisualTransformation {
    private val opTp = operationType
    override fun filter(text: AnnotatedString): TransformedText {
        return dateFilter(text, opTp)
    }
}

fun dateFilter(text: AnnotatedString, operationType: String): TransformedText {
    val groupingSymbol = ' '
    val decimalSymbol = '.'

//    val locale = Locale("en", "US")
    val symbols = DecimalFormatSymbols(Locale.US)
    symbols.groupingSeparator = groupingSymbol
    symbols.decimalSeparator = decimalSymbol
    val pattern = "###,###,###.##"
    val decimalFormat = DecimalFormat(pattern, symbols)
//    val format = decimalFormat.format(text.text.toFloat())

    val trimmed = text.text
    val filteredChars = trimmed.filterIndexed { index, c ->
        c in "0123456789" ||                      // Take all digits
        (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }


    var out = ""
    var outTest = ""
    val offset = 2
    val offsetTwo = 1
    for (i in trimmed.indices) {
        if (filteredChars.contains(".")) {
            val beforeDecimal = filteredChars.substringBefore('.')
            val afterDecimal = filteredChars.substringAfter('.')
            out = decimalFormat.format(if (text.text.startsWith(".")) 0 else beforeDecimal.take(9).toDouble()) + "." + afterDecimal.take(2)
//            Log.i("MYTAG", "out with dot: ${out}")
        } else {
//            Log.i("MYTAG", "text.text: ${text.text}")
//            Log.i("MYTAG", "trimmed: ${trimmed}")
            out = decimalFormat.format(trimmed.toDouble()).toString()
//            Log.i("MYTAG", "out: ${out}")

        }

//        Log.i("MYTAG", trimmed)
//        Log.i("MYTAG", out)

//        out += trimmed[i]
//        outTest += trimmed[i]
//        if (i % 3 == 2 && i < 6) out += " "
//        if (i % 5 == 4 && i < 6 || i % 4 == 3 && i < 6) outTest = outTest.replaceRange(i-offset, i-offset, " ")


//        if (i % 7 == 6 && i < 10) outTest = outTest.replaceRange(i-offsetTwo, i-offsetTwo, " ")
//        Log.i("MYTAG", (i % 3).toString())
//        Log.i("MYTAG", i.toString())
    }

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
//            Log.i("MYTAG", "OffsetTransformed: ${offset}")
//            if (offset <= 3) return offset + if (text.isEmpty()) 3 else 2
//            if (offset <= 6) return offset + 3
////            if (offset <= 9) return offset + 4
//            if (offset <= 12 ) return offset + 4
//            return 16
            return out.length + if (text.isEmpty()) 3 else 2
        }

        override fun transformedToOriginal(offset: Int): Int {
//            Log.i("MYTAG", "OffsetOriginal: ${offset}")
//            if (offset <= 5) return offset - if (text.isEmpty()) 3 else 2
//            if (offset <= 9) return offset - 3
////            if (offset <= 13) return offset - 4
//            if (offset <= 16) return offset - 4
//            return 12
            return text.length
        }
    }

    val anString = buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = if (operationType == OperationType.income) Currency_income_color_base else if (operationType == OperationType.expense) Currency_expense_color_base else Color.White.copy(0.25f),
//            color = Color,
            fontWeight = FontWeight.Light
        )) {
            append("₴ ")
        }
        append(if (text.isEmpty()) "0" else out)
    }

    return TransformedText(anString, numberOffsetTranslator)
//    return TransformedText(AnnotatedString(text.text.toLongOrNull().formatWithComma()), numberOffsetTranslator)
}

fun getValidatedNumber(text: String): String {
    // Start by filtering out unwanted characters like commas and multiple decimals
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789" ||                      // Take all digits
        (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }
    // Now we need to remove extra digits from the input
    return if(filteredChars.contains('.')) {
        val beforeDecimal = filteredChars.substringBefore('.')
        val afterDecimal = filteredChars.substringAfter('.')
//        Log.i("MYTAG", "${filteredChars.length}")
        beforeDecimal.take(9) + "." + afterDecimal.take(2)                // If there is no decimal, just take the first 3 digits
    } else {
        filteredChars.take(9)                     // If there is no decimal, just take the first 3 digits
    }
}

fun Long?.formatWithComma(): String =
    NumberFormat.getNumberInstance(Locale.US).format(this?.toDouble())

fun currencyInputFormatter(num: AnnotatedString): TransformedText {
    val groupingSymbol = ' '
    val decimalSymbol = '.'
//    Log.i("MYTAG", "${num.text}")

    val locale = Locale("en", "UK")
    val symbols = DecimalFormatSymbols(locale)
    symbols.groupingSeparator = '.'
    val pattern = "###,###.##"
    val decimalFormat = DecimalFormat(pattern, symbols)
    val format = decimalFormat.format(num.text.toFloat())

//    val annotatedString = AnnotatedString.Builder().run {
//        for (i in format.indices) {
//            append(format[i])
//
//        }
//        toAnnotatedString()
//    }

    val anString = buildAnnotatedString {
        for (i in format.indices) {
            append(format[i])

        }
    }

//    val phoneNumberOffsetTranslator = object: OffsetMapping {
//        override fun originalToTransformed(offset: Int): Int {
////            val offsetValue = offset.absoluteValue
////            if (offsetValue == 0) return 0
//            if (offset <= 2) return offset
//            if (offset <= 4) return offset + 1
//            if (offset <= 6) return offset + 2
//            if (offset <= 9) return offset + 3
//            return 12
//        }
//
//        override fun transformedToOriginal(offset: Int): Int {
////            val offsetValue = offset.absoluteValue
////            if (offsetValue == 0) return 0
//            if (offset <= 3) return  offset
//            if (offset <= 6) return  offset - 1
//            if (offset <= 9) return  offset - 2
//            if (offset <= 12) return  offset - 3
//            return 9
//        }
//    }

//    return format
    return TransformedText(anString, OffsetMapping.Identity)
}
const val mask = "xx xxx xx xx"

fun mobileNumberFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 6) text.text.substring(0..5) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i == 1 || i == 4 || i == 6) {
                append(" ")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val phoneNumberOffsetTranslator = object: OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 1) return offset
            if (offset <= 4) return offset + 1
            if (offset <= 6) return offset + 2
            if (offset <= 9) return offset + 3
            return 12
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 1) return  offset
            if (offset <= 4) return  offset - 1
            if (offset <= 6) return  offset - 2
            if (offset <= 9) return  offset - 3
            return 9
        }
    }

    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}

//class CurrencyInputFormatterTransformation() : VisualTransformation {
//    override fun filter(text: AnnotatedString): TransformedText {
//        val symbols = DecimalFormat().decimalFormatSymbols
//        val decimalSeparator = symbols.decimalSeparator
//
//        var outputText = ""
//        var integerPart = 0L
//        var decimalPart = ""
//
//    }
//
//}