package com.iitism.concetto.ui.registrationEvent.retrofit

data class RegisterDataModel(
    val admissionNumber: String,
    val botWeight: String,
    val driveLink: String,
    val eventName: String,
    val fieldOfInterest: String,
    val member: List<Member>,
    val problemStatement: String,
    val stages: List<Stage>,
    val teamLeader: String,
    val teamName: String
)