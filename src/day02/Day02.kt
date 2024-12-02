package day02

import println
import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.count { report ->
            report.split("\\s+".toRegex()).isReportSafe()
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { report ->
            report.split("\\s+".toRegex()).isReportSafeTolerating1()
        }
    }

    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}

private fun List<String>.isReportSafe(): Boolean {
    val maxVariance = 1..3

    var descending: Boolean? = null
    this.zipWithNext { a, b ->
        val diff = a.toInt() - b.toInt()
        val isVarianceInRange = abs(diff) in maxVariance

        val currentDescending = diff < 0
        val hasChangedDirection = (descending != null && currentDescending != descending)
        descending = currentDescending

        if (!isVarianceInRange || hasChangedDirection) {
            return false
        }
    }
    return true
}

private fun List<String>.isReportSafeTolerating1(): Boolean {
    if (!isReportSafe()) {
        return this.indices.any { isReportSafeIgnoringIndex(it) }
    }
    return true
}

private fun List<String>.isReportSafeIgnoringIndex(ignoreIndex: Int): Boolean {
    return this.filterIndexed { index, _ -> index != ignoreIndex }.isReportSafe()
}
