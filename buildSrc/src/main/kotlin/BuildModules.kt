/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

object BuildModules {

    object Features {
        const val SETTINGS = ":features:settings"
        const val AUTHENTICATION = ":features:authentication"
        const val GOALS = ":features:goals"
        const val TASKS = ":features:tasks"
        const val SHARING = ":features:sharing"
        const val PROFILE = ":features:profile"
    }

    object Libraries {
        const val CORE = ":libraries:core"
        const val DESIGN = ":libraries:design"
        const val LOGGER = ":libraries:logger"
        const val PREFERENCES = ":libraries:preferences"
        const val AUTHENTICATOR = ":libraries:authenticator"
        const val DATABASE = ":libraries:database"
    }
}
