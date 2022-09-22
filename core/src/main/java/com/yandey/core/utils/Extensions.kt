package com.yandey.core.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.yandey.core.R
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.ln
import kotlin.math.pow

fun ImageView.loadImage(context: Context, url: String?) {
    Glide
        .with(context)
        .load(url)
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.image_error)
        .into(this)
}

fun String.toShortNumber(): String {
    return try {
        val doubleNumber = this.toDouble()
        val formatter = DecimalFormat("0.#")
        val isNumberLessThanThousand = this.toInt() < 1000.0
        formatter.roundingMode = RoundingMode.DOWN

        if (isNumberLessThanThousand) {
            formatter.format(doubleNumber)
        } else {
            val exp = (ln(doubleNumber) / ln(1000.0)).toInt()
            formatter.format(this.toInt() / 1000.0.pow(exp.toDouble())) + "KMBTPE"[exp - 1]
        }
    } catch (e: Exception) {
        "0"
    }
}

fun showSnackBar(view: View?, message: String) {
    if (view != null) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction(R.string.text_close) {
        }.show()
    }
}