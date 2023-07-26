import kotlin.system.measureNanoTime

fun main() {
    // Sequence - последовательность - отложенная (lazy) коллекция
    val sequenceInNanos = measureNanoTime {
        val oneThousandPrimes = generateSequence(3) { value ->
            value + 1
        }.filter { isPrime(it) }.take(10000)
    }
    val listInNanos = measureNanoTime {
        val listOfPrimes = (1..7919)
            .toList()
            .filter { isPrime(it) }
            .take(1000)
    }

    println("Sequence completed in $sequenceInNanos ns")
    println("List completed in $listInNanos ns")
}

fun isPrime(number: Int): Boolean {
    (2..<number).map { divisor ->
        if (number % divisor == 0) {
            return false // Не простое число
        }
    }
    return true
}