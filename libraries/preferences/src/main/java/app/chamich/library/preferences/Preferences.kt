package app.chamich.library.preferences

import android.content.Context
import android.util.Log

class Preferences(
    context: Context
) : IPreferences {

    private val preference =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    init {
        Log.d(
            "--| DEBUG |--",
            "Preferences object created with ID: ${System.identityHashCode(this)}"
        )
    }

    override fun saveInt(key: String, value: Int) {
        val editable = preference.edit()
        editable.putInt(key, value)
        editable.apply()
    }

    override fun loadInt(key: String) =
        if (preference.contains(key)) preference.getInt(key, -1) else null


    private companion object {
        const val SHARED_PREFERENCES_NAME = "perfectus.prefs"
    }
}
