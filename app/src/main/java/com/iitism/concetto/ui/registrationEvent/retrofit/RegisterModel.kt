package com.iitism.concetto.ui.registrationEvent.retrofit

data class RegisterModel(
    val botWeight: String,
    val driveLink: String,
    val eventName: String,
    val fieldOfInterest: String,
    val member: List<Member>,
    val problemStatementChosen: String,
    val teamLeader: String,
    val teamName: String
)