package com.iitism.concetto.ui.allevents.retrofit

import com.iitism.concetto.ui.clubevents.Contact
import com.iitism.concetto.ui.clubevents.ExtraDetail
import com.iitism.concetto.ui.clubevents.Stage

data class AllEventsDataModel (
    var _id: String,
    var posterMobile: String,
    var posterWeb: String,
    var name: String,
    var mode: String,
    val registrationLink:String,
    val registrationStatus : String,
    var descripationEvent: String,
    var descriptionOrganizer: String,
    var type: Int,
    var organizer: String,
    var rules: List<String>,
    var prizes: String,
    var contacts: List<Contact>,
    var pdfLink: String,
    var minTeamSize: Int,
    var maxTeamSize: Int,
    var problemStatements: String,
    var extraDetails: List<ExtraDetail>,
    var teams: List<String>,
    var stages: List<Stage>
)

