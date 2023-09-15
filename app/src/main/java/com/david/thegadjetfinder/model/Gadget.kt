package com.david.thegadjetfinder.model

class Gadget {
    private var id : Long = 0
    private val userID: Long
    private val gadget: String



    constructor(userID: Long, gadget: String) {
        this.userID = userID
        this.gadget = gadget

    }

    constructor(id: Long, userID: Long, gadget: String) {
        this.id = id
        this.userID = userID
        this.gadget = gadget

    }

    public fun getGadget() : String{
        return this.gadget;
    }
    fun getID():Long{
        return this.id
    }
}