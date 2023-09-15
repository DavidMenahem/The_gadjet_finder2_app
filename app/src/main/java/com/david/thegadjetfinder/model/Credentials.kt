package com.david.thegadjetfinder.model

class Credentials{
    private val email : String
    private val password : String;

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}