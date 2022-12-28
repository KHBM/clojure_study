package com.study.fox

import io.kotest.core.spec.style.DescribeSpec
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName

@DisplayName("optionals class test")
class OptionalsTest : DescribeSpec() {

    init {
        describe("equals test") {
            context("input value 'a'") {
                it("equals expected value") {
                    val expected = "a"
                    val input = Optionals.of("a")
                    Assertions.assertEquals(expected, input.get())
                }
            }
        }

        describe("filter test") {
            context("input value 'a'") {
                it("filtered value 'b' is null") {
                    val input = Optionals.of("b")
                    val actual = input.filter { it == "a" }?.get()
                    println(actual)
                    Assertions.assertNull(actual)
                }
            }
        }

        describe("map test") {
            context("input value 'a'") {
                it("mapped value plus 'b' then 'ab' is equal") {
                    val expected = "ab"
                    val input = Optionals.of("a")
                    val actual = input.map { it + "b" }.get()
                    Assertions.assertEquals(expected, actual)
                }
            }
        }

        describe("flatMap test") {
            context("input value '123'") {
                it("new value Optionals('543') then Optionals('123543')") {
                    val expected = Optionals.of("123543")
                    val input = Optionals.of("123")
                    val actual = input.flatMap {
                        Optionals.of(it + "543")
                    }

                    Assertions.assertEquals(expected.get(), actual.get())
                }
            }
        }
    }
}
