@file:Suppress("DEPRECATION")

package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?>{
        val parts : List<String>? = fullName?.split(" ");

        var firstName = parts?.getOrNull(0);
        var secondName = parts?.getOrNull(1);

        if(firstName == "") firstName = null;
        if(secondName == "") secondName = null;

        return Pair(firstName, secondName);
    }

    fun toInitials(firstName: String?, lastName: String?): String?{
        val firstInitial: String? = firstName?.getOrNull(0)?.toUpperCase()?.toString();
        val secondInitial: String? = lastName?.getOrNull(0)?.toUpperCase()?.toString();

        if(firstInitial == null || firstInitial == "") return null;
        if(secondInitial == null || secondInitial == "") return firstInitial;

        return (firstInitial + secondInitial);
    }

    /*
    fun isUriValid(uri: String?, validUri: String): Boolean{
        return if(uri == null || uri == ""){
            false
        }
        else{
            // TODO: Доделать потом
        }
    }
     */
}