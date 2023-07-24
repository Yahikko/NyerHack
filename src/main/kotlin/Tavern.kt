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
    val menuGreetingsLength = menuGreetings.count()
    val menuList = mutableListOf<String>()

    menuData.forEach { index1 ->
        val (type1, _, _) = index1.split(",")
        val placeForType = menuGreetingsLength / 2 - type1.count() / 2 - 3
        val typeToMenu = "${" ".repeat(placeForType)}~[$type1]~"

        if (!menuList.contains(typeToMenu)) {
            menuList += typeToMenu
            menuData.forEach { index2 ->
                val (type2, name, price) = index2.split(",")
                if (type1 == type2) {
                    val difference = menuGreetingsLength - name.length - price.length + 1
                    val item = name + ".".repeat(difference) + price
                    menuList += item
                }
            }
        }
    }
    println(menuGreetings)
    menuList.forEach { println(it) }
}

private fun placeOrder(patronName: String, menuItemName: String) {
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    narrate("$TAVERN_MASTER hands $patronName a $menuItemName")
}