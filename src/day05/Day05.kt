package day05

import println
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader

fun main() {
    fun part1(input: PageUpdates): Int {
        return input.getOrderedUpdates().sumOf { it.split(',').middle().toInt() }
    }

    fun part2(input: PageUpdates): Int {
        return input.getFixedUpdates().sumOf { it.split(',').middle().toInt() }
    }

    val testInput = readUpdates("day05/Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readUpdates("day05/Day05")
    part1(input).println()
    part2(input).println()
}

fun readUpdates(input: String): PageUpdates {
    val orderingRules = mutableListOf<Pair<String, String>>()
    val updates = mutableListOf<String>()
    Path("src/$input.txt").bufferedReader().useLines { lines ->
        lines.forEach { line ->
            if (line.contains('|')) {
                val (a, b) = line.split("|")
                orderingRules.add(a to b)
            } else if (line.contains(',')) {
                updates.add(line)
            }
        }
    }
    return PageUpdates(orderingRules, updates)
}

data class PageUpdates(val orderingRules: List<Pair<String, String>>, val updates: List<String>) {
    fun getOrderedUpdates(): List<String> {
        return updates.filter { it.isUpdateOrdered() }
    }

    fun getFixedUpdates(): List<String> {
        return updates.filter { !it.isUpdateOrdered() }
            .map { it.reorder() }
    }

    private fun String.isUpdateOrdered(): Boolean {
        val values = this.split(",")
        return orderingRules.map { values.indexOf(it.first) to values.indexOf(it.second) }
            .filter { it.first != -1 && it.second != -1 }
            .all { it.first < it.second }
    }

    private fun String.reorder(): String {
        return this.split(',').sortedWith { a, b ->
            orderingRules.find { (it.first == a && it.second == b) || (it.first == b && it.second == a) }?.let {
                if (it.first == a) -1 else 1
            } ?: 0
        }.joinToString(",")
    }
}



fun List<String>.middle(): String {
    return this[this.lastIndex / 2]
}