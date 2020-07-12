package app.chamich.feature.settings.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.chamich.feature.settings.R
import app.chamich.library.logger.ILogger
import app.chamich.library.preferences.IPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class GeneralSettingsFragment : Fragment() {

    @Inject
    lateinit var logger: ILogger

    @Inject
    lateinit var preferences: IPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.settings_fragment_general_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.debug(message = "Settings View Created")
    }

}