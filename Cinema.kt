package cinema

import java.util.*

const val CAPACITY = 60
const val PRICE = 10
const val LOWER_PRICE = 8

fun main() {
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val columns = readLine()!!.toInt()
    val seats = Array(rows) { Array(columns) {'S'} }

    val total = if (rows * columns <= CAPACITY)
        rows * columns * PRICE
    else
        rows / 2 * columns * PRICE + (rows - rows / 2) * columns * LOWER_PRICE
    var income = 0
    var ticket = 0
    var percentage = 0.00

    while (true) {
        when (menuChoice()) {
            1 -> showSeats(seats)
            2 -> {
                income += buyTicket(seats)
                ticket++
                percentage = ticket.toDouble() * 100 / (rows * columns)
            }
            3 -> getStatistics(ticket, percentage, income, total)
            0 -> return
        }
    }
}

fun menuChoice() : Int {
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readLine()!!.toInt()
}

fun showSeats(seats : Array<Array<Char>>) {
    print("Cinema:\n  ")
    for (i in 1..seats[0].size)
        print("$i ")
    for (i in 1..seats.size)
        print("\n$i ${seats[i - 1].joinToString(" ")}")
    println()
}

fun buyTicket(seats : Array<Array<Char>>) : Int {
    var rowSeat : Int
    var columnSeat : Int

    do {
        println("Enter a row number:")
        rowSeat = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        columnSeat = readLine()!!.toInt()

        when {
            rowSeat !in 0..seats.size || columnSeat !in 0..seats[0].size -> println("Wrong input!")
            seats[rowSeat - 1][columnSeat - 1] == 'B' -> println("That ticket has already been purchased!")
            else -> break
        }
    } while (true)

    val ticketPrice = when {
        seats.size * seats[0].size <= CAPACITY -> PRICE
        seats.size / 2 >= rowSeat -> PRICE
        else -> LOWER_PRICE
    }
    println("Ticket price: $$ticketPrice")
    seats[rowSeat - 1][columnSeat - 1] = 'B'

    return ticketPrice
}

fun getStatistics(ticket : Int, percentage : Double, income : Int, total : Int) {
    println("Number of purchased tickets: $ticket")
    println("Percentage: ${"%.2f".format(Locale("en", "US"), percentage)}%")
    println("Current income: $$income")
    println("Total income: $$total")
}