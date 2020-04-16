package com.javiersc.resources.dependency

internal fun String.toDependency(): Dependency = Dependency(this)

internal fun String.toVersion(): Version {
    val majorMinorIncremental = split("-")[0]
    val qualifier = split("-").getOrNull(1)
    val (major, minor, incremental) = majorMinorIncremental.split(".")
    return Version(major, minor, incremental, qualifier)
}

internal fun String.toQualifier(): Qualifier = Qualifier(filter(Char::isLetter), filter(Char::isDigit))

internal val String.isNumber: Boolean get() = all(Char::isDigit)

internal fun String.toCamelCase(): String {
    return split("-").joinToString("") { it.capitalize() }.decapitalize()
}