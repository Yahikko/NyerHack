import java.io.File

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")

private val menuItems = List(menuData.size) { index ->
    val (_, name, _) = menuData[index].split(",")
    name
}

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:")
    menuItems.forEach { item -> println(item) }
    printMenu()

    val patrons: MutableSet<String> = mutableSetOf()
    while (patrons.size < 10) {
        patrons += "${firstNames.random()} ${lastNames.random()}"
    }

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random())
    }
}

// В файле tavern-menu-data необходимо добавить пробел в конце,
// иначе невозможно сделать все надписи одной ширины
private fun printMenu() {

    val menuGreetings = "*** Welcome to $TAVERN_NAME ***"
    val menuGreetingsLength = "*** Welcome to $TAVERN_NAME ***".count()

    val menuItems = List(menuData.size) { index ->
        val (_, name, price) = menuData[index].split(",")
        val difference = menuGreetingsLength - name.length - price.length + 1
        val item = name + ".".repeat(difference) + price
        item
    }

    println(menuGreetings)
    menuItems.forEach { println(it) }
}

private fun placeOrder(patronName: String, menuItemName: String) {
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    narrate("$TAVERN_MASTER hands $patronName a $menuItemName")
}