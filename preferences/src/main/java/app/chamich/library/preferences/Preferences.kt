package app.chamich.library.preferences

import android.util.Log
import app.chamich.library.preferences.api.IPreferences

class Preferences: IPreferences {

    init {
        Log.d("--| DEBUGGING |--",
            "Preferences object created with ID: ${System.identityHashCode(this)}")
    }

}