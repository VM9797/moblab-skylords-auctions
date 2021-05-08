package hu.vm.bme.skylordsauctions.util

import kotlinx.coroutines.*

typealias Callback<T> = (T) -> Unit

fun onMain(block: suspend CoroutineScope.() -> Unit): Job = GlobalScope.launch(Dispatchers.Main, block = block)
fun onIO(block: suspend CoroutineScope.() -> Unit): Job = GlobalScope.launch(Dispatchers.IO, block = block)