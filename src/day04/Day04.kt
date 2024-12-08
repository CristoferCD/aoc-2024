package day04

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input[0].indices.flatMap { x -> input.indices.map { y -> Point(x, y) } }
            .filter { input.characterAt(it) == 'X' }
            .sumOf { it.countWordAround(input, "XMAS") }
    }

    fun part2(input: List<String>): Int {
        return input[0].indices.flatMap { x -> input.indices.map { y -> Point(x, y) } }
            .count { input.isXMasAt(it) }
    }

    val testInput = readInput("day04/Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("day04/Day04")
    part1(input).println()
    part2(input).println()
}

class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    fun countWordAround(input: List<String>, word: String): Int {
        return listOf(
            Point(-1, -1),
            Point(0, -1),
            Point(1, -1),
            Point(-1, 0),
            Point(1, 0),
            Point(-1, 1),
            Point(0, 1),
            Point(1, 1),
        ).count { input.findWord(word, this, it) }
    }

}

fun Pair<Int, Int>.toPoint() = Point(first, second)

fun List<String>.characterAt(point: Point): Char? {
    return this.getOrNull(point.y)?.let { return it.getOrNull(point.x) }
}

fun List<String>.findWord(word: String, origin: Point, direction: Point): Boolean {
    var coordinates = origin
    word.forEach { char ->
        if (this.characterAt(coordinates) != char) {
            return false
        }
        coordinates += direction
    }
    return true
}

fun List<String>.isXMasAt(point: Point): Boolean {
    val topLeft = this.characterAt(point + Point(-1, -1))
    val topRight = this.characterAt(point + Point(1, -1))
    val bottomLeft = this.characterAt(point + Point(-1, 1))
    val bottomRight = this.characterAt(point + Point(1, 1))
    val middle = this.characterAt(point)

    val diagonal1 = "" + topLeft + middle + bottomRight
    val diagonal2 = "" + bottomLeft + middle + topRight

    return (diagonal1 == "MAS" || diagonal1 == "SAM") &&
            (diagonal2 == "MAS" || diagonal2 == "SAM")
}