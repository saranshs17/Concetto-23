package com.iitism.concetto.ui.singleevent

data class SingleEventModelItem(
    val _id: String,
    val contacts: List<Contact>,
    val descriptionEvent: String,
    val descriptionOrganizer: String,
    val extraDetails: List<ExtraDetail>,
    val fees: Int,
    val maxTeamSize: Int,
    val minTeamSize: Int,
    val mode: String,
    val name: String,
    val organizer: String,
    val pdfLink: String,
    val posterMobile: String,
    val posterWeb: String,
    val prizes: String,
    val problemStatements: String,
    val rules: List<String>,
    val stages: List<Stage>,
    val teams: List<String>,
    val type: Int
)