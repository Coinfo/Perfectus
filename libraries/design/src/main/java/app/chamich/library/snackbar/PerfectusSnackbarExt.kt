/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.snackbar

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup?

    do {
        if (view is CoordinatorLayout || (view is FrameLayout && view.id == android.R.id.content)) {
            return view as? ViewGroup
        } else {
            fallback = view as? ViewGroup
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    return fallback
}
