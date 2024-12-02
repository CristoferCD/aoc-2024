package day01

import println
import kotlin.io.path.Path
import kotlin.io.path.bufferedReader
import kotlin.math.abs

fun main() {
    fun part1(input: Pair<List<Int>, List<Int>>): Int {
        val (left ,right) = input
        return left.sorted().zip(right.sorted()).sumOf { (a, b) ->
            abs(a - b)
        }
    }

    fun part2(input: Pair<List<Int>, List<Int>>): Int {
        val (left ,right) = input
        return left.sumOf { a -> a * right.count { b -> b == a } }
    }

    val testInput = getListsFromInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the Day01_test.txt from the `src/Day01.txt` file.
    val input = getListsFromInput("Day01")
    part1(input).println()
    part2(input).println()
}

fun getListsFromInput(file: String) : Pair<List<Int>, List<Int>> {
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    Path("src/day01/$file.txt").bufferedReader().useLines { lines ->
        lines.forEach {
            val split = getValuesFromLine(it)
            left.add(split.first)
            right.add(split.second)
        }
    }
    return Pair(left, right)
}

fun getValuesFromLine(line: String): Pair<Int, Int> {
    val separator = "\\s+".toRegex()
    val split = line.split(separator)
    return Pair(split[0].toInt(), split[1].toInt())
}
