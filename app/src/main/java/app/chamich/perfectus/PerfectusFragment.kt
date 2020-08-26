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
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.chamich.feature.goals.ui.goals.GoalsFragment
import app.chamich.feature.sharing.ui.sharing.SharingFragment
import app.chamich.feature.tasks.ui.tasks.TasksFragment
import app.chamich.library.authentication.IAuthenticator
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_perfectus.*
import javax.inject.Inject

@AndroidEntryPoint
class PerfectusFragment : Fragment() {

    private val fragments by lazy {
        listOf(
            Pair(GoalsFragment(), R.string.app_tab_goals),
            Pair(TasksFragment(), R.string.app_tab_tasks),
            Pair(SharingFragment(), R.string.app_tab_sharing)
        )
    }

    @Inject
    lateinit var authenticator: IAuthenticator

    private val navController: NavController by lazy { findNavController(this) }

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
        initializeTabLayout()
        addMenuItemClickListener()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Function

    private fun addMenuItemClickListener() {
        appbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem?.itemId) {
                R.id.action_sign_out -> {
                    authenticator.signOut()
                    navController.popBackStack()
                    navController.navigate(R.id.navigation_authentication)
                }
                R.id.action_settings -> {
                    navController.navigate(R.id.navigation_settings)
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun initializeViewPager() {
        view_pager_perfectus.adapter = PerfectusAdapter(requireActivity())
    }

    private fun initializeTabLayout() {
        TabLayoutMediator(tab_layout_perfectus, view_pager_perfectus) { tab, position ->
            tab.text = getText(fragments[position].second)
        }.attach()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    inner class PerfectusAdapter(
        activity: FragmentActivity
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position].first
    }

}
