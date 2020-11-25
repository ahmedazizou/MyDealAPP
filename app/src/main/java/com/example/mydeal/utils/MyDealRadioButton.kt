package com.example.mydeal.utils


import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class MyDealRadioButton(context:Context , attributeSet: AttributeSet)
    :AppCompatRadioButton(context,attributeSet) {
        init {
            applyFont()
        }
    private fun applyFont() {
        //this is used to get the file from the assets folder and set ot tp the title textView.
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Font_Bold.ttf")
        setTypeface(typeface)

    }
}