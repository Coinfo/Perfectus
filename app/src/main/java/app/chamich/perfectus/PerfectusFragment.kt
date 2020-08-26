/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.perfectus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.chamich.feature.goals.ui.goals.GoalsFragment
import kotlinx.android.synthetic.main.fragment_perfectus.*


class PerfectusFragment : Fragment() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfectus, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViewPager()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Function

    private fun initializeViewPager() {
        view_pager_perfectus.adapter = PerfectusAdapter(requireActivity(), 2)
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    class PerfectusAdapter(
        activity: FragmentActivity, private val itemsCount: Int
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount() = itemsCount

        override fun createFragment(position: Int): Fragment {
            return GoalsFragment()
        }
    }

}
