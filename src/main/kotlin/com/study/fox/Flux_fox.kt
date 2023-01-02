package com.study.fox

/**
 *
 * @author foxrain user has created this file at 2023/01/02, 23:04 on 2023
 **/
class Flux_fox<T> private constructor(
    val datas: List<T> = emptyList(),
) {

    companion object {
        fun <T> of(vararg xs: T): Flux_fox<T> {
            return Flux_fox(xs.asList())
        }

//        fun <T> of_(xs: List<T> = emptyList()): Flux_fox<T> {
//            val arrayOf = arrayOf(xs)
//            return of(*arrayOf)
//        }
    }

    fun <R> map(
        functor: ((T) -> (R)),
    ): Flux_fox<R> {
       return Flux_fox(datas.map{
               functor(it)
           })
    }

    fun filter(
        predicate: ((T) -> (Boolean)),
    ): Flux_fox<T> {
        val filtered: List<T> = datas.filter {
            predicate(it)
        }

        if (filtered.isEmpty()) {
            return of()
        } else {
            return filtered.fold(of()) {
                r, l ->
                val ts = r.datas + l
                Flux_fox(ts)
            }
        }
    }

    fun get(): List<T> {
        return datas
    }

    fun <R> flatMap(
        applier: ((T) -> (Flux_fox<R>))
    ): Flux_fox<R> {
        return if (datas.isEmpty())
            of()
        else datas.map {
            applier(it)
        }.reduce { r, l ->
            Flux_fox(r.datas + l.datas)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Flux_fox<*>

        if (datas != other.datas) return false

        return true
    }

    override fun hashCode(): Int {
        return datas.hashCode()
    }

}

inline fun <reified T> List<T>.toFlux(): Flux_fox<T> {
    return Flux_fox.of(*this.toTypedArray())
}