fun main() {
    narrate(
        message = "A hero enters the town of Kronstadt. What is their name?"
    ) { message -> "\u001b[36;1m$message\u001b[0m" }
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
        else -> "The Renowned Hero"
    }
}