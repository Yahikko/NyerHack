package com.bignerdranch.nyethack

lateinit var player: Player

fun main() {

    narrate("Welcome to NyetHack!")
//    changeNarratorMood()
    val playerName = promptHeroName()
    player = Player(playerName)
    Game.play()
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

object Game {

    private val worldMap = listOf(
        listOf(TownSquare(), Tavern(), Room("Back Room")),
        listOf(Room("A Long Corridor"), Room("A Generic Room")),
        listOf(Room("The Dungeon"))
    )

    private var currentPosition = Coordinate(0, 0)
    private var currentRoom: Room = worldMap[0][0]

    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal" else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }

    fun play() {
        while (true) {
            narrate("${player.name} of ${player.hometown}, ${player.title} is in ${currentRoom.description()}")
            currentRoom.enterRoom()

            print("> Enter your command: ")
            GameInput(readln()).processCommand()
        }
    }

    fun move(direction: Direction) {
        val newPosition = direction.updateCoordinate(currentPosition)
        val newRoom = worldMap.getOrNull(newPosition.y)?.getOrNull(newPosition.x)

        if (newRoom != null) {
            narrate("The hero moves ${direction.name}")
            currentPosition = newPosition
            currentRoom = newRoom
        } else {
            narrate("You cannot move ${direction.name}")
        }
    }

    private class GameInput(arg: String?) {

        private val input = arg ?: ""
        val command = input.split(" ")[0]
        val argument = input.split(" ").getOrElse(1) { "" }

        fun processCommand() = when (command.lowercase()) {
            "move" -> {
                val direction = Direction.entries.firstOrNull() {
                    it.name.equals(argument, ignoreCase = true)
                }
                if (direction != null) {
                    move(direction)
                } else {
                    narrate("I don't know what direction that is")
                }
            }

            else -> narrate("I'm not sure what you're trying to do")
        }
    }
}