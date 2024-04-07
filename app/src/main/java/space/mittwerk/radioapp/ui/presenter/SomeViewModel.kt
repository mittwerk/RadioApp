package space.mittwerk.radioapp.ui.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

class SomeViewModel(private val getSomeModelUseCase: GetSomeModelUseCase) :
    ContainerHost<ExampleState, ExampleSideEffect>,
    ViewModel() {
    override val container = container<ExampleState, ExampleSideEffect>(ExampleState())

    fun load() {
        viewModelScope.launch {
            val result = getSomeModelUseCase()
            // Do something with the result
        }
    }

    fun onClick(number: Int) =
        intent {
            postSideEffect(ExampleSideEffect.Toast("Adding $number to ${state.total}!"))
            // reduce { state.copy(total = state.total + number) }
        }
}

fun interface GetSomeModelUseCase : suspend () -> Either<List<SomeModel>, String>

data object SomeModel

data class ExampleState(val seen: List<String> = emptyList()) {
    val total: Int = 0
}

sealed class ExampleSideEffect {
    data class Toast(val text: String) : ExampleSideEffect()
}

@JvmInline
value class ID(val value: String)

@JvmInline
value class Name(val value: String)

@JvmInline
value class Points(val value: Int) : Comparable<Points> {
    init {
        check(value >= 0)
    }

    override fun compareTo(other: Points): Int {
        return value.compareTo(other.value)
    }

    operator fun minus(other: Points): Points {
        return Points(value - other.value)
    }
}
