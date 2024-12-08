package day03

import println
import readInput

fun main() {
    fun part1(input: List<String>): Long {
        return input.flatMap(::extractMulExpressions)
            .also { println(it) }
            .fold(0L) { a, b ->
                a + evalMul(b)
            }
    }

    fun part2(input: List<String>): Long {
        val expressions = input.flatMap(::extractExpressions)
        var enabled = true
        var result = 0L
        for (expr in expressions) {
            when {
                expr == "do()" -> enabled = true
                expr == "don't()" -> enabled = false
                enabled && expr.startsWith("mul") -> result += evalMul(expr)
            }
        }
        return result
    }

    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 161L)
    val testInput2 = readInput("day03/Day03_testp2")
    check(part2(testInput2) == 48L)

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}


private const val mulPattern = "mul\\((\\d+),(\\d+)\\)"
private const val doPattern = "do\\(\\)"
private const val dontPattern = "don't\\(\\)"

fun extractMulExpressions(input: String): List<String> {
    return mulPattern.toRegex().findAll(input).map { it.value }.toList()
}

fun extractExpressions(input: String): List<String> {
    return "$mulPattern|$doPattern|$dontPattern".toRegex()
        .findAll(input)
        .map { it.value }
        .toList()
}

fun evalMul(input: String): Long {
    val (a, b) = mulPattern.toRegex().find(input)?.destructured ?: error("Invalid mul input")
    return a.toLong() * b.toLong()
}