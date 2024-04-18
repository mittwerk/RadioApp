 package space.mittwerk.radioapp.ui.presenter

 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.viewModelScope
 import arrow.core.Either
 import arrow.core.Either.Left
 import arrow.core.Either.Right
 import arrow.core.left
 import arrow.core.raise.Raise
 import arrow.core.right
 import kotlinx.coroutines.launch
 import org.orbitmvi.orbit.ContainerHost
 import org.orbitmvi.orbit.syntax.simple.intent
 import org.orbitmvi.orbit.syntax.simple.postSideEffect
 import org.orbitmvi.orbit.viewmodel.container
 import kotlin.reflect.KProperty

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

 sealed class Q {
    data class EventNotFound(val id: EventId) : Q()

    data class EventPassed(val event: Event) : Q()
 }

 interface EventService {
    suspend fun fetchUpcomingEvent(id: EventId): Event
 }

 @JvmInline
 value class Url(val value: String)

 @JvmInline
 value class City(val value: String)

 @JvmInline
 value class Street(val value: String)

 data class Address(val city: City, val street: Street)

 data class Title(val value: String)

 data class Organizer(val value: String)

 data class Description(val value: String)

 data class AgeRestriction(val value: Int)

 data class LocalDate(val value: String)

 sealed class Event {
    abstract val id: EventId
    abstract val title: Title
    abstract val organizer: Organizer
    abstract val description: Description
    abstract val ageRestriction: AgeRestriction
    abstract val date: LocalDate

    data class Online(
        override val id: EventId,
        override val title: Title,
        override val organizer: Organizer,
        override val description: Description,
        override val ageRestriction: AgeRestriction,
        override val date: LocalDate,
        val url: Url,
    ) : Event()

    data class AtAddress(
        override val id: EventId,
        override val title: Title,
        override val organizer: Organizer,
        override val description: Description,
        override val ageRestriction: AgeRestriction,
        override val date: LocalDate,
        val address: Address,
    ) : Event()
 }

 sealed class SomeClass {
    data class Pid(val id: String) : SomeClass()

    @JvmInline
    value class Name(val name: String)
 }

 val someClass: SomeClass.Name = SomeClass.Name("SomeClass")

 sealed class MyError {
    data class EventNotFound(val id: EventId) : MyError()

    data class EventPassed(val event: Event) : MyError()
 }

 data object EventId

 data class EventWithUser(val event: Event, val user: User)

 data class User(val id: Int)
 data class AnotherUser(val id: UserNotFound)

 val user: Either<UserNotFound, User> = User(1).right()

 fun Raise<UserNotFound>.user(): User = User(1)

 val Either<UserNotFound, User>.user: User?
    get() =
        when (this) {
            is Left<*> -> User(1)
            is Right<*> -> null
        }

 fun interface GetSomeModelUseCase : suspend () -> Either<List<SomeModel>, String>

 operator fun GetSomeModelUseCase.getValue(
    thisRef: Any?,
    property: KProperty<*>
 ): Either<List<SomeModel>, String> = emptyList<SomeModel>().left()

 private fun getSomeModelUseCase() = GetSomeModelUseCaseImpl()

 private fun GetSomeModelUseCase(): Either<List<SomeModel>, String> = emptyList<SomeModel>().left()

 fun GetSomeModelUseCaseImpl(): Either<List<SomeModel>, String> = emptyList<SomeModel>().left()

 GetSomeModelUseCase() = emptyList<SomeModel>().left()
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
