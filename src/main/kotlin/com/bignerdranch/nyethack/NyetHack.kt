package com.bignerdranch.nyethack

val player = Player()

fun main() {

//    changeNarratorMood()
    narrate("${player.name}, ${player.title}, heads to the town square")

    visitTavern()
    player.castFireball()
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