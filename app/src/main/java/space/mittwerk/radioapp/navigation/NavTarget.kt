package space.mittwerk.radioapp.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class NavTarget : Parcelable {
    @Parcelize
    data object Child1 : NavTarget()

    @Parcelize
    data object Child2 : NavTarget()

    @Parcelize
    data object Child3 : NavTarget()
}
