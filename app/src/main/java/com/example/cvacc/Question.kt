package com.example.cvacc

class Question(var theQuestion: String = "") {
    var description: String = ""
    var eligible: Boolean = true
    var priority: Boolean = false
    constructor(theQuestion: String, description: String) : this(theQuestion) {
        this.description = description
    }
    constructor(theQuestion: String, description: String, eligible: Boolean) : this(theQuestion,description) {
        this.eligible = eligible
    }
    constructor(theQuestion: String, description: String, eligible: Boolean, priority: Boolean) : this(theQuestion,description,eligible) {
        this.priority = priority
    }
}