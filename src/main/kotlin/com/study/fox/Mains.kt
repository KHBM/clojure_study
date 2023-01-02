package com.study.fox


fun main(args: Array<String>) {
    try {
        val opt: Optional_fox<Int> = Optional_fox.of(null)

        val orElse: Int =
            opt
            .map { it + 1 }
            .map { it + 2 }
            .orElse(342)
        println(orElse)

        val get = Optional_fox.of(3)
            .filter { it > 3 }
            .get()
        println(get)

        val nullValue = Optional_fox.of(null)
            .filter { it == 3 }
            .get()
        println("return $nullValue")

        val get2 = Optional_fox.of(3)
            .map { it.toString()  }
            .map { it == "3"}
            .get()
        println(get2)

        val orElse1 = Optional_fox.of(listOf(1, 2, 3, 4))
            .map { it.size }
            .orElse(5)
        println(orElse1)

        listOf(1, 2, 3).map {
            it + 3
        }.stream().map { it }

        val fnOp = Optional_fox.of(
            { a:Int -> a + 100 }
        )

        val dOp = Optional_fox.of(50)

        val rOp: Optional_fox<Int> = applyi(fnOp, dOp)

//        val fn1 = { a:Int -> a + 100 }
//        val fv1 = 50
//
//        fn1.invoke(fv1)

        println(rOp.get())

    } catch(e: Exception) {
        e.printStackTrace()
    }
}