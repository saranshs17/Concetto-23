package com.iitism.concetto.ui.registrationEvent.retrofit

data class RegisterDataModel(
    var admissionNumber: String,
    var botWeight: String,
    var driveLink: String,
    var eventName: String,
    var fieldOfInterest: String,
    var member: List<Member>,
    var problemStatement: String,
    var stages: List<Stage>,
    var teamLeader: String,
    var teamName: String
)