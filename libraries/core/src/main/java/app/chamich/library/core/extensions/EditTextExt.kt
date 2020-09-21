/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.core.extensions

import android.widget.EditText

/**
 * Returns the {@see EditText} text as Long if conversion succeed; otherwise 0
 */
val EditText.asLong
    get() = try {
        if (!this.hasText) 0L else this.text.toString().toLong()
    } catch (e: NumberFormatException) {
        0L
    }

/**
 * Returns the {@see EditText} text as String
 */
val EditText.asString
    get() = this.text.toString()

val EditText.hasText
    get() = !this.text.isNullOrBlank()
