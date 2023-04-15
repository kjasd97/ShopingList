package com.ulyanenko.shopinglist.presentation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.ulyanenko.shopinglist.R

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError:Boolean){
        val message = if (isError) {
           textInputLayout.context.getString(R.string.errorName)
        } else {
            null
        }
       textInputLayout.error = message

}


@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError:Boolean){
    val message = if (isError) {
        textInputLayout.context.getString(R.string.errorCount)
    } else {
        null
    }
    textInputLayout.error = message

}