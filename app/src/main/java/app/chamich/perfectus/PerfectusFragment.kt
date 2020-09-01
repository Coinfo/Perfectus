/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.perfectus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import app.chamich.feature.goals.ui.GoalsPagerFragment
import app.chamich.library.authentication.IAuthenticator
import app.chamich.library.core.CorePagerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_perfectus.*
import javax.inject.Inject

@AndroidEntryPoint
class PerfectusFragment : Fragment() {

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

        initializeViewPagerAndTabLayout()
        handleMenuItemClicks()
        handleFabClicks()
    }

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Function

    private fun initializeViewPagerAndTabLayout() {
        view_pager_perfectus.adapter = PerfectusAdapter(requireActivity().supportFragmentManager)
        tab_layout_perfectus.setupWithViewPager(view_pager_perfectus)
    }

    private fun handleFabClicks() {
        val adapter = view_pager_perfectus.adapter as PerfectusAdapter
        fab_add.setOnClickListener {
            // Each fragment in the ViewPager handles "Floating Action Button" clicks itself.
            adapter.getCurrentFragment()?.handleAddClicked()
        }
    }

    private fun handleMenuItemClicks() {
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

    //endregion
    ////////////////////////////////////////////////////////////////////////////////////////////////

    inner class PerfectusAdapter(
        fm: FragmentManager
    ) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        private val fragments = listOf(
            Pair(GoalsPagerFragment(), R.string.app_pager_goals)
        )

        private var currentPagerFragment: CorePagerFragment? = null

        override fun setPrimaryItem(container: ViewGroup, position: Int, fragment: Any) {
            if (currentPagerFragment != fragment) {
                currentPagerFragment = fragment as CorePagerFragment
            }
            super.setPrimaryItem(container, position, fragment)
        }

        override fun getItem(position: Int) = fragments[position].first

        override fun getCount() = fragments.size

        override fun getPageTitle(position: Int) = context?.getString(fragments[position].second)

        fun getCurrentFragment(): CorePagerFragment? = currentPagerFragment

    }

}
