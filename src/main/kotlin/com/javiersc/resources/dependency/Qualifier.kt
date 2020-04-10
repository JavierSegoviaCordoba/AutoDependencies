package com.javiersc.resources.dependency

data class Qualifier(val name: String, val number: String) : Comparable<Qualifier> {

    override fun compareTo(other: Qualifier): Int = when {
        name != other.name -> {
            if (name.isNumber && other.name.isNumber) name.toInt() - other.name.toInt()
            else name.compareTo(other.name)
        }
        number != other.number -> {
            if (number.isNumber && other.number.isNumber) number.toInt() - other.number.toInt()
            else number.compareTo(other.number)
        }
        else -> 0
    }
}