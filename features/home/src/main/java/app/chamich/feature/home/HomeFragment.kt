package app.chamich.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.home_fragment_home.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.home_fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        appbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem?.itemId) {
                R.id.action_signin -> {
                    val myNavHostFragment = nav_host_fragment_home.findNavController()
                    val inflater = myNavHostFragment.navInflater
                    val graph = inflater.inflate(R.navigation.account_navigation)
                    myNavHostFragment.graph = graph
                }
                R.id.action_settings -> {
                    val myNavHostFragment = nav_host_fragment_home.findNavController()
                    val inflater = myNavHostFragment.navInflater
                    val graph = inflater.inflate(R.navigation.settings_navigation)
                    myNavHostFragment.graph = graph
                }

            }
            return@setOnMenuItemClickListener true
        }


    }
}