/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.friends.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.chamich.feature.friends.R
import app.chamich.library.core.CorePagerFragment


class FriendsPagerFragment : CorePagerFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.friends_container, container, false)
    }

    override fun handleAddClicked() {
        // Do Nothing
    }

    override fun setTitleView(view: TextView) {
        // Do Nothing
    }

    override fun getSubtitle(): String {
        return ""
    }
}
