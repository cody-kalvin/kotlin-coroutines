import kotlinx.coroutines.*
import java.lang.IndexOutOfBoundsException

fun main() {
    runBlocking {
        val myHandler = CoroutineExceptionHandler { _, throwable ->
            println("Exception handled: ${throwable.localizedMessage}")
        }

        val job = GlobalScope.launch(myHandler) {
            println("Throwing exception from job")
            throw IndexOutOfBoundsException("exception in coroutine")
        }
        job.join()

        val deferred = GlobalScope.async {
            println("Throwing exception from async")
            throw ArithmeticException("exception from async")
        }

        try {
            @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
            deferred.await()
        } catch (e: java.lang.ArithmeticException) {
            println("Caught ArithmeticException ${e.localizedMessage}")
        }
    }
}