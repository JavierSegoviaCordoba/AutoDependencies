package com.javiersc.resources.dependency
data class Version(
    val major: String,
    val minor: String,
    val incremental: String,
    val qualifier: String?
) : Comparable<Version> {

    override fun compareTo(other: Version): Int {
        return when {
            major != other.major -> when {
                major.isNumber && other.major.isNumber -> major.toInt() - other.major.toInt()
                else -> major.compareTo(other.major, true)
            }
            minor != other.minor -> when {
                minor.isNumber && other.minor.isNumber -> minor.toInt() - other.minor.toInt()
                else -> minor.compareTo(other.minor, true)
            }
            incremental != other.incremental -> when {
                incremental.isNumber && other.incremental.isNumber -> {
                    incremental.toInt() - other.incremental.toInt()
                }
                else -> incremental.compareTo(other.incremental, true)
            }
            qualifier != other.qualifier -> {
                if (qualifier == null && other.qualifier != null) 1
                else if (qualifier != null && other.qualifier == null) -1
                else if (qualifier != null && other.qualifier != null)
                    qualifier.toQualifier().compareTo(other.qualifier.toQualifier())
                else 0
            }
            else -> 0
        }
    }
}