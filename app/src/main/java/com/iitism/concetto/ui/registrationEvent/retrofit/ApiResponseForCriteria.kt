package com.iitism.concetto.ui.registrationEvent.retrofit

data class ApiResponseForCriteria(
    val __v: Int,
    val _id: Id,
    val botWeight: Boolean,
    val driveLink: Boolean,
    val eventName: String,
    val fieldOfInterest: Boolean,
    val members: Boolean,
    val problemStatement: Boolean,
    val teamLeader: Boolean,
    val teamName: Boolean
)