package space.mittwerk.radioapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import arrow.core.Either
import org.orbitmvi.orbit.compose.collectAsState
import space.mittwerk.radioapp.ui.presenter.GetSomeModelUseCase
import space.mittwerk.radioapp.ui.presenter.SomeViewModel
import space.mittwerk.radioapp.ui.theme.RadioAppTheme
import space.mittwerk.radioapp.ui.utils.UIModePreviews

@Suppress("ktlint:standard:function-naming")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: SomeViewModel,
) {
    val state by viewModel.collectAsState()

    Box(modifier = modifier) {
        Text(text = "Main Screen")
    }
}

@Suppress("ktlint:standard:function-naming")
@UIModePreviews
@Composable
fun MainScreenPreview() {
    RadioAppTheme {
        MainScreen(viewModel = SomeViewModel(GetSomeModelUseCase { Either.Left(emptyList()) }))
    }
}
