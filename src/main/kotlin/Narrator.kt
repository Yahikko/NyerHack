import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

inline fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) }
) {
    println(modifier(message))
}

fun changeNarratorMood() {
    val mood: String
    val modifier: (String) -> String
    when (Random.nextInt(1..8)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numExclamationPoints = 3
                message.uppercase() + "!".repeat(numExclamationPoints)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                val numExclamationPoints = 3
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message -> "$message?" }
        }

        4 -> {
            var narrationsGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationsGiven++
                "$message.\n(I have narrated $narrationsGiven things)"
            }
        }

        5 -> {
            mood = "lazy"
            modifier = { message ->
                "${message.take(message.length / 2)}..."
            }
        }

        6 -> {
            mood = "mysterious (leet cipher)"
            modifier = { message ->
                message.replace("[Ll]".toRegex()) { "1" }
                    .replace("[Ee]".toRegex()) { "3" }
                    .replace("[Tt]".toRegex()) { "7" }
            }
        }

        7 -> {
            mood = "poetic"
            modifier = { message ->
                message.replace(" ".toRegex()) {" ".repeat(Random.nextInt(2..5))}
            }
        }

        else -> {
            mood = "professional"
            modifier = { message -> "$message." }
        }
    }

    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}