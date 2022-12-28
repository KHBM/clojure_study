package com.study.fox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FoxApplication

fun main(args: Array<String>) {
	runApplication<FoxApplication>(*args)
}
