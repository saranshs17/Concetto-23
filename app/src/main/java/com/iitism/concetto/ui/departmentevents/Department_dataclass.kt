package com.iitism.concetto.ui.departmentevents

data class Department_dataclass(
    val posterMobile: String,
    val posterWeb: String,
    val name: String,
    val mode: String,
    val descriptionEvent: String,
    val descriptionOrganizer: String,
    val type: Int,
    val organizer: String,
    val rules: List<String>,
    val prizes: String,
    val contacts: List<String>,
    val fees: Int,
    val pdfLink: String,
    val minTeamSize: Int,
    val maxTeamSize: Int,
    val problemStatements: String,
    val extraDetails: List<String>,
    val teams: List<String>,
    val stages: List<String>
)
