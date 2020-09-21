/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import app.chamich.library.design.R
import com.google.android.material.snackbar.ContentViewCallback

class PerfectusSnackbarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    init {
        View.inflate(context, R.layout.design_snackbar_view, this)
        clipToPadding = false
    }

    fun setMessage(message: String) {
        findViewById<TextView>(R.id.text_view_snackbar).text = message
    }

    override fun animateContentIn(delay: Int, duration: Int) = Unit

    override fun animateContentOut(delay: Int, duration: Int) = Unit
}
