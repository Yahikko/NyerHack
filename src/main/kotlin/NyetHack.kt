var heroName: String = ""

fun main() {

    heroName = promptHeroName()
//    changeNarratorMood()
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")

    visitTavern()
}

fun createTitle(name: String): String {
    return when {
        name.count { it.lowercase() in "aeiouy" } > 4 -> "The Master of Vowels"
        name.all { it.isDigit() } -> "The Identifiable"
        name.none { it.isLetter() } -> "The Witness Protection Member"
        else -> "The Renowned Hero"
    }
}

private fun promptHeroName(): String {
    narrate("A hero enters the town of Kronstadt. What is their name?") { message ->
        "\u001b[36;1m$message\u001b[0m"
    }
    /*val input = readlnOrNull()
    require(!heroName.isNullOrEmpty()) {
        "The hero must have a name."
    }
    return input*/
    println("Madrigal")
    return "Madrigal"
}