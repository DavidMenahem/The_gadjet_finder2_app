package com.david.thegadjetfinder.model

class ApiResponse {
    private val email : String
    private val userID : Long
    private val response : String

    constructor(email: String, userID: Long, response: String) {
        this.email = email
        this.userID = userID
        this.response = response
    }

    fun getUserID(): Long{
        return this.userID
    }

    fun getResponse(): String{
        return this.response
    }
}