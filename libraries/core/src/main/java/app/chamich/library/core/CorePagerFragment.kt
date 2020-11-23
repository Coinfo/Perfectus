/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.core

import android.widget.TextView
import androidx.fragment.app.Fragment

abstract class CorePagerFragment : Fragment() {

    abstract fun handleAddClicked()

    abstract fun setTitleView(view: TextView)

    abstract fun getSubtitle(): String
}
