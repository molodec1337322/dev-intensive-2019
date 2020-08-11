package ru.skillbranch.devintensive.extensions

fun String.truncate(value: Int = 16): String{
    if(this.length <= value) return this

    var newStr: String = this.slice(0..value)
    while (newStr.getOrNull(newStr.lastIndex) != null && newStr.getOrNull(newStr.lastIndex).toString() == " ")
        newStr = newStr.slice(0..newStr.lastIndex-1)

    return newStr + "..."
}