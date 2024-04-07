package space.mittwerk.radioapp.dispatcher

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ContextProvider {
    val main: CoroutineContext
    val mainImmediate: CoroutineContext
    val io: CoroutineContext
    val default: CoroutineContext
    val unconfined: CoroutineContext
}

class ContextProviderImpl : ContextProvider {
    override val main: CoroutineContext by lazy { Dispatchers.Main }
    override val mainImmediate: CoroutineContext by lazy { Dispatchers.Main.immediate }
    override val io: CoroutineContext by lazy { Dispatchers.IO }
    override val default: CoroutineContext by lazy { Dispatchers.Default }
    override val unconfined: CoroutineContext by lazy { Dispatchers.Unconfined }
}
