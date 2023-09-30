package com.iitism.concetto.ui.clubevents


data class Club_dataclass(
    var _id: String,
    var posterMobile: String,
    var posterWeb: String,
    var name: String,
    var mode: String,
    var descripationEvent: String,
    var descriptionOrganizer: String,
    var type: Int,
    var oraganizer: String,
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