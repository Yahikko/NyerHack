package com.bignerdranch.nyethack

import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.exitProcess

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
        listOf(
            TownSquare(),
            Tavern(),
            MonsterRoom("Back Room", Draugr())
        ),
        listOf(
            MonsterRoom("A Long Corridor", Goblin()),
            Room("A Generic Room"),
            MonsterRoom("A Wierd Room", mobSpawn())
        ),
        listOf(
            MonsterRoom("The Dungeon", mobSpawn()),
            Room("Room With Small Booty")
        ),
        listOf(
            MonsterRoom("The Dragon's Lair", Dragon()),
            Room("Room With Huge Booty"),
            MonsterRoom("Abandoned Room", mobSpawn())
        )
    )

    private var currentPosition = Coordinate(0, 0)
    private var currentRoom: Room = worldMap[0][0]

    init {
        narrate("Welcome, adventurer")
        val mortality = if (player.isImmortal) "an immortal" else "a mortal"
        narrate("${player.name}, $mortality, has ${player.healthPoints} health points")
    }

    private fun mobSpawn(): Monster {
        return when (Random.nextInt(100)) {
            in 0..45 -> Goblin()
            in 46..80 -> Draugr()
            in 81..97 -> Werewolf()
            else -> Dragon()
        }
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

    fun fight() {
        val monsterRoom = currentRoom as? MonsterRoom
        val currentMonster = monsterRoom?.monster
        if (currentMonster == null) {
            narrate("There's nothing to fight here")
            return
        }
        while (player.healthPoints > 0 && currentMonster.healthPoints > 0) {
            player.attack(currentMonster)
            if (currentMonster.healthPoints > 0) {
                currentMonster.attack(player)
            }
            Thread.sleep(1000)
        }
        if (player.healthPoints <= 0) {
            narrate("You have been defeated! Thanks for playing")
            exitProcess(0)
        } else {
            narrate("${currentMonster.name} has been defeated")
            monsterRoom.monster = null
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

            "map" -> {
                val array = listOf(
                    arrayOf("O", "O", "O", "O"),
                    arrayOf("O", "O", "O", "O"),
                    arrayOf("O", "O", "O", "O"),
                )
                array[currentPosition.x][currentPosition.y] = "X"
                println(
                    """
                    ${array[0][0]} ${array[1][0]} ${array[2][0]}
                    ${array[0][1]} ${array[1][1]} ${array[2][1]}
                    ${array[0][2]} ${array[1][2]}
                    ${array[0][3]} ${array[1][3]} ${array[2][3]}
                """.trimIndent()
                )
            }

            "fight" -> fight()

            else -> narrate("I'm not sure what you're trying to do")
        }
    }
}