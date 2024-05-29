package space.mittwerk.radioapp

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.bumble.appyx.navigation.integration.NodeActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import dagger.hilt.android.AndroidEntryPoint
import space.mittwerk.radioapp.navigation.RootNode
import space.mittwerk.radioapp.ui.theme.RadioAppTheme

@AndroidEntryPoint
class MainActivity : NodeActivity() {
//    @Inject
//    private lateinit var viewModel: SomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
                navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            )

            // Lifecycle.State.STARTED
//            viewModel.observe(state = ::render, sideEffect = ::handleSideEffect)
            RadioAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NodeHost(
                        lifecycle = AndroidLifecycle(LocalLifecycleOwner.current.lifecycle),
                        integrationPoint = appyxIntegrationPoint,
                    ) {
                        RootNode(it)
                    }
                }
            }
        }
    }
}
