package com.study.fox

import io.kotest.core.spec.style.DescribeSpec

/**
 *
 * @author foxrain user has created this file at 2022/12/28, 19:38 on 2022
 **/
class OptionalFoxTest: DescribeSpec() {

    init {

        describe("orElse 의 전달된 값이 NULl 일 때 리턴") {
            context("when option value is null") {
                it("optional.orElse should return its parameter") {
                    val o : Optional_fox<Int> = Optional_fox.of(null)
                    val actual = o.map { it + 1 }
                        .map { it + 2 }
                        .orElse(400)
                    Assertions.assertEquals(400, actual)
                }
            }
        }
        describe("orElse 의 전달된 값이 Non-NULl 일 때 리턴") {
            context("when option value is null") {
                it("optional.orElse should return its parameter") {
                    val o : Optional_fox<Int> = Optional_fox.of(400)
                    val actual = o.map { it + 1 }
                        .map { it + 2 }
                        .orElse(400)
                    Assertions.assertEquals(403, actual)
                }
            }
        }

    }
}