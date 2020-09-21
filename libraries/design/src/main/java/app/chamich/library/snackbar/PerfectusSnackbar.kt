/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.snackbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import app.chamich.library.design.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


class PerfectusSnackbar(
    parent: ViewGroup,
    content: PerfectusSnackbarView
) : BaseTransientBottomBar<PerfectusSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(
            view: View,
            message: String,
            @Duration duration: Int = Snackbar.LENGTH_LONG
        ): PerfectusSnackbar {
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )

            return PerfectusSnackbar(parent,
                (LayoutInflater.from(view.context).inflate(
                    R.layout.design_snackbar,
                    parent,
                    false
                ) as PerfectusSnackbarView).apply {
                    setMessage(message)
                }).apply {
                this.duration = duration
            }
        }

        fun make(
            view: View,
            @StringRes message: Int,
            @Duration duration: Int = Snackbar.LENGTH_LONG
        ) =
            make(view, view.context.getString(message, duration))
    }
}


