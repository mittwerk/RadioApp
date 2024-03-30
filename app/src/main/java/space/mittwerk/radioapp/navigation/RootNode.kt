package space.mittwerk.radioapp.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.node

open class RootNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget> =
        BackStack(
            model =
                BackStackModel(
                    initialTarget = NavTarget.Child1,
                    savedStateMap = nodeContext.savedStateMap,
                ),
            visualisation = { BackStackFader(it) },
        ),
) : Node<NavTarget>(
        appyxComponent = backStack,
        nodeContext = nodeContext,
    ) {
    override fun buildChildNode(
        navTarget: NavTarget,
        nodeContext: NodeContext,
    ): Node<*> =
        when (navTarget) {
            NavTarget.Child1 -> node(nodeContext) { Text(text = "Placeholder for child 1") }
            NavTarget.Child2 -> node(nodeContext) { Text(text = "Placeholder for child 2") }
            NavTarget.Child3 -> SomeChildNode(nodeContext)
        }

    @Composable
    override fun Content(modifier: Modifier) {
        Column(
            modifier = modifier,
        ) {
            // Let's include the elements of our component into the composition
            AppyxNavigationContainer(
                appyxComponent = backStack,
                modifier = Modifier.weight(0.9f),
            )

            // Let's also add some controls so we can test it
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(0.1f),
            ) {
                TextButton(onClick = { backStack.push(NavTarget.Child1) }) {
                    Text(text = "Push child 1")
                }
                TextButton(onClick = { backStack.push(NavTarget.Child2) }) {
                    Text(text = "Push child 2")
                }
                TextButton(onClick = { backStack.push(NavTarget.Child3) }) {
                    Text(text = "Push child 3")
                }
                TextButton(onClick = { backStack.pop() }) {
                    Text(text = "Pop")
                }
            }
        }
    }
}
