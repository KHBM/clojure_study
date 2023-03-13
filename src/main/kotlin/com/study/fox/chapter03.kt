package com.study.fox

/**
 *
 * @author foxrain user has created this file at 2023/03/07, 2:35 on 2023
 **/
class chapter03 {

    tailrec fun tailrecMaximum(
        items: List<Int>,
        acc: Int = Int.MIN_VALUE,
    ): Int = when {
        items.isEmpty() -> error("Empty list")
        items.size == 1 -> {
            println("head : ${items[0]}, maxVal : ${acc}")
            items[0]
        }
        else -> {
            val head = items.first()
            val maxValue = if (head > acc) head else acc
            println("head : $head, maxVal : $maxValue")
            tailrecMaximum(items.drop(1), maxValue)
        }
    }

}

fun main() {
    val tailrecMaximum = chapter03().tailrecMaximum(
        listOf(1, 10)
    )

//    println(tailrecMaximum)

    println(solution3_2()(3.0, 3))
    println(solution3_3()(5))
    println(solution3_4()(5))
    println(solution3_5()(3, 5))
    println(solution3_6()(15, listOf(3,3,3,3,3,3,15)))
    println(solution3_7()(5, repeat(3)))
}

fun solution3_1() {

    //정의역 양의 정수
    //n=0 일 때 1을 리턴하기에 참이다
    //n=1 일 때 1을 리턴하기에 참이다
    //임의의 양의 정수 k 에 대해 n < k 0 ~ n 까지의 곱을 올바르게 계산한다고 가정한다
    //n=k 인 경우, func(k-1)을 호출할 때 위 가정에 의해 곱이 올바르다. 이곳에 n 을 곱하여 반환하므로 결과적으로 곱이 올발게 된다.

}

fun solution3_2(): (Double, Int)->Double {

    fun power(x: Double, n: Int):Double {
        return when {
            n  < 0 -> throw java.lang.RuntimeException("0 이상만 지원합니다.")
            n == 1 -> 1.0
            else -> x * power(x, n-1)
        }
    }
    println("solution3-2")
    return ::power
}

fun solution3_3(): (Int) -> Int {

    fun fac(n: Int): Int {
        return when {
            n < 0 -> throw Exception("minus is not allowd")
            n == 0 -> 1
            else -> n * fac(n-1)
        }
    }
    println("solution3-3")
    return ::fac
}

fun solution3_4(): (Int) -> String {
    fun toBinary(n: Int): String {
        return when {
            n / 2 <= 0 -> "${n % 2}"
            else -> toBinary(n/2) + "${n%2}"
        }
    }

    println("solution3-4")
    return ::toBinary
}

fun solution3_5(): (Int, Int) -> List<Int> {

    fun replicate(n: Int, element: Int): List<Int> {
        return when {
            n == 0 -> listOf()
            n == 1 -> listOf(element)
            else -> listOf(element) + replicate(n-1, element)
        }
    }
    println("solution3-5")
    return ::replicate
}

fun solution3_6(): (Int, List<Int>) -> Boolean {
    fun elem(num: Int, list: List<Int>): Boolean {
        return when {
            list.isEmpty() -> false
            list.size == 1 -> list.first() == num
            else -> list.first() == num || elem(num, list.drop(1))
        }
    }
    println("solution3-6")
    return ::elem
}

fun solution3_7(): (Int, Sequence<Int>) -> List<Int> {

    fun takeSequence(n: Int, sequence: Sequence<Int>): List<Int> {
        return when {
            n <= 0 -> listOf()
            sequence.none() -> listOf()
            else -> listOf(sequence.first()) + takeSequence(n-1, sequence.drop(1))
        }
    }
    println("soultion3-7")
    return ::takeSequence
}
fun take(n: Int, list: List<Int>): List<Int> = when {
    n <= 0 -> listOf()
    list.isEmpty() -> listOf()
    else -> listOf(list.first()) + take(n-1, list.drop(1))
}

fun repeat(n: Int): Sequence<Int> = sequenceOf(n) + { repeat(n) }

operator fun <T> Sequence<T>.plus(other: () -> Sequence<T>) = object : Sequence<T> {
    private val thisIterator: Iterator<T> by lazy { this@plus.iterator() }
    private val otherIterator: Iterator<T> by lazy { other().iterator() }
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        override fun next(): T =
            if (thisIterator.hasNext())
                thisIterator.next()
            else
                otherIterator.next()

        override fun hasNext(): Boolean = thisIterator.hasNext() || otherIterator.hasNext()
    }
}