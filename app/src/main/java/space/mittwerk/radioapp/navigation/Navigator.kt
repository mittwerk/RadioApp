package space.mittwerk.radioapp.navigation

import androidx.compose.runtime.compositionLocalOf
import com.bumble.appyx.navigation.plugin.NodeReadyObserver
import kotlinx.coroutines.CoroutineScope

val LocalNavigator = compositionLocalOf { Navigator() }

open class Navigator : NodeReadyObserver<RootNode> {
    private lateinit var rootNode: RootNode
    private lateinit var lifecycleScope: CoroutineScope

    override fun init(node: RootNode) {
        rootNode = node
        lifecycleScope = node.lifecycleScope
    }
}
