package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    var lastVisit : Date? = Date(),
    var isOnline : Boolean = false
) {
    constructor(id: String, firstName: String?, lastName: String?) : this(
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
    )

    constructor(id: String): this(
        id = id,
        firstName = "john",
        lastName = "Doe${id}",
        avatar = null
    )

    companion object Factory{
        private var lastId : Int = -1;
        fun makeUser(fullName: String?):User{
            lastId++;
            val (firstName , lastName) = Utils.parseFullName(fullName);
            return User(lastId.toString(), firstName, lastName);
        }
    }
}