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

private val menuItems = List(menuData.size) { index ->
    val (_, name, _) = menuData[index].split(",")
    name
}

private val menuItemPrices: Map<String, Double> = List(menuData.size) { index ->
    val (_, name, price) = menuData[index].split(",")
    name to price.toDouble()
}.toMap()

private val menuItemTypes: Map<String, String> = List(menuData.size) { index ->
    val (type, name, _) = menuData[index].split(",")
    name to type
}.toMap()

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:")
    narrate(menuItems.joinToString())

    val patrons: MutableSet<String> = mutableSetOf()
    val patronGold = mutableMapOf(
        TAVERN_MASTER.to(86.00),
        heroName to 4.5,
//        Pair("Unknown", 8.0)
    )

    while (patrons.size < 5) {
        val patronName = "${firstNames.random()} ${lastNames.random()}"
        patrons += patronName
        patronGold += patronName to Random.nextDouble(5.0, 30.0)
    }

//  println(patronGold)
//  println(patronGold[heroName])
//  println(patronGold.getOrDefault(TAVERN_MASTER, 0.0))
//  println(patronGold.getOrElse("Eli") { 0.0 })

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), patronGold)
    }
    displayPatronBalances(patronGold)
}

private fun placeOrder(
    patronName: String,
    patronGold: MutableMap<String, Double>
) {
    val itemsCount = Random.nextInt(1..3)
    val itemsToOrder = mutableMapOf<String, Double>()
    var itemsPrice = 0.0
    var items = ""

    repeat(itemsCount) {
        val i = menuItems[Random.nextInt(menuItems.size)]
        itemsToOrder += i to menuItemPrices.getValue(i)
        itemsPrice += menuItemPrices.getValue(i)
    }

    narrate("$patronName speaks with $TAVERN_MASTER to place an order")

    if (patronGold.getOrDefault(patronName, 0.0) >= itemsPrice) {

        itemsToOrder.forEach{ i ->
            items += "${i.key}, "
        }
        items = items.dropLast(2)

        narrate("$TAVERN_MASTER hands $patronName $items")
        narrate("$patronName pays $TAVERN_MASTER $itemsPrice gold")
        patronGold[patronName] = patronGold.getValue(patronName) - itemsPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemsPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for a this order\"")
    }
}

private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach { (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}