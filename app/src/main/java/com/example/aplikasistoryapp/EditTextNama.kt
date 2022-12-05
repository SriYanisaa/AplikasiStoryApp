package com.example.aplikasistoryapp

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class EditTextNama : AppCompatEditText {
    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = context.getString(R.string.edt_nama)
    }

    private fun init(){
        maxLines = 1
        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setSelection(text!!.length)
                if (s != null){
                    if (s.isEmpty()){
                        error = "Lengkapi nama anda !"
                    }
                    else{
                        error = null
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
}
