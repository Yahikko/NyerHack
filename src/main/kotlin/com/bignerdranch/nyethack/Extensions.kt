package com.bignerdranch.nyethack

fun String.addEnthusiasm(enthusiasmLevel: Int = 1) = this + "!".repeat(enthusiasmLevel)

val String.numVowels
    get() = count { it.lowercase() in "aeiouy" }

operator fun List<List<Room>>.get(coordinate: Coordinate) =
    getOrNull(coordinate.y)?.getOrNull(coordinate.x)

infix fun Coordinate.move(direction: Direction) =
    direction.updateCoordinate(this)

fun Room?.orEmptyRoom(name: String = "the middle of nowhere"): Room = this ?: Room(name)

inline fun MonsterRoom.configurePitGoblin(
    block: MonsterRoom.(Goblin) -> Goblin
): MonsterRoom {
    val goblin = block(Goblin("Pit Goblin", description = "An Evil Pit Goblin"))
    monster = goblin
    return this
}

fun String.frame(padding: Int, formatChar: String = "*"): String {
    val middle = formatChar
        .padEnd(padding)
        .plus(this)
        .plus(formatChar.padStart(padding))
    val end = (middle.indices).joinToString("") { formatChar }
    return "$end\n$middle\n$end"
}