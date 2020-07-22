package app.chamich.library.preferences

interface IPreferences {

    /**
     * Saves integer [value] to the given [key]
     */
    fun saveInt(key: String, value: Int)

    /**
     * Loads saved integer by the [key]; otherwise if no saved integer returns null
     */
    fun loadInt(key: String): Int?
}
