package app.chamich.library.preferences

import android.content.Context
import android.util.Log

class Preferences(
    private val context: Context
): IPreferences {

    init {
        Log.d("--| DEBUG |--",
            "Preferences object created with ID: ${System.identityHashCode(this)}")
    }

}
