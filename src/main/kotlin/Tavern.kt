import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData = File("data/tavern-menu-data.txt")
    .readText()
    .split("\n")
    .map { it.split(",") }

private val menuItems: List<String> = menuData.map { (_, name, _) -> name }

private val menuItemPrices = menuData.associate { (_, name, price) -> name to price.toDouble() }

private val menuItemTypes = menuData.associate { (type, name, _) -> name to type }

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:")
    narrate(menuItems.joinToString())

    val patrons: MutableSet<String> = firstNames
        .shuffled()
        .zip(lastNames.shuffled()) { firstName, lastName ->
            "$firstName $lastName"
        }.toMutableSet()

    val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.5,
        *patrons.map { it to 6.0 }.toTypedArray()
    )

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random(), patronGold)
    }
    displayPatronBalances(patronGold)

    patrons.filter { patron ->
        patronGold.getOrDefault(patron, 0.0) < 4.0
    }.also { departingPatrons ->
        patrons -= departingPatrons.toSet()
        patronGold -= departingPatrons.toSet()
    }.forEach { patron ->
        narrate("$heroName sees $patron departing the tavern")
    }
    narrate("There are still some patrons in the tavern")
    narrate(patrons.joinToString())
}

private fun getFavoriteMenuItems(patron: String): List<String> {
    return when (patron) {
        "Alex Ironfoot" -> menuItems.filter { menuItem ->
            menuItemTypes[menuItem]?.contains("dessert") == true
        }

        else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}

private fun placeOrder(
    patronName: String,
    menuItemName: String,
    patronGold: MutableMap<String, Double>
) {
    val itemPrice = menuItemPrices.getValue(menuItemName)
    val action = when (menuItemTypes[menuItemName]) {
        "shandy", "elixir" -> "pours"
        "meal" -> "serves"
        else -> "hands"
    }

    narrate("$patronName speaks with $TAVERN_MASTER to place an order")

    if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_MASTER $itemPrice gold")
        patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
    }
}

private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach { (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}