package com.example.cvacc

/***
 * KLASA QUESTION KOJA SADRZI PITANJE;OPIS;DA LI KORISNIK MOZE PRIMITI VAKCINU UKOLIKO JE ODGOVOR DA;
 * DA LI JE KORISNIK U PRIORITETNOJ GRUPI TJ. DA LI JE PITANJE PRIORITET
 */

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