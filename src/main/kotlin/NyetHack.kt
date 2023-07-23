fun main() {
    narrate(
        "A hero enters the town of Kronstadt. What is their name?",
        ::makeYellow
    )
    val heroName = readlnOrNull()
    require(!heroName.isNullOrEmpty()) {
        "The hero must have a name."
    }
    changeNarratorMood()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")
}

fun createTitle(name: String): String {
    return when {
        name.count { it.lowercase() in "aeiouy" } > 4 -> "The Master of Vowels"
        name.all { it.isDigit() } -> "The Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        name.all { it.isUpperCase() } -> "Outstanding"
        name.length > 20 -> "Spacious"
        name.lowercase() == name.lowercase().reversed() -> "Palindrome Carrier"
        else -> "The Renowned Hero"
    }
}

private fun makeYellow(message: String) = "\u001B[33;1m$message\u001B[0m"