package com.study.fox

import io.kotest.core.spec.style.DescribeSpec
import org.assertj.core.api.Assertions

/**
 *
 * @author foxrain user has created this file at 2023/01/02, 23:02 on 2023
 **/
class FoxFlux: DescribeSpec() {

    init {

        describe("of에 가변인자로 Flux 생성") {
            context("when parameters are given") {
                it("Flux will be created with the parameters") {
                    val flux = Flux_fox.of(1, 2, 3, 4, 5)
                    val datas = flux.get()
                    Assertions.assertThat(datas).isEqualTo(listOf(1, 2, 3, 4, 5))
                }
            }
        }
        describe("of에 null 이 들어가면 null 포함") {
            context("when some of parameter value is null") {
                it("element includes null..") {
                    val flux = Flux_fox.of(1, null, 3, 4, 5)
                    val datas = flux.get()
                    Assertions.assertThat(datas).isEqualTo(listOf(1, null, 3, 4, 5))
                }
            }
        }
        describe("of에 배열이 들어가면 1개 요소임") {
            context("when parameter is of list") {
                it("element is of element") {
                    val flux = Flux_fox.of(listOf(1, 2))
                    val datas = flux.get()
                    Assertions.assertThat(datas).isEqualTo(listOf(listOf(1, 2)))
                }
            }
        }
        describe("of에 배열이 들어가면 2개 요소임") {
            context("when parameter is of list") {
                it("element is of element") {
                    val flux = Flux_fox.of(listOf(1, 2), listOf(3, 4, 5))
                    val datas = flux.get()
                    Assertions.assertThat(datas).isEqualTo(listOf(listOf(1, 2), listOf(3, 4, 5)))
                }
            }
        }
        describe("map 으로 전환하는 기능 테스트") {
            context("map function test") {
                it("map function result") {
                    val flux = Flux_fox.of(listOf(1, 2), listOf(3, 4, 5))
                    val datas = flux.map { it.size }
                    Assertions.assertThat(datas).isEqualTo(Flux_fox.of(2, 3))
                }
            }
        }
        describe("flatMap 으로 전환하는 기능 테스트") {
            context("flatMap function test") {
                it("flatMap function result") {
                    val flux = Flux_fox.of(listOf(1, 2), listOf(3, 4, 5))
                    val datas = flux.flatMap { it.toFlux() }
                    Assertions.assertThat(datas).isEqualTo(Flux_fox.of(1, 2, 3, 4, 5))
                }
            }
        }
        describe("filter 으로 전환하는 기능 테스트") {
            context("filter function test") {
                it("filter function result") {
                    val flux = Flux_fox.of(listOf(1, 2), listOf(3, 4, 5))
                    val datas = flux.filter { it.size > 2 }
                    Assertions.assertThat(datas).isEqualTo(Flux_fox.of(listOf(3, 4, 5)))
                }
            }
        }
        describe("filter 으로 전환하는 기능 테스트 2") {
            context("filter function test 2") {
                it("filter function result 2") {
                    val flux = Flux_fox.of(listOf(1, 2), listOf(3, 4, 5))
                    val datas = flux.filter { it.size > 3 }
                    Assertions.assertThat(datas).isEqualTo(Flux_fox.of<Int>())
                }
            }
        }
    }
}