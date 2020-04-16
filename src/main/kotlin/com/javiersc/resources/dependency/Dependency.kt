package com.javiersc.resources.dependency

data class Dependency(private val dependency: String) : Comparable<Dependency> {
    val groupId: String = dependency.split(":")[0]
    val artifact: String = dependency.split(":")[1]
    val version: Version? =
        if (dependency.count { it == ':' } == 3) dependency.split(":")[2].toVersion() else null

    override fun compareTo(other: Dependency): Int = when {
        groupId != other.groupId -> when {
            groupId.isNumber && other.groupId.isNumber -> groupId.toInt() - other.groupId.toInt()
            else -> groupId.compareTo(other.groupId, true)
        }
        artifact != other.artifact -> when {
            artifact.isNumber && other.artifact.isNumber -> artifact.toInt() - other.artifact.toInt()
            else -> artifact.compareTo(other.artifact, true)
        }
        version != other.version && version != null && other.version != null -> {
            version.compareTo(other.version)
        }
        else -> 0
    }

    override fun toString(): String = dependency
}

fun List<Dependency>.cleanDuplicates(): List<Dependency> {
    return groupingBy { Pair(it.groupId, it.artifact) }
        .reduce { _, accumulator, element -> maxOf(accumulator, element) }
        .map { it.value }
        .toList()
}

fun List<Dependency>.toDependenciesString(): String {
    var temp = "import org.gradle.kotlin.dsl.DependencyHandlerScope\n\n"
    forEach {
        temp += """|val DependencyHandlerScope.${it.artifact.toCamelCase()}: String 
                   |    get() = "$it"
                   |${"\n"}
                """.trimMargin()
    }
    return temp
}