import arrow.core.Either

fun main() {
    val x = parse("123")

    val value = when (x) {
        is Either.Left -> when (x) {
            is NumberFormatException -> "Not a number!"
            else -> "Unknown error"
        }

        is Either.Right -> "Number that was parsed: ${x.value}"
    }

    println(value)
}

fun parse(s: String):
        Either<NumberFormatException, Int> =
    if (s.matches(Regex("-?[0-9]+"))) {
        Either.Right(s.toInt())
    } else {
        Either.Left(NumberFormatException("$s is not a valid integer."))
    }
