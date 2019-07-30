package com.sid.kotlintest.data.db.dto

class Customer{
    var id:Int
    var name:String
    var username:String
    var password:String

    constructor(id: Int, name: String, username: String, password: String) {
        this.id = id
        this.name = name
        this.username = username
        this.password = password
    }
}