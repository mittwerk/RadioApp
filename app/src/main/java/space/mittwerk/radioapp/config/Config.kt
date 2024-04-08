package space.mittwerk.radioapp.config

import jakarta.inject.Inject
import space.mittwerk.radioapp.BuildConfig

class Config
@Inject
constructor() {
    fun isDebugMode() = BuildConfig.DEBUG
}
