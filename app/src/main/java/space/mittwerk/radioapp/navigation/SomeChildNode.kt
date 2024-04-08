package space.mittwerk.radioapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext

class SomeChildNode(
    nodeContext: NodeContext,
) : RootNode(
    nodeContext = nodeContext,
) {
    @Composable
    override fun Content(modifier: Modifier) {
        Text("This is SomeChildNode")
    }
}
