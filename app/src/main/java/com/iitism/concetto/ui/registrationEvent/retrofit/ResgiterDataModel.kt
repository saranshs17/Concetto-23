package com.iitism.concetto.ui.registrationEvent.retrofit

data class ResgiterDataModel(
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