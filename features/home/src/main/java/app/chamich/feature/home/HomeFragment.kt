package app.chamich.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import app.chamich.feature.home.databinding.HomeFragmentHomeBinding


class HomeFragment : Fragment() {

    private val navController: NavController by lazy { findNavController(this) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<HomeFragmentHomeBinding>(
        inflater, R.layout.home_fragment_home, container, false
    ).root

}
