fun main() {
    // Sequence - последовательность - отложенная (lazy) коллекция
    val oneThousandPrimes = generateSequence(3) { value ->
        value + 1
    }.filter { isPrime(it) }.take(10000)

    oneThousandPrimes.forEach { println(it) }
}

fun isPrime(number: Int): Boolean {
    (2..<number).map { divisor ->
        if (number % divisor == 0) {
            return false // Не простое число
        }
    }
    return true
}