package com.example.mydeal.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MyDealButton(context: Context,attrs:AttributeSet):AppCompatButton(context, attrs) {

    init {
        // call the funcation to apply thr font to components
        applyFont()
    }

    private fun applyFont() {
        //this is used to get the file from the assets folder and set ot tp the title textView.
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Font_Bold.ttf")
        setTypeface(typeface)
    }
}