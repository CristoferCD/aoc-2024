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
    val maxVariance = 1 to 3

    var descending: Boolean? = null
    for ((index, value) in withIndex()) {
        val nextValue = (if (index + 1 < this.size) this[index + 1] else null) ?: break

        val diff = value.toInt() - nextValue.toInt()
        val isVarianceInRange = (abs(diff) >= maxVariance.first && abs(diff) <= maxVariance.second)

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
        return (0..this.size).any { isReportSafeIgnoringIndex(it) }
    }
    return true
}

private fun List<String>.isReportSafeIgnoringIndex(ignoreIndex: Int): Boolean {
    val newList = mutableListOf<String>()
    this.forEachIndexed { index, it ->
        if (index != ignoreIndex) {
            newList.add(it)
        }
    }
    return newList.isReportSafe()
}
