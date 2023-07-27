package com.bignerdranch.nyethack

lateinit var player: Player

fun main() {

//    changeNarratorMood()

    narrate("Welcome to NyetHack!")
    val playerName = promptHeroName()
    player = Player(playerName)

    player.prophesize()

    val mortality = if (player.isImmortal) "an immortal" else "a mortal"
    val currentRoom: Room = Tavern()

    narrate("${player.name} of ${player.hometown}, ${player.title} is in ${currentRoom.description()}")
    narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    currentRoom.enterRoom()

    player.prophesize()
    player.castFireball()

    //visitTavern()
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