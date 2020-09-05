/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.core

import androidx.fragment.app.Fragment

abstract class CorePagerFragment : Fragment() {

    abstract fun handleAddClicked()

    abstract fun getTitle(): String

    abstract fun getSubtitle(): String
}
