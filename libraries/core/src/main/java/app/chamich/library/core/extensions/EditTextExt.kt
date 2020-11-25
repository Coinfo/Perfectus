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

val EditText.asInt
    get() = try {
        if (!this.hasText) 0 else this.text.toString().toInt()
    } catch (e: NumberFormatException) {
        0
    }

/**
 * Returns the {@see EditText} text as String
 */
val EditText.asString
    get() = this.text.toString()

val EditText.asTrimmedString
    get() = this.asString.trim()

val EditText.hasText
    get() = !this.text.isNullOrBlank()
