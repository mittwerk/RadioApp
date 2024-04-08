package space.mittwerk.radioapp.config

import space.mittwerk.radioapp.BuildConfig
import javax.inject.Inject

class Config
@Inject
constructor() {
    fun isDebugMode() = BuildConfig.DEBUG
}
