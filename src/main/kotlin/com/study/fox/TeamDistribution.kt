package com.study.fox

/**
 *
 * @author foxrain user has created this file at 2023/03/04, 23:51 on 2023
 **/
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 *
 * @author foxrain user has created this file at 2023/03/04, 23:28 on 2023
 **/
class TeamDistribution(
    val strategy: Strategy,
) {

    fun distribute(
        members: List<TeamMember>,
    ): Map<Int, TeamMember> {

        val memberNumber = strategy.numberOfMember
        if (memberNumber == 0) {
            System.err.println("Team Number Cannot be 0")
            throw java.lang.RuntimeException("팀 인원은 0명이 되면 안되요")
        }

        // 팀 갯수... 분배.. 팀 갯수는?
        val sizeAllMember = members.size
        val teamNumber = 6// sizeAllMember / memberNumber

        println("팀은 최대 $teamNumber 만큼 생성됩니다.")

        //만약 그룹 분배가 이뤄진다면
        var targetMemberList = if (strategy.evenGreeny) {
            members.sortedBy { -it.score.value }
        } else {
            members
        }
//        println(members.sortedWith(compareBy( {it.greeny}, {it.gender}, {it.score})))
        println(members.sortedWith(compareBy( {it.greeny}, {it.gender}, {it.score})))
        //만약 성별 분배가 이뤄진다면

        //만약 실력 분배가 이뤄진다면

        val teamMap = mutableMapOf<Int, Team>()
        for (i in 0..5) {
            teamMap[i] = Team(mutableSetOf())
        }

        for (m in targetMemberList) {
            var targetTeamId = -1

            val lowScore = getLowScore(teamMap)
            val lowGender = getLowGender(teamMap, (m.gender.value > 0))
            val lowGroup = getLowGroup(teamMap, (m.greeny.value > 0))

            println("점수: $lowScore/성별: $lowGender/그룹: $lowGroup")

//            teamMap[lowGender]!!.add(m)
            teamMap[getCount(
                lowScore,
                lowGender,
                lowGroup,
            )]!!.add(m)
        }

        for (t in teamMap) {
            println(" t(${t.value.size()})/score=${t.value.getScore()}/gender=${t.value.getGender()}/group=${t.value.getGroup()}" +
                    " : ${t.value.members.sortedBy { -it.score.value }}")
        }
        for (t in teamMap) {
            println("${t.key+1}팀" +
//                    "/전투력합계(${t.value.getScore()})" +
                    "/남여성비>${t.value.members.filter { it.gender == Gender.M }.count()}:${t.value.members.filter { it.gender == Gender.F }.count()}" +
                    "/그룹분포(그린포켓:클라임네이버)>${t.value.members.filter { it.greeny == Greeny.GREEN_POCKET }.count()}:${t.value.members.filter { it.greeny == Greeny.CLIMB_NAVER }.count()}" +
                    "/팀원(${t.value.size()})] ${t.value.members.sortedBy { -it.score.value }.map { it.name }}")
        }

        return mapOf()
    }

    fun getCount(
        score: Int,
        gender: Int,
        group: Int
    ): Int {
        //3 same
        if (score == gender && gender == group) {
            return score
        }
        //right two same
        if (score != gender && gender == group) {
            return score
        }

        //
        if (score != gender && gender != group) {
            //left right same
            if (score == group) {
                return score
            }
            //no same
            else {
                return score
            }
        }

        if (score == gender && gender != group) {
            return score
        }
        return score
    }

    fun getLowScore(
        map: Map<Int, Team>
    ): Int {
        val sortedBy = map.entries
            .filter { it.value.size() < 8 }
            .sortedBy {
                it.value.getScore()
            }
        return sortedBy[0].key
    }

    fun getLowGroup(
        map: Map<Int, Team>,
        isPos: Boolean,
    ): Int {
        val sortedBy = map.entries
            .filter { it.value.size() < 8 }
            .sortedBy {
                it.value.getGroup()
            }
        val sort = sortedBy.find {
                if (isPos) {
                    it.value.getGroup() >= 0
                } else {
                    it.value.getGroup() < 0
                }
            }
        return if (sort == null) sortedBy[0].key else sort.key
    }
    fun getLowGender(
        map: Map<Int, Team>,
        isPos: Boolean,
    ): Int {
        val sortedBy = map.entries
            .filter { it.value.size() < 8 }
            .sortedBy {
                it.value.getGender()
            }

        val sort= sortedBy.find {
                if (!isPos) {
                    it.value.getGender() >= 0
                } else {
                    it.value.getGender() < 0
                }
            }
        return if (sort == null) sortedBy[0].key else sort.key
    }
}

fun main() {
    try {
        val file = File("/Users/user/Downloads/member.txt")

        val reader = BufferedReader(InputStreamReader(file.inputStream()))

        val allMembers = mutableListOf<TeamMember>()
        reader.use {
            it.lines()
                .forEach {
                    val split = it.split("/")
                    if (split.isEmpty()) {
                        return@forEach
                        println("cannot parse $it")
                    }

                    val group = split[0]
                    val greeny = if (group == "Climb NAVER") Greeny.CLIMB_NAVER else Greeny.GREEN_POCKET

                    val name = split[1]
                    val score = Score.valueOf(split[2])
                    val gender = Gender.valueOf(split[3])

                    allMembers.add(
                        TeamMember(
                            greeny = greeny,
                            name = name,
                            score =score,
                            gender = gender,
                        )
                    )
                }
        }

        println("size = ${allMembers.size}")
        val sortedWith =
            allMembers.sortedWith(compareBy({ it.greeny }, { -it.score.value }, { it.gender }))
        for (m in sortedWith) {
            println(m)
        }

        val allM = sortedWith.filter { it.gender == Gender.M }.count()
        val allF = sortedWith.filter { it.gender == Gender.F }.count()
        val allGr = sortedWith.filter { it.greeny == Greeny.GREEN_POCKET }.count()
        val allNC= sortedWith.filter { it.greeny == Greeny.CLIMB_NAVER }.count()

        println("남($allM)/여($allF)/그포($allGr)/네클($allNC)")

        val td = TeamDistribution(
            Strategy(
                numberOfMember = sortedWith.size,
                evenGender = true,
                evenScore = true,
                evenGreeny = true,
            )
        )

        val distribute = td.distribute(sortedWith)


    } catch (e: Exception) {
        e.printStackTrace()
    }
    println("======")


}

data class Strategy(
    val numberOfMember: Int,
    val evenGender: Boolean,
    val evenScore: Boolean,
    val evenGreeny: Boolean,
) {

}

enum class Greeny(
    val value: Int
) {
    CLIMB_NAVER(-1),
    GREEN_POCKET(1),
}

enum class Gender(
    val value: Int
) {
    M(-1),
    F(1),
}

enum class Score(
    val value: Int
) {
    ORANGE(0),
    ORANGE_YELLOW(1),
    YELLOW(2),
    YELLOW_GREEN(3),
    GREEN(4),
    GREEN_BLUE(5),
    BLUE(6),
    BLUE_RED(7),
    RED(8),
    RED_PURPLE(9),
    PURPLE(10),
    EXPERT(11),
}

data class TeamMember(
    val greeny: Greeny,
    val gender: Gender,
    val score: Score,
    val name: String,
): Comparable<TeamMember> {
    override fun compareTo(other: TeamMember): Int {
       return compareValuesBy(this, other, { it.gender.value })
    }
}

class GroupComparator: Comparator<TeamMember> {
    override fun compare(o1: TeamMember?, o2: TeamMember?): Int {
        if (o1 == null) return 0
        if (o2 == null) return 0

        return if (o1.greeny == o2.greeny) 0 else if (o1.greeny == Greeny.GREEN_POCKET) 1
        else -1
    }
}

class GenderComparator: Comparator<TeamMember> {
    override fun compare(o1: TeamMember?, o2: TeamMember?): Int {
        if (o1 == null) return 0
        if (o2 == null) return 0

        return if (o1.gender == o2.gender) 0 else if (o1.gender == Gender.F) 1
        else -1
    }
}

class ScoreComparator: Comparator<TeamMember> {
    override fun compare(o1: TeamMember?, o2: TeamMember?): Int {
        if (o1 == null) return 0
        if (o2 == null) return 0

        return o1.score.value - o2.score.value
    }
}

data class Team(
    val members: MutableSet<TeamMember>
) {
    fun getScore():Int {
        return members.sumOf { it.score.value }
    }

    fun getGender(): Int {
        return members.sumOf { it.gender.value }
    }

    fun getGroup(): Int {
        return members.sumOf { it.greeny.value }
    }

    fun add(
        member : TeamMember
    ) {
        members.add(member)
    }

    fun size():Int {
        return members.size
    }
}