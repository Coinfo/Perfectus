/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import app.chamich.library.design.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PerfectusDialog : BottomSheetDialogFragment() {

    private var title: String? = null
    private var message: String? = null
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null
    private var positiveButtonListener: () -> Unit = { }
    private var negativeButtonListener: () -> Unit = { }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.design_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView(view)
    }


    fun setTitle(title: String?) {
        this.title = title
    }

    fun setTitle(@StringRes title: Int) {
        setTitle(getString(title))
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun setMessage(@StringRes message: Int) {
        setMessage(getString(message))
    }

    fun setPositiveButton(text: String, listener: () -> Unit) {
        positiveButtonText = text
        positiveButtonListener = listener
    }

    fun setPositiveButton(@StringRes text: Int, listener: () -> Unit) {
        setPositiveButton(getString(text), listener)
    }

    fun setNegativeButton(text: String, listener: () -> Unit) {
        negativeButtonText = text
        negativeButtonListener = listener
    }

    fun setNegativeButton(@StringRes text: Int, listener: () -> Unit) {
        setNegativeButton(getString(text), listener)
    }

    fun show(fm: FragmentManager?) {
        fm?.let {
            (it.findFragmentByTag(TAG_PERFECTUS_DIALOG) as? PerfectusDialog ?: this)
                .show(it, TAG_PERFECTUS_DIALOG)
        }
    }

    private fun initializeView(view: View) {
        view.findViewById<TextView>(R.id.text_view_title).text = title
        view.findViewById<TextView>(R.id.text_view_message).text = message
        view.findViewById<Button>(R.id.button_positive).apply {
            text = positiveButtonText
            setOnClickListener {
                positiveButtonListener()
            }
        }
        view.findViewById<Button>(R.id.button_negative).apply {
            text = negativeButtonText
            setOnClickListener {
                negativeButtonListener()
            }
        }
    }


    companion object {
        private const val TAG_PERFECTUS_DIALOG =
            "app.chamich.library.dialog.TAG_PERFECTUS_DIALOG"
    }
}
