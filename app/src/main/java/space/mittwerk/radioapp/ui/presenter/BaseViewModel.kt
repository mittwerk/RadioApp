package space.mittwerk.radioapp.ui.presenter
//
// import arrow.atomic.AtomicInt
// import arrow.core.Either
// import arrow.core.EmptyValue.fold
// import arrow.core.MemoizedDeepRecursiveFunction
// import arrow.core.NonEmptyList
// import arrow.core.left
// import arrow.core.leftNel
// import arrow.core.mapOrAccumulate
// import arrow.core.memoize
// import arrow.core.nonEmptyListOf
// import arrow.core.partially1
// import arrow.core.raise.Raise
// import arrow.core.raise.either
// import arrow.core.raise.ensure
// import arrow.core.raise.ensureNotNull
// import arrow.core.raise.withError
// import arrow.core.right
// import arrow.eval.Eval
// import arrow.optics.Every
// import arrow.optics.Lens
// import arrow.optics.optics
// import arrow.resilience.Saga
// import arrow.resilience.saga
//
// class BaseViewModel
//
// data class Author(val name: String) {
//    companion object {
//        operator fun invoke(name: String): Either<EmptyAuthorName, Author> =
//            either {
//                ensure(name.isNotEmpty()) { EmptyAuthorName }
//                Author(name)
//            }
//    }
// }
//
// fun even(n: Int): Eval<Boolean> =
//    Eval.always { n == 0 }.flatMap {
//        if (it) {
//            Eval.now(true)
//        } else {
//            odd(n - 1)
//        }
//    }
//
// fun odd(n: Int): Eval<Boolean> =
//    Eval.always { n == 0 }.flatMap {
//        if (it) {
//            Eval.now(false)
//        } else {
//            even(n - 1)
//        }
//    }
//
// object EmptyAuthorName
//
// data class AuthorName(val name: String)
//
// data class AuthorList(val authors: NonEmptyList<Author>)
//
// fun validateAuthorList(authorList: AuthorList): Either<EmptyAuthorName, AuthorList> =
//    either {
//        ensure(authorList.authors.isNotEmpty()) { EmptyAuthorName }
//        authorList
//    }
//
// data class Book(val title: String, val authors: NonEmptyList<Author>)
//
// data class BookList(val books: NonEmptyList<Book>)
//
// fun dance(
//    rounds: Int,
//    person: String,
// ) {
//    TODO()
// }
//
// fun List<String>.everybodyDancesTwo() = forEach { dance(2, it) }
//
// fun List<String>.everybodyDancesTwopartially1() = forEach(::dance.partially1(2))
//
// @optics
// sealed interface BookValidationError
//
// val personCity: Lens<Person, String> =
//    Person.address compose Address.city
//
// @optics
// data class Db(val cities: Map<String, CCity>) {
//    companion object
// }
//
// @optics
// data class CCity(val name: String, val country: String) {
//    companion object
// }
//
// val db =
//    Db(
//        mapOf(
//            "Alejandro" to CCity("Hilversum", "Netherlands"),
//            "Ambrosio" to CCity("Ciudad Real", "Spain"),
//        ),
//    )
//
// @optics
// data class Person(val name: String, val age: Int, val address: Address) {
//    companion object {
//        val name: Lens<Person, String> = TODO()
//        val age: Lens<Person, Int> = TODO()
//        val address: Lens<Person, Address> = TODO()
//        const val INITIAL_VALUE = 1
//    }
//
//    fun List<Person>.happyBirthdayMap(): List<Person> = map { Person.age.modify(it) { age -> age + 1 } }
//
//    fun List<Person>.happyBirthdayOptics(): List<Person> = Every.list<Person>().age.modify(this) { age -> age + 1 }
//
//    fun Person.happyBirthdayFriends(): Person = copy(friends = friends.map { friend -> friend.copy(age = friend.age + 1) }, age = age + 1)
//
//    fun Person.happyBirthdayFriendsOptics(): Person = Person.friends.every(Every.list()).age.modify(this) { it + 1 }
//
//    @optics
//    sealed interface User {
//        companion object
//    }
//
//    @optics
//    data class Person(val name: String, val age: Int) : User {
//        companion object
//    }
//
//    @optics
//    data class Company(val name: String, val country: String) : User {
//        companion object
//    }
//
//    val fibonacciWorker =
//        MemoizedDeepRecursiveFunction<Int, Int> { n ->
//            when (n) {
//                0 -> 0
//                1 -> 1
//                else -> callRecursive(n - 1) + callRecursive(n - 2)
//            }
//        }
//
//    val fibonacci = ::fibonacciWorker.memoize()
//
//    fun Person.happyBirthday(): Person = Person(name, age + 1)
//
//    private fun expensive(x: Int): Int {
//        // fake it by sleeping the thread
//        Thread.sleep(x * 100L)
//        return x
//    }
//
//    val memoizedExpensive = ::expensive.memoize()
//
//    @optics
//    data class Berson(val name: String, val age: Int, val address: Address) {
//        companion object {
//            operator fun invoke(
//                name: String,
//                age: Int,
//                address: Address,
//            ): Either<EmptyAuthorName, Berson> =
//                either {
//                    ensure(name.isNotEmpty()) { EmptyAuthorName }
//                    Berson(name, age, address)
//                }
//        }
//    }
//
//    fun Berson.capitalizeCountryModify(): Berson = Berson.address.modify { it.copy(country = it.country.capitalize()) }
//
//    val PROBLEM = Throwable("problem detected!")
//
//    // describe the transaction
//    val transaction: Saga<Int> =
//        saga {
//            saga({
//                // action to perform
//                Counter.increment()
//            }) {
//                // inverse action for rolling back
//                Counter.decrement()
//            }
//            saga({
//                throw PROBLEM
//            }) {}
//            // final value of the saga
//            Counter.value.get()
//        }
//
//    object Counter {
//        val value = AtomicInt(INITIAL_VALUE)
//
//        fun increment() {
//            value.incrementAndGet()
//        }
//
//        fun decrement() {
//            value.decrementAndGet()
//        }
//    }
//
//    fun validateAuthorName(name: String): Either<EmptyAuthorName, AuthorName> =
//        either {
//            ensure(name.isNotEmpty()) { EmptyAuthorName }
//            AuthorName(name)
//        }
//
//    data object EmptyBookList : BookValidationError
//
//    data class BookValidationErrorList(val errors: NonEmptyList<BookValidationError>)
//
//    fun validateBookList(bookList: BookList): Either<BookValidationError, BookList> =
//        either {
//            ensure(bookList.books.isNotEmpty()) { EmptyBookList }
//            bookList
//        }
//
//    fun process(user: AnotherUser?): Either<MuserNotFound, UserNotFound> =
//        either {
//            ensureNotNull(user) { MuserNotFound("Cannot process null user") }
//            user.id // smart-casted to non-null
//        }
//
//    data class MuserNotFound(val message: String)
//
//    val quser: Either<UserNotFound, User> = User(1).right()
//
//    fun Raise<UserNotFound>.quser(): User = User(1)
//
//    val error: Either<UserNotFound, User> = UserNotFound.left()
//
//    fun Raise<UserNotFound>.error(): AnotherUser = AnotherUser(UserNotFound)
//
//    data object UserNotFound
//
//    fun example() {
//        process(null) shouldBe
//            MuserNotFound("Cannot process null user")
//                .left()
//
//        fold(
//            { process(User(1)) },
//            { _: MuserNotFound -> fail("No logical failure occurred!") },
//            { i: Long -> i shouldBe 1L },
//        )
//    }
//
//    data class UserAlreadyExists(val username: String, val email: String)
//
//    data class NotEven(val i: Int)
//
//    private fun Raise<NotEven>.isEven(i: Int): Int = i.also { ensure(i % 2 == 0) { NotEven(i) } }
//
//    private fun isEven2(i: Int): Either<NotEven, Int> = either { isEven(i) }
//
//    val errors = nonEmptyListOf(NotEven(1), NotEven(3), NotEven(5), NotEven(7), NotEven(9)).left()
//
//    fun examples() {
//        (1..10).mapOrAccumulate { isEven(it) }
//        (1..10).mapOrAccumulate { isEven2(it).bind() }
//    }
//
//    data class qMyError(val message: String)
//
//    fun Raise<qMyError>.is1Even(i: Int): Int = ensureNotNull(i.takeIf { i % 2 == 0 }) { qMyError("$i is not even") }
//
//    fun isE1ven2(i: Int): Either<qMyError, Int> = either { is1Even(i) }
//
//    operator fun qMyError.plus(second: qMyError): qMyError = qMyError(message + ", ${second.message}")
//
//    // this is the type we want to construct
//    @JvmInline
//    value class Age(val age: Int)
//
//    // these are the potential problems
//    sealed interface AgeProblem {
//        data object InvalidAge : AgeProblem
//
//        data object NotLegalAdult : AgeProblem
//    }
//
//    // validation returns either problems or the constructed value
//    fun validAdult(age: Int): Either<AgeProblem, Age> =
//        when {
//            age < 0 -> AgeProblem.InvalidAge.left()
//            age < 18 -> AgeProblem.NotLegalAdult.left()
//            else -> Age(age).right()
//        }
//
//    fun <E, A> eitherNel(e: E): EitherNel<E, A> = e.leftNel()
//
//    sealed interface UserProblem {
//        data object EmptyName : UserProblem
//
//        data class NegativeAge(val age: Int) : UserProblem
//    }
//
//    private val stringError: Either<String, Boolean> = "problem".left()
//
//    val intError: Either<Int, Boolean> =
//        either {
//            // transform error String -> Int
//            withError({ it.length }) {
//                stringError.bind()
//            }
//        }
//
//    data object EmptyTitle : BookValidationError
//
//    data object EmptyAuthorName : BookValidationError
//
//    data object NoAuthors : BookValidationError
//
//    data class EmptyAuthor(val index: Int) : BookValidationError
//
//    fun validateBook(book: Book): Either<BookValidationError, Book> =
//        either {
//            ensure(book.title.isNotEmpty()) { EmptyTitle }
//            ensure(book.authors.isNotEmpty()) { NoAuthors }
//            ensure(book.authors.all { it.name.isNotEmpty() }) { EmptyAuthor(0) }
//            book
//        }
// }
//
// typealias EitherNel<E, A> = Either<NonEmptyList<E>, A>
